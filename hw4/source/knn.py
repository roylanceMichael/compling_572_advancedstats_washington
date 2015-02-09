import math
import re
import operator


class KNN:
    def __init__(self, kval, similarity_func, l, tr):
        self.kval = kval
        self.similarity_func = similarity_func
        self.doc = l
        self.tr = tr
        self.testVectDict = {}
        self.expectedClassName = None
        self.actualClassName = None

        self.prepInput()

    def prepInput(self):
        # prepare the test string for distance calculation
        ilist = re.split('\s+', self.doc.strip())
        self.expectedClassName = ilist[0]
        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:
                self.testVectDict[pair[0]] = float(pair[1])

    def getDistance(self, trainInstance):
        # choose Euclidean or Cosine
        if self.similarity_func == 1:
            return self.distanceEuclidean(trainInstance)
        elif self.similarity_func == 2:
            return self.distanceCosine(trainInstance)

    def distanceEuclidean(self, trainInstance):
        addSum = 0

        for feature in trainInstance:
            if feature in self.testVectDict:
                addSum += math.pow((trainInstance[feature] - self.testVectDict[feature]), 2)
            elif feature in trainInstance:
                addSum += math.pow(trainInstance[feature], 2)

        for feature in self.testVectDict:
            if feature not in trainInstance and feature in self.tr.allTrainFeatures:
                addSum += math.pow(self.testVectDict[feature], 2)

        return math.sqrt(addSum)

    def distanceCosine(self, trainInstance):
        # x1 * x2 / x1^2 + x2^2
        numeratorSum = 0
        denominatorTrainSum = 0
        denominatorTestSum = 0

        for feature in trainInstance:
            if feature in self.testVectDict:
                numeratorSum += trainInstance[feature] * self.testVectDict[feature]
                denominatorTrainSum += math.pow(trainInstance[feature], 2)
                denominatorTestSum += math.pow(self.testVectDict[feature], 2) 
            elif feature in trainInstance:
                denominatorTrainSum += math.pow(trainInstance[feature], 2)

        for feature in self.testVectDict:
            if feature not in trainInstance and feature in self.tr.allTrainFeatures:
                denominatorTestSum += math.pow(self.testVectDict[feature], 2) 

        if denominatorTrainSum == 0 or denominatorTestSum == 0:
            return 1

        cosineDist = float(numeratorSum) / (math.sqrt(denominatorTrainSum) * math.sqrt(denominatorTestSum))
        return 1 - cosineDist

    # note: call reportSysRecord first
    def classify(self):
        if self.expectedClassName == None:
            raise Exception("Call reportSysRecord first!")
        return (self.expectedClassName, self.actualClassName)

    def reportSysRecord(self, idx):
        resultsFromKnn = self.knn()

        allClasses = {}
        for className in self.tr.classNames:
            allClasses[className] = 0

        highestClass = None
        highestCount = 0
        for result in resultsFromKnn:
            className = self.tr.getClassNameFromId(result)

            if className in allClasses:
                allClasses[className] += 1

                if allClasses[className] > highestCount:
                    highestCount = allClasses[className]
                    highestClass = className
            else:
                allClasses[className] = 1 

                if allClasses[className] > highestCount:
                    highestCount = allClasses[className]
                    highestClass = className

        sortedResults = sorted(allClasses, key=allClasses.get, reverse=True)

        self.actualClassName = sortedResults[0]

        denom = 0
        for className in allClasses:
            denom += allClasses[self.actualClassName]

        stringBuilder = ""
        for className in sortedResults:
            norm = float(allClasses[className])/denom
            stringBuilder += str(className) + " " + str(norm) + " "
        
        return "array:"+ str(idx) + "\t" + self.actualClassName + "\t" + stringBuilder 

    def knn(self):
        kneighbours = {}   # here we store ids and distances of labelled instances

        for current_id in self.tr.vectors:
            d = self.getDistance(self.tr.vectors[current_id].features)

            if len(kneighbours) < self.kval:
                kneighbours[current_id] = d
            else:
                # are we lower than the highest row distance?
                highestRowId = 0
                highestRowDistance = 0
                for rowId in kneighbours:
                    if kneighbours[rowId] > highestRowDistance:
                        highestRowDistance = kneighbours[rowId]
                        highestRowId = rowId

                if d < highestRowDistance:
                    del kneighbours[highestRowId]
                    kneighbours[current_id] = d

        return kneighbours
                    
                
                
