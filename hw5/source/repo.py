import re

class Repo:
    def __init__(self):
        self.featureVector = {} # { featureName: count }
        self.classVector = {}   # {className1: [{f1:1}, {f2:1}, {f3:1}], className2: []} - a list of feature vectors represented as dictionaries


    def read_into_dicts(self, line_of_input):
        ilist = re.split('\s+', line_of_input.strip())

        className = ilist[0]

        if className not in self.classVector:
            self.classVector[className] = {}
        
        vectDict = {}
        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:
                vectDict[pair[0]] = float(pair[1])

                if pair[0] in self.featureVector:
                    self.featureVector[pair[0]] += 1
                else:
                    self.featureVector[pair[0]] = 1

                if pair[0] in self.classVector[className]:
                    self.classVector[className][pair[0]] += 1
                else:
                    self.classVector[className][pair[0]] = 1