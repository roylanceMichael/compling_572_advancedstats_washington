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
        self.allFeatures = {}
        self.expectedClassName = None


    def prepInput(self):
        # prepare the test string for distance calculation
        ilist = re.split('\s+', self.doc.strip())
        self.expectedClassName = ilist[0]
        for i in ilist[1:]:
            # get features
            pair = i.split(':')
            if len(pair) == 2:
                self.testVectDict[pair[0]] = float(pair[1])
                self.allFeatures[pair[0]] = None


    def getDistance(self, trainInstance):
        # choose Euclidean or Cosine
        if self.similarity_func == 1:
            return self.distanceEuclidean(trainInstance)
        elif self.similarity_func == 2:
            return self.distanceCosine(trainInstance)


    def getAllFeatures(self, trainInstance):
        # collect all features in test and train instance
        for feature in trainInstance:
            if feature not in self.allFeatures:
                self.allFeatures[feature] = None


    def distanceEuclidean(self, trainInstance):
        addSum = 0
        self.getAllFeatures(trainInstance)   # check all features
        for feature in self.allFeatures:
            if feature in self.testVectDict and feature in trainInstance:
                addSum += math.pow((trainInstance[feature] - self.testVectDict[feature]), 2)
            elif feature in trainInstance:
                addSum += math.pow(trainInstance[feature], 2)
        return math.sqrt(addSum)

#    def distanceCosine(self):

    def classify(self):
        resultsFromKnn = self.knn()

        allClasses = {}
        for result in resultsFromKnn:
            className = self.tr.getClassNameFromId(result)

            if className in allClasses:
                allClasses[className] += 1
            else:
                allClasses[className] = 1 

        for result in allClasses:
            print "%s -> %s " % (result, allClasses[result])

        return max(allClasses, key=allClasses.get)

    def knn(self):
        self.prepInput()
        kneighbours = {}   # here we store ids and distances of labelled instances
        # print self.doc
        for current_id in self.tr.vectors:
            d = self.getDistance(self.tr.vectors[current_id].features)

            if len(kneighbours) < self.kval:
                kneighbours[current_id] = d
            else:
                highestRowId = -1
                highestRowDistance = 0

                for rowId in kneighbours:
                    if kneighbours[rowId] > highestRowDistance:
                        highestRowId = rowId
                        highestRowDistance = kneighbours[rowId] 

                if d < highestRowDistance and highestRowId > 0:
                    del kneighbours[highestRowId]
                    kneighbours[current_id] = d


        return kneighbours
                    
                
                
