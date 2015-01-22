import sys
import getVectors
import bernoulli
import classInstance
import reportFiles


def main():
    # get from command parameters
    trainFile = sys.argv[1]
    modelFile = sys.argv[2]
#    testFile = sys.argv[2]
#    classPriorD = int(sys.argv[3])
#    condProbD = float(sys.argv[4])
#    modelFile = sys.argv[5]
#    sysFile = sys.argv[6]

    vect = getVectors.GetVectors()

    # read in the training file
    with open(trainFile) as inputF:
        lines = 0
        l = inputF.readline()
        while len(l.strip()) > 0:
            vect.read_into_dicts(l)
            l = inputF.readline()
            if len(l) != 0: 
                lines += 1
    
#    print vect.allFeatures
    vect.addMissingTerms()
#    print vect.featDict
    # for now hardcoding classPriorD and condProbD
    bernNB = bernoulli.Bernoulli(vect.vectors, vect.featDict, 1, 1, lines)
    bernNB.bernoulliNB()

#    for className in bernNB.classes:
#        print bernNB.classes[className].probs



    # read in the test file into our repository
#    with open(testFile) as inputF:
#        l = inputF.readline()
#        while len(l.strip()) > 0:
#            testVectorRepo.read_into_dicts(l)
#            l = inputF.readline()

    # report model file
    reportFiles.reportModelFile(modelFile, bernNB.classes)

#    def allVectors():
#        for vector in trainVectorRepo.getAllVectors():
#            yield vector
#        for vector in testVectorRepo.getAllVectors():
#            yield vector

    # report sys file
#    reportFiles.reportSysFile(sysFile, rootS, allVectors())

    # report confusion matrix
#    reportFiles.printConfusionMatrix(rootS, trainVectorRepo.getAllVectors(), "training")
#    reportFiles.printConfusionMatrix(rootS, testVectorRepo.getAllVectors(), "testing")

if __name__ == '__main__':
        main()
