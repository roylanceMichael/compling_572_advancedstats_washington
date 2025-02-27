import re
import word

class Repository:
    def __init__(self):
        self.classProbability = {}
        self.featDict = {}   # {className1: {f1:#, f2:#, f3:#,..}, className2 : {}} - how many times different features are found in each class
        self.allFeatures = {}   # {f1: None, f2: None, } - all the features in the documents = V
        self.sizeClass = {}   # {className1: size, className2: size, ...}
        self.cachedTotalInstances = 0
        self.cachedTotalWords = 0
        self.trainVectors = []
        self.testVectors = []

    def buildWords(self, ilist, vectorType):
        words = [word.Word(ilist[0], 0, "class")]
        for i in ilist[1:]:
            pair = i.split(':')
            words.append(word.Word(pair[0], int(pair[1]), "word"))
        
        if vectorType == "train":
            self.trainVectors.append(words)
        else:
            self.testVectors.append(words)

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

    def getClassProbability(self, className, classPriorD):
        if self.cachedTotalInstances == 0:
            for key in self.classProbability:
                self.cachedTotalInstances += self.classProbability[key]

        classPriorDenominatorSmoothing = 0
        if (classPriorD > 0):
            classPriorDenominatorSmoothing = len(self.classProbability)

        return float(self.classProbability[className] + classPriorD) / (classPriorDenominatorSmoothing + self.cachedTotalInstances)

    def getClassCount(self, className):
        return self.classProbability[className]

    def readIntoDictsBinarized(self, line_of_input, vectorType):
        # fill three dictionaries
        ilist = re.split('\s+', self.binarize(line_of_input).strip())

        self.buildWords(ilist, vectorType)

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

        # get class sizes - how many words there are
        for className in self.featDict:
            self.sizeClass[className] = 0
            for feat in self.featDict[className]:
                self.sizeClass[className] += self.featDict[className][feat]

    def readIntoDictsNormal(self, line_of_input, vectorType):
        # fill three dictionaries
        ilist = re.split('\s+', line_of_input.strip())

        self.buildWords(ilist, vectorType)

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
                        self.featDict[className][pair[0]] += int(pair[1])
                    else:
                        self.featDict[className][pair[0]] = int(pair[1])
                else:
                    self.featDict[className] = { pair[0] : int(pair[1])}

        # get class sizes - how many words there are
        for className in self.featDict:
            self.sizeClass[className] = 0
            for feat in self.featDict[className]:
                self.sizeClass[className] += self.featDict[className][feat]                 

    def addMissingTerms(self):
        for key in self.allFeatures:
            for className in self.featDict:
                if key not in self.featDict[className]:        
                    self.featDict[className][key] = 0
