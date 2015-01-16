from __future__ import division
import os
import sys
import re
import math
import getVectors
from decimal import Decimal


class S:
        def __init__(self):
            self.allFeatures = {}
            self.vectors = []
            self.distr = {} # 'talk.politics.guns' : 100
            self.totalSize = 0
            self.featureSplitOn = ""
            self.hasFeatureSplitOn = False

        def addVectors(self, vectors):
            for vector in vectors:
                self.vectors.append(vector)

                if vector['className'] in self.distr:
                    self.distr[vector['className']] += 1
                else:
                    self.distr[vector['className']] = 1

                for key in vector:
                    self.allFeatures[key] = None

            self.totalSize = len(vectors)

        def entropy(self):
            # calculate global entropy
            e = 0
            for key in self.distr:
                e += -(self.distr[key]/self.totalSize*math.log(self.distr[key]/self.totalSize, 2))
            return e

        def splitVectorsOnFeatures(self):
            # will be going through each feature, creating a new "s",
            # and splitting the vectors on that feature
            for feature in self.allFeatures:
                vectorsWithFeature = []
                vectorsWithoutFeature = []

                for vector in self.vectors:
                    if feature in vector:
                        vectorsWithFeature.append(vector)
                    else:
                        vectorsWithoutFeature.append(vector)


                sWithFeature = S()
                sWithFeature.featureSplitOn = feature
                sWithFeature.hasFeatureSplitOn = True
                sWithoutFeature = S()
                sWithoutFeature.featureSplitOn = feature
                sWithoutFeature.hasFeatureSplitOn = False

                sWithFeature.addVectors(vectorsWithFeature)
                sWithoutFeature.addVectors(vectorsWithoutFeature)

                yield (sWithFeature, sWithoutFeature)
