from crypt import methods
from tokenize import String
from turtle import st
from urllib import request
from flask import Flask
from flask import request
from torchvision import transforms
import os
import base64
import matplotlib.pyplot as plt
import torch
import numpy as np
from neural_network import NeuralNetwork
from flask import Flask, request
import json
from PIL import Image, ImageOps
import io

IMGFOLDER = os.getcwd() + '/images/'

model = None

app = Flask(__name__)

def load():
    global model
    model = NeuralNetwork()
    model.load_state_dict(torch.load("./Models/ass3model3.pth"))
    model.eval()

def get_data(bytesdata):
    
    image_data = bytesdata
    image = Image.open(io.BytesIO(image_data))
    image = ImageOps.grayscale(image)
    print(image.size)
    image = image.resize((28,28))
    print(image.size)
    image.show()
    convert_tensor = transforms.ToTensor()
    data_tensor = convert_tensor(image)
    data_tensor_flattened = torch.reshape(data_tensor, (1, 1, 28, 28))
    guess = torch.argmax(model(data_tensor_flattened)[0])
    print(guess)
    return str(guess)

@app.route('/upload2', methods = ['GET', 'POST'])
def downloadimg():
    
    import datetime

    # Get Request Details from the Application
    base64ImageData = str(request.form['imageuri'])
   
    #Decode the Base64 Image data
    img_plain_data = base64.b64decode(base64ImageData)
    cat3 = get_data(img_plain_data)[7]
    print(cat3)

    # Return Response
    return cat3

if __name__ == "__main__":
    load()
    app.run(host="0.0.0.0", port=5002, debug=True)