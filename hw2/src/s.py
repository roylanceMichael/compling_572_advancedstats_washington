from __future__ import division
import os
import sys
import re
import math
import getVectors
from decimal import Decimal


class S:
        def __init__(self):
            self.vectors = []
            self.distr = {} # 'talk.politics.guns' : 100
            self.totalSize = 0

        def addVectors(self, vectors):
            for vector in vectors:
                if vector['className'] in self.distr:
                    self.distr[vector['className']] += 1
                else:
                    self.distr[vector['className']] = 1

            self.totalSize = len(vectors)

        def entropy(self):
            # calculate global entropy
            e = 0
            for key in self.distr:
                print key, self.distr[key]
                e += -(self.distr[key]/self.totalSize*math.log(self.distr[key]/self.totalSize, 2))
            return e

