# Snap Split

## Introduction
Snap Split is a cutting-edge mobile application that employs deep learning to forecast numbers extracted from images taken with Android devices. This Android app can identify numbers within images and categorize them into folders. Moreover, Snap Split leverages parallel processing to enhance precision by adopting a collaborative strategy: it divides images into four segments, sends each segment to four distinct servers for decentralized number prediction, consolidates the outcomes for accuracy, and subsequently saves the image in the appropriate sub-folder based on the final prediction.

## Model Training
Our deep learning model, built using PyTorch, incorporates convolutional layers, max pooling, ReLU activation, and linear layers. PyTorch's dynamic computation capabilities simplify the construction of intricate neural networks.

## Integration with Application
The PyTorch model seamlessly integrates into the Android application. Upon capturing an image, the mobile app sends an image upload request to the Flask server. The server's function decodes the image, predicts its category, and saves it in the corresponding folder.

## Project Workflow Steps
1. Image segmentation into four parts.
2. Image upload to the Flask server.
3. Processing on four different sub-ordinate servers.
4. Combination of results and summarizing them to determine the final prediction.

## Dependencies / Tech Used
<div align="center">
	<code><img width="100" src="https://user-images.githubusercontent.com/25181517/192108895-20dc3343-43e3-4a54-a90e-13a4abbc57b9.png" alt="Android Studio" title="Android Studio"/></code>
	<code><img width="100" src="https://user-images.githubusercontent.com/25181517/183423507-c056a6f9-1ba8-4312-a350-19bcbc5a8697.png" alt="Python" title="Python"/></code>
	<code><img width="100" src="https://user-images.githubusercontent.com/25181517/183423775-2276e25d-d43d-4e58-890b-edbc88e915f7.png" alt="Flask" title="Flask"/></code>
    <code><img width="300" src="https://upload.wikimedia.org/wikipedia/commons/9/96/Pytorch_logo.png" alt="Flask" title="Flask"/></code>
</div>

## Instructions for Running the Project
1. Install necessary dependencies for Android (JAVA SDK & Emulator Dependencies)
2. Run the Flask server on the specified IP and port.
3. Deploy the Android application using Android Studio.
4. Click and Upload the Image for Prediction.
