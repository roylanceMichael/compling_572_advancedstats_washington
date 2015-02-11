from __future__ import division
import re
import math

class Decode:
    def __init__(self):
        self.doc = {}   # {className: {f1:None, f2:None, f3:None, ...}}


    def read_into_dicts(self, line_of_input):
        ilist = re.split('\s+', line_of_input.strip())

        self.doc[ilist[0]] = {}

        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:
                self.doc[ilist[0]][pair[0]] = None


