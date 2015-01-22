from __future__ import division
from decimal import Decimal
import math
import operator
import getVectors
import classInstance


class Bernoulli:
    def __init__(self, vectors, featDict, classPriorD, condProbD, lines):
        self.vectors = vectors
        self.featDict = featDict
        self.classPriorD = classPriorD
        self.condProbD = condProbD
        self.lines = lines
        self.classes = {}
            

    def bernoulliNB(self):
        for key in self.vectors:
            prior = (len(self.vectors[key]) + self.classPriorD) /(len(self.vectors) + self.lines)
            logprior = math.log10(prior)
            probs = {}
            for feat in self.featDict[key]:
                condProb = (self.featDict[key][feat] + self.condProbD) / (2 + len(self.vectors[key]))
                logCondProb = math.log10(condProb)
                probs[feat] = [condProb, logCondProb]

            cI = classInstance.ClassInstance(key, prior, logprior, probs)
            self.classes[key] = cI


    def classify(self, wordlist):
        # working with self.classes where all the training probabilities are stored
        instanceName = wordlist[0]
        classification = {}
        
        for className in self.classes:
            pclass = self.classes[className].logprior
            for word in wordlist[1:]:
                if word in self.classes[className].probs:
                    pclass = pclass * self.classes[className].probs[word][1]
                else:
                    pclass = pclass * math.log10(1-self.classes[className].probs[word][0])
            classification[className] = pclass

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



#        def reportClassPrediction(self, vectorInstance):
#            if self.highestIgInstance == None:
#                return self.highestClass

#            nextFeature = self.highestIgInstance.splitWithFeature.featureSplitOn

#            if nextFeature in vectorInstance.features:
#                return self.highestIgInstance.splitWithFeature.reportClassPrediction(vectorInstance)

#            return self.highestIgInstance.splitWithoutFeature.reportClassPrediction(vectorInstance)

#        def reportClassificationResult(self, vectorInstance):
#            if self.highestIgInstance == None:
#                strBuilder = "array:%s " % (vectorInstance.id)
#                for key in self.distr:
#                    strBuilder = "%s %s %s " % (strBuilder, key, self.distr[key])

#                return strBuilder
#            else:
#                featureToCompare = self.highestIgInstance.splitWithFeature.featureSplitOn

#                if featureToCompare in vectorInstance.features:
#                    return self.highestIgInstance.splitWithFeature.reportClassificationResult(vectorInstance)
#                else:
#                    return self.highestIgInstance.splitWithoutFeature.reportClassificationResult(vectorInstance)

#        def reportAdditionalFeatures(self):
#            returnStr = " %s " % (self.totalSize)

#            for className in self.distr:
#                returnStr = "%s %s %s " % (returnStr, className, self.distr[className] / self.totalSize)

#            return returnStr

#        def reportSelf(self):
#            if len(self.featureSplitOn) == 0:
#                return ""

#            if self.hasFeatureSplitOn:
#                return self.featureSplitOn
#            else:
#                return "!" + self.featureSplitOn

#        def reportConcat(self):
#            if self.highestIgInstance == None or len(self.featureSplitOn) == 0:
#                return ""
#            else:
#                return "&"


        
