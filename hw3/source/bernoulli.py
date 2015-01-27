from __future__ import division
from decimal import Decimal
import math
import operator
import classInstance

class Bernoulli:
    def __init__(self, repo, classPriorD, condProbD, lines):
        self.classes = {}
        self.featDict = repo.featDict
        self.classPriorD = classPriorD
        self.condProbD = condProbD
        self.lines = lines
        self.repo = repo

        self.classificationCache = {}
            
    def bernoulliNB(self):
        for key in self.featDict:
            prior =  self.repo.getClassProbability(key, self.classPriorD) 
            logprior = math.log10(prior)
            probs = {}

            commonDenominator = float(len(self.featDict) + self.repo.getClassCount(key))

            for feat in self.featDict[key]:
                condProb = (self.featDict[key][feat] + self.condProbD) / commonDenominator
                logCondProb = math.log10(condProb)
                probs[feat] = [condProb, logCondProb]

            cI = classInstance.ClassInstance(key, prior, logprior, None, None, probs)
            self.classes[key] = cI

    def classify(self, wordlist):
        # working with self.classes where all the training probabilities are stored
        instanceName = wordlist[0].value
        classification = {}

        currentWordList = {}
        for word in wordlist[1:]:
            currentWordList[word.value] = None
        
        for className in self.classes:
            wordGivenClassProb = self.classes[className].logprior

            # this is p(xj|ci)
            for word in currentWordList:
                if word in self.classes[className].probs:
                    wordGivenClassProb += self.classes[className].probs[word][1]

            for word in self.repo.featDict[className]:
                if word not in currentWordList:
                    wordGivenClassProb += math.log10(1-self.classes[className].probs[word][0])
            
            classification[className] = wordGivenClassProb

        return instanceName, classification

    def getClassificationResultForConfusionMatrix(self, key):
        return self.classificationCache[key]
                           
    def reportClassificationResultForSysFile(self, key, idx, wordList):
        # for sys file
        (name, clf) = self.classify(wordList)

        sortedClassification = sorted(clf, key=clf.get, reverse=True)

        self.classificationCache[key] = (name, sortedClassification[0])

        stringBuilder = ""

        # converting and normalizing
        maxkey = max(clf.iteritems(), key=operator.itemgetter(1))[0]
        denom = pow(Decimal(10), Decimal(clf[maxkey]))   # normalize by this

        for className in sortedClassification:
            conv = pow(Decimal(10), Decimal(clf[className]))
            norm = Decimal(conv)/Decimal(denom)
            stringBuilder += str(className) + " " + str(norm) + " "

        return "array:"+ str(idx) + "\t" + name + "\t" + stringBuilder
        
