import s
import re
import dt
import sys
import reportFiles
import getVectors


def main():
    # get from command parameters
    trainFile = sys.argv[1]
    testFile = sys.argv[2]
    maxDepth = int(sys.argv[3])
    minGain = float(sys.argv[4])
    modelFile = sys.argv[5]
    sysFile = sys.argv[6]

    print "working with %s %s %s %s %s %s" % (trainFile, testFile, maxDepth, minGain, modelFile, sysFile)

    # acc file is implied, we'll use print
    # to communicate with that file
    trainVectorRepo = getVectors.GetVectors(3, 0.01)
    testVectorRepo = getVectors.GetVectors(3, 0.01)

    dtree = dt.DT()

    # read in the training file into our repository
    with open(trainFile) as inputF:
        l = inputF.readline()
        while len(l.strip()) > 0:
            trainVectorRepo.read_into_dicts(l)
            l = inputF.readline()

    # read in the test file into our repository
    with open(testFile) as inputF:
        l = inputF.readline()
        while len(l.strip()) > 0:
            testVectorRepo.read_into_dicts(l)
            l = inputF.readline()

    # create the root item
    rootS = s.S(trainVectorRepo, 0)
    rootS.addVectors(trainVectorRepo.vectors)
    rootS.informationGain()

    # report model file
    reportFiles.reportModelFile(modelFile, rootS)

    def allVectors():
        for vector in trainVectorRepo.getAllVectors():
            yield vector
        for vector in testVectorRepo.getAllVectors():
            yield vector

    # report sys file
    reportFiles.reportSysFile(sysFile, rootS, allVectors())

    # report confusion matrix
    reportFiles.printConfusionMatrix(rootS, trainVectorRepo.getAllVectors(), "training")
    reportFiles.printConfusionMatrix(rootS, testVectorRepo.getAllVectors(), "testing")

if __name__ == '__main__':
        main()
