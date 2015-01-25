import re


class GetVectors:
    def __init__(self):
        self.classProbability = {}
        self.featDict = {}   # {className1: {f1:#, f2:#, f3:#,..}, className2 : {}} - how many times different features are found in each class
        self.allFeatures = {}   # {f1: None, f2: None, } - all the features in the documents = V
        self.cachedTotalWords = 0
        self.cachedTotalInstances = 0

    def binarize(self, line):
        # binarize the input: non-zero values are substituted by 1
        return re.sub(r':[123456789]\d*', r':1', line)

    def getFeatureProbability(self, feature): 
        if feature not in self.allFeatures or self.allFeatures[feature] == 0:
            return 0

        if self.cachedTotalWords == 0:
            for key in self.allFeatures:
                self.cachedTotalWords += self.allFeatures[key]

        return float(self.allFeatures[feature]) / self.cachedTotalWords

    def getClassProbability(self, className):
        if self.cachedTotalInstances == 0:
            for key in self.classProbability:
                self.cachedTotalInstances += self.classProbability[key]

        return float(self.classProbability[className]) / self.cachedTotalInstances

    def getClassCount(self, className):
        return self.classProbability[className]

    def read_into_dicts(self, line_of_input):
        # fill three dictionaries
        ilist = re.split('\s+', self.binarize(line_of_input).strip())

        className = ilist[0]

        if className in self.classProbability:
            self.classProbability[className] += 1
        else:
            self.classProbability[className] = 1

        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:

                # fill self.allFeatures
                self.allFeatures[pair[0]] = None
                
                # fill self.featDict
                if className in self.featDict:
                    if pair[0] in self.featDict[className]:
                        self.featDict[className][pair[0]] += 1
                    else:
                        self.featDict[className][pair[0]] = 1
                else:
                    self.featDict[className] = { pair[0] : 1}

    def addMissingTerms(self):
        for key in self.allFeatures:
            for className in self.featDict:
                if key not in self.featDict[className]:        
                    self.featDict[className][key] = 0

    def getWords(self, line):
        ilist = re.split('\s+', line.strip())

        words = [ilist[0]]
        for i in ilist[1:]:
            pair = i.split(':')
            words.append(pair[0])
        return words
