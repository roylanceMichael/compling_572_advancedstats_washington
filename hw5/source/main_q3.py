import sys
import train
import knn

def main():
    # get from command parameters
    trainFile = sys.argv[1]
    testFile = sys.argv[2]
    kval = float(sys.argv[3])
    similarity_func = float(sys.argv[4])
    kept_feat_list = sys.argv[5]
    sysOutput = sys.argv[6]

    tr = train.Train()
    # read the list of kept features:
    with open(kept_feat_list) as inF:
        keptF = inF.readline()
        while len(keptF.strip()) > 0:
            tr.getKeptFeatures(keptF)
            keptF = inF.readline()

    # read in the training file
    with open(trainFile) as inputF:
        l = inputF.readline()
        while len(l.strip()) > 0:
            tr.read_into_dicts(l)
            l = inputF.readline()

    reportingDict = {}
    for className in tr.classNames:
        reportingDict[className] = {}
        for otherClassName in tr.classNames:
            reportingDict[className][otherClassName] = 0

    # read in the test file and classify
    clusterResults = []

    correctTotal = 0
    totalVectors = 0
    currentIdx = 0

    with open(sysOutput, "w") as outputF:
        with open(testFile) as inputF:
            l = inputF.readline()
            while len(l.strip()) > 0:
                k = knn.KNN(kval, similarity_func, l, tr)

                # write to sys output
                outputF.write(k.reportSysRecord(currentIdx) + "\n")

                # get expected and actual
                (expected, actual) = k.classify()

                currentIdx += 1
                totalVectors += 1

                if expected == actual:
                    correctTotal += 1

                if expected in reportingDict:
                    if actual in reportingDict[expected]:
                        reportingDict[expected][actual] += 1
                    else:
                        reportingDict[expected][actual] = 1
                else:
                    reportingDict[expected] = { actual: 1 }

                l = inputF.readline()   

    headerColumn = "\t"
    for expectedKey in reportingDict:
        headerColumn = headerColumn + "\t" + expectedKey

    confusionMatrix = ""
    for expectedKey in reportingDict:
        confusionMatrix = confusionMatrix + expectedKey + "\t"
        for actualKey in reportingDict[expectedKey]:
            confusionMatrix = confusionMatrix + str(reportingDict[expectedKey][actualKey]) + "\t"
        confusionMatrix = confusionMatrix + "\n"

    print "Number of related features: %s" % (len(tr.allTrainFeatures))
    print "Test Accuracy: %s" % (float(correctTotal) / totalVectors) 
    print "Confusion matrix for the test data:\n"
    print "row is the truth, column is the system output\n"
    print headerColumn
    print confusionMatrix
    print "test accuracy =%s\n" % (float(correctTotal) / totalVectors) 

if __name__ == '__main__':
        main()
