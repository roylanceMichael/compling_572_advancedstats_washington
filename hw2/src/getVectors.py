import os
import sys
import re

class GetVectors:
	def __init__(self):
            self.vectors = []   # a list of feature vectors represented as dictionaries

        def binarize(self, line):
            # binarize the input: non-zero values are substituted by 1
            return re.sub(r':[123456789]\d*', r':1', line)


        def read_into_dicts(self, line_of_input):
            # turn each feature vector into a dictionary
            ilist = re.split('\s+', self.binarize(line_of_input).strip())
            vectDict = {}
            vectDict['className'] = ilist[0]   # 'className' : talk.politics.guns
            for i in ilist[0:]:
                pair = i.split(':')
                if len(pair) == 2:
                    vectDict[pair[0]] = pair[1]

            self.vectors.append(vectDict)