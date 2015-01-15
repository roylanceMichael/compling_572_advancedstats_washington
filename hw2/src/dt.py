from __future__ import division
import os
import sys
import re
import math
import getVectors
from decimal import Decimal


class DT:
#	def __init__(self):

        def findClassDistribution(self, vectors):
            # how many different class labels we have
            distr = {}   # 'talk.politics.guns' : 100
            for vector in vectors:
                if vector['className'] in distr:
                    distr[vector['className']] += 1
                else:
                    distr[vector['className']] = 1

            return distr, len(vectors)


        def entropy(self, params):
            # calculate global entropy
            total = params[1]
            print total
            e = 0
            for key in params[0]:
                print key, params[0][key]
                e += -(params[0][key]/total*math.log(params[0][key]/total, 2))
            return e


#        def IG(self, line_of_input):
            # calculate information gain

