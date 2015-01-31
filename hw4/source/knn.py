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
            else:
                addSum += math.pow(trainInstance[feature], 2)
        return math.sqrt(addSum)

#    def distanceCosine(self):

    def classify(self):
        resultsFromKnn = self.knn()

        allClasses = {}
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
                    
        return highestClass

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
                    
                
                
