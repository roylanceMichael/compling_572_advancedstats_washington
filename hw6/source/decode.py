from __future__ import division
import re
import math

class Decode:
    def __init__(self, m):
        self.features = m.features
        self.doc = {}   # {f1:None, f2:None, f3:None, ...}
        self.docID = []
        self.condProb = {}   # {className: prob, className: prob, ...} - not normalized yet
        self.Z = 0
        self.P = {}   # {className: prob, className: prob, ...} - final


    def read_into_dicts(self, line_of_input, n):
        ilist = re.split('\s+', line_of_input.strip())

        self.docID = [n, ilist[0]]

        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:
                self.doc[pair[0]] = None


    def getCondProb(self):
        for className in self.features:
            power = self.features[className]['<default>']
            for f in self.doc:
                if f in self.features[className]:
                    power += self.features[className][f]

            condProb = math.exp(power)
#            print condProb
            self.condProb[className] = condProb
                    

    def normaliZ(self):
        for className in self.condProb:
            self.Z += self.condProb[className]


    def classCondProb(self):
        self.normaliZ()
        for className in self.condProb:
#            print className
#            print self.condProb[className]
 #           print self.Z
            self.P[className] = float(self.condProb[className] / self.Z)


    def reportSysRecord(self):
        stringBuilder = ""
        for className in self.P:
            stringBuilder += str(className) + " " + str(self.P[className]) + "\t "
        
        return "array:"+ str(self.docID[0]) + "\t" + str(self.docID[1]) + "\t" + stringBuilder 
