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

    print "preparing the classifier..."
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
    
    vect.addMissingTerms()
    # for now hardcoding classPriorD and condProbD
    bernNB = bernoulli.Bernoulli(vect.vectors, vect.featDict, 1, 1, lines)
    bernNB.bernoulliNB()

    print "classifying test file..."
    train = getVectors.GetVectors()
    # read in the training file for classification
    with open(trainFile) as inputF:
        l = inputF.readline()
        i = 0
        while len(l.strip()) > 0:
            w = train.getWords(l)
            bernNB.reportClassificationResult(i, w)
            l = inputF.readline()
            i += 1


    # read in the test file for classification
#    with open(testFile) as inputF:
#        l = inputF.readline()
#        while len(l.strip()) > 0:
#            testVectorRepo.read_into_dicts(l)
#            l = inputF.readline()

    # report model file
    reportFiles.reportModelFile(modelFile, bernNB.classes)



    # report sys file
#    reportFiles.reportSysFile(sysFile, rootS, allVectors())

    # report confusion matrix
#    reportFiles.printConfusionMatrix(rootS, trainVectorRepo.getAllVectors(), "training")
#    reportFiles.printConfusionMatrix(rootS, testVectorRepo.getAllVectors(), "testing")

if __name__ == '__main__':
        main()
