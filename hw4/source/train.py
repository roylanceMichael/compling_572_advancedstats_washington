import re
import vectorInstance


class Train:
    def __init__(self):
        self.vectors = {}   # {className1: [{f1:1}, {f2:1}, {f3:1}], className2: []} - a list of feature vectors represented as dictionaries
        self.currentId = 1
        self.allTrainFeatures = {}
        self.classNames = {}


    def read_into_dicts(self, line_of_input):
        ilist = re.split('\s+', line_of_input.strip())

        className = ilist[0]
        self.classNames[className] = None
        
        vectDict = {}
        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:
                vectDict[pair[0]] = float(pair[1])
                self.allTrainFeatures[pair[0]] = None

        vi = vectorInstance.VectorInstance(self.currentId, ilist[0], vectDict)
        self.vectors[self.currentId] = vi
        self.currentId = self.currentId + 1

    def getClassNameFromId(self, vectId):
        if vectId in self.vectors:
            return self.vectors[vectId].className
        return None