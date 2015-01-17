from __future__ import division
import os
import sys
import re
import math
import getVectors
import ig
from decimal import Decimal

class S:
        def __init__(self, vectorRepo, treeDepth):
            self.treeDepth = treeDepth
            self.vectorRepo = vectorRepo
            self.vectorIds = []
            self.distr = {} # 'talk.politics.guns' : 100
            self.totalSize = 0
            self.featureSplitOn = ""
            self.hasFeatureSplitOn = False
            self.highestIgInstance = None

        def reportSelf(self):
            if len(self.featureSplitOn) == 0:
                return ""

            if self.hasFeatureSplitOn:
                return self.featureSplitOn
            else:
                return "!" + self.featureSplitOn

        def reportConcat(self):
            if self.highestIgInstance == None or len(self.featureSplitOn) == 0:
                return ""
            else:
                return "&"


        def reportTree(self):
            if self.highestIgInstance == None:
                yield self.reportSelf()
            else:
                for childReport in self.highestIgInstance.splitWithFeature.reportTree():
                    yield self.reportSelf() + self.reportConcat() + childReport

                for childReport in self.highestIgInstance.splitWithoutFeature.reportTree():
                    yield self.reportSelf() + self.reportConcat() + childReport

        def addVectors(self, vectorIds):
            for vectorId in vectorIds:
                self.vectorIds.append(vectorId)

                className = self.vectorRepo.getClassName(vectorId)

                if className in self.distr:
                    self.distr[className] += 1
                else:
                    self.distr[className] = 1

            self.totalSize = len(vectorIds)

        def entropy(self):
            # calculate global entropy
            e = 0
            for key in self.distr:
                e += -(self.distr[key]/self.totalSize*math.log(self.distr[key]/self.totalSize, 2))
            return e

        def informationGain(self):
            selfEntropy = self.entropy()

            highestInformationGain = self.vectorRepo.minimumInfoGain

            for splitSet in self.splitVectorsOnFeatures():
                # newIg is a whole class... sorry, that's a bad name
                newIg = ig.IG(self, splitSet[0], splitSet[1])

                currentIg = newIg.calculateInformationGain()

                if currentIg > highestInformationGain:
                    highestInformationGain = currentIg
                    self.highestIgInstance = newIg

                    featureSplit = splitSet[0].featureSplitOn
                    featureWithTotal = splitSet[0].totalSize
                    featureWithoutTotal = splitSet[1].totalSize
                    informationGain = highestInformationGain

                    print "feature %s (%s/%s) with %s was highest!" % (featureSplit, featureWithTotal, featureWithoutTotal, informationGain)

            if self.highestIgInstance != None:
                self.highestIgInstance.calculateInformationGainForChildren()

        def splitVectorsOnFeatures(self):
            if self.treeDepth == self.vectorRepo.maxTreeDepth:
                return
                yield

            # will be going through each feature, creating a new "s",
            # and splitting the vectors on that feature
            for feature in self.vectorRepo.allFeatures:
                vectorsWithFeature = []
                vectorsWithoutFeature = []

                for vectorId in self.vectorIds:
                    if feature in self.vectorRepo.getFeatures(vectorId):
                        vectorsWithFeature.append(vectorId)
                    else:
                        vectorsWithoutFeature.append(vectorId)


                sWithFeature = S(self.vectorRepo, self.treeDepth + 1)
                sWithoutFeature = S(self.vectorRepo, self.treeDepth + 1)

                sWithFeature.featureSplitOn = feature
                sWithFeature.hasFeatureSplitOn = True
                sWithoutFeature.featureSplitOn = feature
                sWithoutFeature.hasFeatureSplitOn = False
                sWithFeature.addVectors(vectorsWithFeature)
                sWithoutFeature.addVectors(vectorsWithoutFeature)

                yield (sWithFeature, sWithoutFeature)
