from fer import FER
import redis
import json
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
import base64

r = redis.Redis(host='localhost', port=6379, db=0)

def writeResult(result):
  r.publish("result", json.dumps(result))

def getInfoByPhoto(contentBytes):
  with open('./tmp.jpg', 'wb') as file:
    file.write(base64.b64decode(contentBytes))

  test_image_one = plt.imread("./tmp.jpg")

  emo_detector = FER(mtcnn=True)

  captured_emotions = emo_detector.detect_emotions(test_image_one)

  if (len(captured_emotions) == 0):
    return {"angry": -1, "disgust": -1, "fear": -1, "happy": -1, "sad": -1, "surprise": -1, "neutral": -1}

  return captured_emotions[0]['emotions']


def processMessage(message):
  if (message['type'] == "message"):
    parseMessage = json.loads(message['data'])

    res = getInfoByPhoto(parseMessage['photoBytes'])

    if (res != None):
      writeResult(
        {
          "chatId": parseMessage['chatId'],
          "result": res
        }
      )

  else:
    return None


sub = r.pubsub()
sub.subscribe("emotional")
while True:
  msg = sub.get_message()
  if (msg != None):
    processMessage(msg)