from __future__ import division
from decimal import Decimal
import math
import operator
import getVectors
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


    def multinomialNB(self):
        for key in self.featDict:
            prior =  self.repo.getClassProbability(key, self.classPriorD) 
            logprior = math.log10(prior)
            condProb_ooc = (self.condProbD) / float(self.repo.sizeClass[key] + self.lenAllFeat)
            logCondProb_ooc = math.log10(condProb_ooc)
            probs = {}

            for feat in self.featDict[key]:
                condProb = (self.featDict[key][feat] + self.condProbD) / float(self.repo.sizeClass[key] + self.lenAllFeat)
                logCondProb = math.log10(condProb)
                probs[feat] = [condProb, logCondProb]

            cI = classInstance.ClassInstance(key, prior, logprior, condProb_ooc, logCondProb_ooc, probs)
            self.classes[key] = cI


    def classify(self, wordlist):
        # working with self.classes where all the training probabilities are stored
        instanceName = wordlist[0]
        classification = {}

        currentWordList = {}
        for word in wordlist[1:]:
            currentWordList[word] = None
        
        for className in self.classes:
            wordGivenClassProb = self.classes[className].logprior   #

            # this is p(c|doc)
            for word in currentWordList:
                if word in self.classes[className].probs:
                    wordGivenClassProb += self.classes[className].probs[word][1]   #
                else:
                    wordGivenClassProb += self.classes[className].logCondProb_ooc   #

            classification[className] = math.pow(10, wordGivenClassProb)

        return instanceName, classification

    def getClassificationResultForConfusionMatrix(self, wordList):
        (expected, clf) = self.classify(wordList)
        return (expected, sorted(clf, key=clf.get, reverse=True)[0])
                           
    def reportClassificationResultForSysFile(self, idx, wordList):
        # for sys file
        (name, clf) = self.classify(wordList)
        stringBuilder = ""

        for className in sorted(clf, key=clf.get, reverse=True):
            stringBuilder += str(className) + " " +  str(clf[className]) + " "
        
        return "array:"+ str(idx) + "\t" + name + "\t" + stringBuilder
        

