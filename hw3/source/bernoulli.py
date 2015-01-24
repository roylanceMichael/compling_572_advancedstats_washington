from __future__ import division
from decimal import Decimal
import math
import operator
import getVectors
import classInstance

class Bernoulli:
    def __init__(self, repo, classPriorD, condProbD, lines):
        self.classes = {}
        self.featDict = repo.featDict
        self.classPriorD = classPriorD
        self.condProbD = condProbD
        self.lines = lines
        self.repo = repo
            
    def bernoulliNB(self):
        for key in self.featDict:
            prior = (len(self.featDict[key]) + self.classPriorD) / (len(self.featDict) + self.lines)
            logprior = math.log10(prior)
            probs = {}

            for feat in self.featDict[key]:
                condProb = (self.featDict[key][feat] + self.condProbD) / (len(self.featDict) + len(self.featDict[key]))
                logCondProb = math.log10(condProb)
                probs[feat] = [condProb, logCondProb]

            cI = classInstance.ClassInstance(key, prior, logprior, probs)
            self.classes[key] = cI

    def classify(self, wordlist):
        # working with self.classes where all the training probabilities are stored
        instanceName = wordlist[0]
        classification = {}

        currentWordList = {}
        for word in wordlist[1:]:
            currentWordList[word] = None
        
        for className in self.classes:
            wordGivenClassProb = self.classes[className].logprior

            # this is p(xj|ci)
            for word in currentWordList:
                wordGivenClassProb += self.classes[className].probs[word][1]

            for word in self.repo.featDict[className]:
                if word not in currentWordList:
                    wordGivenClassProb += math.log10(1-self.classes[className].probs[word][0])
                
            classification[className] = math.pow(10, wordGivenClassProb)

        # update probability for classification
        # totalAmount = 0
        # for className in classification:
        #     totalAmount += classification[className]

        # for className in classification:
        #     classification[className] = float(classification[className]) / totalAmount

        return instanceName, classification
                    
            
    def reportClassificationResult(self, i, w):
        # for sys file
        c = self.classify(w)
        name = c[0]
        clf = c[1]
        string = ""

        for w in sorted(clf, key=clf.get, reverse=True):
            string += str(w) + " " +  str(clf[w]) + " "
        print "array:"+ str(i) + "\t" + name + "\t" + string
        
