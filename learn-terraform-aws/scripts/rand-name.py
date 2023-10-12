import random, string, json

def getRandomName(size:int = 6):
    return ''.join(random.SystemRandom().choice(string.ascii_uppercase + string.digits) for _ in range(size))

data = {
    "random_name": getRandomName(10)
}

print(json.dumps(data))