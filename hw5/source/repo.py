from __future__ import division
import re
import math

class Repo:
    def __init__(self):
        self.featureVector = {} # { featureName: count }
        self.classVector = {}   # {className1: {{f1:1}, {f2:1}, {f3:1}}, className2: {}} - a dictionary of feature vectors represented as dictionaries
        self.classTotal = {}   # {className1: #feat-s, className2: #feat-s, ...}
        self.classExpected = {}
        self.chi2 = {}
        self.total = 0
        self.df = 0


    def read_into_dicts(self, line_of_input):
        ilist = re.split('\s+', line_of_input.strip())

        className = ilist[0]

        if className not in self.classVector:
            self.classVector[className] = {}

        if className not in self.classTotal:
            self.classTotal[className] = 0

        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:

                if pair[0] in self.featureVector:
                    self.featureVector[pair[0]] += 1
                else:
                    self.featureVector[pair[0]] = 1
             
                if pair[0] in self.classVector[className]:
                    self.classVector[className][pair[0]] += 1
                else:
                    self.classVector[className][pair[0]] = 1

                self.classTotal[className] += 1


    def getTotal(self):   
        for key in self.classTotal:
            self.total += self.classTotal[key]


    def getClassExpected(self):
        self.getTotal()
        for key in self.classTotal:
            for feat in self.featureVector:
                self.classExpected[key] = self.classTotal[key] * self.featureVector[feat] / self.total


    def chiSq(self):
    # chiSq for every feature
        for feat in self.featureVector:
            chi = 0
            
            for className in self.classExpected:
                if feat not in self.classVector[className]:
                    self.classVector[className][feat] = 0
                chi += math.pow((self.classVector[className][feat] - self.classExpected[className]), 2) / self.classExpected[className]

            self.chi2[feat] = chi

#    def chiSq(self):
# calculates one number for the whole table               
#        for feat in self.featureVector:
#            for className in self.classExpected:
#                self.chi2 += math.pow((self.classVector[className][feat] - self.classExpected[className]), 2) / self.classExpected[className]


    def DF(self):
        self.df = (len(self.featureVector) - 1) * (len(self.classTotal) - 1)
