import os
import sys
import re
import vectorInstance

class GetVectors:
    def __init__(self):
        self.currentId = 1
        self.allFeatures = {}
        self.vectors = {}   # a list of feature vectors represented as dictionaries

    def binarize(self, line):
        # binarize the input: non-zero values are substituted by 1
        return re.sub(r':[123456789]\d*', r':1', line)

    def getVector(self, vectorId):
        return self.vectors[vectorId]

    def getFeatures(self, vectorId):
        return self.vectors[vectorId].features

    def getClassName(self, vectorId):
        return self.vectors[vectorId].className

    def read_into_dicts(self, line_of_input):
        # turn each feature vector into a dictionary
        ilist = re.split('\s+', self.binarize(line_of_input).strip())
        vectDict = {}
        for i in ilist[0:]:
            pair = i.split(':')
            if len(pair) == 2:
                vectDict[pair[0]] = pair[1]
                self.allFeatures[pair[0]] = None

        vi = vectorInstance.VectorInstance(self.currentId, ilist[0], vectDict)
        self.vectors[self.currentId] = vi
        self.currentId = self.currentId + 1

