from __future__ import division
from decimal import Decimal
import math
import operator
import classInstance

class Multinomial:
    def __init__(self, repo, classPriorD, condProbD, lines):
        self.classes = {}
        self.featDict = repo.featDict
        self.classPriorD = classPriorD
        self.condProbD = condProbD
        self.lines = lines
        self.repo = repo
        self.lenAllFeat = len(self.repo.allFeatures)

        self.classificationCache = {}

    def multinomialNB(self):
        for key in self.featDict:
            prior =  self.repo.getClassProbability(key, self.classPriorD) 
            logprior = math.log10(prior)
            commonDenominator = float(self.repo.sizeClass[key] + self.lenAllFeat)
            condProb_ooc = (self.condProbD) / commonDenominator
            logCondProb_ooc = math.log10(condProb_ooc)
            probs = {}

            for feat in self.featDict[key]:
                condProb = (self.featDict[key][feat] + self.condProbD) / commonDenominator
                logCondProb = math.log10(condProb)
                probs[feat] = [condProb, logCondProb]

            cI = classInstance.ClassInstance(key, prior, logprior, condProb_ooc, logCondProb_ooc, probs)
            self.classes[key] = cI


    def classify(self, wordlist):
        # working with self.classes where all the training probabilities are stored
        instanceName = wordlist[0].value
        classification = {}

        currentWordList = {}
        for word in wordlist[1:]:
            currentWordList[word] = None
        
        for className in self.classes:
            wordGivenClassProb = self.classes[className].logprior   #

            # this is p(c|doc)
            for word in currentWordList:
                if word.value in self.classes[className].probs:
                    wordGivenClassProb += self.classes[className].probs[word.value][1] * word.count #
                else:
                    wordGivenClassProb += self.classes[className].logCondProb_ooc * word.count   #

            classification[className] = wordGivenClassProb

        return instanceName, classification

    def getClassificationResultForConfusionMatrix(self, key):
        return self.classificationCache[key]
                           
    def reportClassificationResultForSysFile(self, key, idx, wordList):
        # for sys file
        (name, clf) = self.classify(wordList)
        stringBuilder = ""

        sortedClassification = sorted(clf, key=clf.get, reverse=True)

        self.classificationCache[key] = (name, sortedClassification[0])

        for className in sortedClassification:
            stringBuilder += str(className) + " " +  str(clf[className]) + " "
        
        return "array:"+ str(idx) + "\t" + name + "\t" + stringBuilder
        

