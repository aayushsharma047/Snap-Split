import torch
import torch.nn as nn
import torch.nn.functional as F

class NeuralNetwork(nn.Module):
    
    def __init__(self):
        super(NeuralNetwork, self).__init__()
        self.encoder = nn.Sequential(
            nn.Conv2d(1, 6, 5), 
            nn.MaxPool2d(2, 2), 
            nn.ReLU(True),      
            nn.Conv2d(6, 16, 5), 
            nn.MaxPool2d(2, 2),  
            nn.ReLU(True) 
        )
        self.classifier =  nn.Sequential(
            nn.Linear(16 * 4 * 4, 120),
            nn.ReLU(),
            nn.Linear(120, 84),
            nn.ReLU(),
            nn.Linear(84, 10),
            nn.Softmax(1)
        )

    def forward(self, x):
        x = self.encoder(x)
        x = x.view(-1, 16 * 4 * 4)
        x = self.classifier(x)
        return x
