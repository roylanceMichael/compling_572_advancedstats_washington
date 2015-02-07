from __future__ import division
import re
import math

class Repo:
    def __init__(self):
        self.featureVector = {} # { featureName: count } - over all the classes
        self.classVector = {}   # {className1: {{f1:1}, {f2:1}, {f3:1}}, className2: {}} - a dictionary of feature vectors represented as dictionaries
        self.docsInClass = {}
        self.featExpected = {}   # {featureName: {className1 : [float, float], className2 : [], } featureName: {}} - calculate Expected value
        self.chi2 = {}
        self.total = 0
        self.df = 0


    def read_into_dicts(self, line_of_input):
        ilist = re.split('\s+', line_of_input.strip())

        className = ilist[0]

        if className not in self.classVector:
            self.classVector[className] = {}

        if className not in self.docsInClass:
            self.docsInClass[className] = 1
        else:
            self.docsInClass[className] += 1

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


    def getTotal(self):   
        for key in self.docsInClass:
            self.total += self.docsInClass[key]

    def getClassExpected(self):
        self.getTotal()

        for feat in self.featureVector:
            # {feature : {class : [expected_out_of_class, expected_in_class]} }
            self.featExpected[feat] = {}
            for className in self.classVector:
                 self.featExpected[feat][className] = [0, 0]
                 # f not in c
                 self.featExpected[feat][className][0] += (self.total - self.featureVector[feat]) * self.docsInClass[className] / self.total
                 # f in c
                 self.featExpected[feat][className][1] += self.featureVector[feat] * self.docsInClass[className] / self.total


    def chiSq(self):
    # chiSq for every feature - fill chi2
        for feat in self.featureVector:
            chi = 0
            for className in self.featExpected[feat]:
                if feat not in self.classVector[className]:
                    self.classVector[className][feat] = 0
                # f not in c
                chi += math.pow(((self.docsInClass[className] - self.classVector[className][feat]) - self.featExpected[feat][className][0]), 2) / self.featExpected[feat][className][0]
                # f in c
                chi += math.pow((self.classVector[className][feat] - self.featExpected[feat][className][1]), 2) / self.featExpected[feat][className][1]

            self.chi2[feat] = chi


    def DF(self):
        self.df = (len(self.featureVector) - 1) * (len(self.classTotal) - 1)
