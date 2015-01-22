from __future__ import division
from decimal import Decimal
import math
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
#                print key + "\t" + feat + "\t" + str(condProb) + "\n"
                logCondProb = math.log10(condProb)
                probs[feat] = [condProb, logCondProb]

            cI = classInstance.ClassInstance(key, prior, logprior, probs)
            self.classes[key] = cI



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


        
