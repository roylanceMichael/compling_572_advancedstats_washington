from __future__ import division
import re
import math
import model
import decode

class ModelE:
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
                # GET RID OF SELF.FEATURES!!!
                if pair[0] in self.features[className]:
                    self.features[className][pair[0]] += 1
                else:
                    self.features[className][pair[0]] = 1

                # for total number of features
                if pair[0] not in self.allFeat:
                    self.allFeat[pair[0]] = 1
                else:
                    self.allFeat[pair[0]] += 1                    


    def outputModelE_(self, p, total):
        # without a model file
        stringBuilder = ""
        for className in self.features:
            for feat in sorted(self.allFeat):
                count = p * self.allFeat[feat]
                model = count / total
                stringBuilder += str(className) + " " + str(feat) + " " + str(model) + " " + str(count) + "\n"
        
        return stringBuilder


#    def outputModelE_model(self, d, total):
#        # with a model file
#        
#        stringBuilder = ""
#        for className in self.features:
#            for feat in sorted(self.allFeat):
#                count = p * self.allFeat[feat]
#                model = count / total
#                stringBuilder += str(className) + " " + str(feat) + " " + str(model) + " " + str(count) + "\n"
        
#        return stringBuilder
        



