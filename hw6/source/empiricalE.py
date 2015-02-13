from __future__ import division
import re
import math

class Empirical:
    def __init__(self):
        self.features = {}   # {className1: {f1:#, f2:#, f3:#,..}, className2: {f1:#, f2:#,..},.. }
        self.allFeat = {}   # {f1:None, f2:None,.. }
        

    def read_into_dicts(self, line_of_input):
        ilist = re.split('\s+', line_of_input.strip())

        className = ilist[0]
        if className not in self.features:
            self.features[className] = {}

        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:
                # for raw count
                if pair[0] in self.features[className]:
                    self.features[className][pair[0]] += 1
                else:
                    self.features[className][pair[0]] = 1

                # for total number of features
                if pair[0] not in self.allFeat:
                    self.allFeat[pair[0]] = None


    def outputEmpirical(self, total):
        stringBuilder = ""
        for className in self.features:
            for feat in sorted(self.features[className]):
                empirical = self.features[className][feat] / total
                stringBuilder += str(className) + " " + str(feat) + " " + str(empirical) + str(self.features[className][feat]) + "\n"
        
        return stringBuilder
        



