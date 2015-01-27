import re
import sys
import repository
import bernoulli
import classInstance
import reportFiles


def main():
    # get from command parameters
    trainFile = sys.argv[1]
    testFile = sys.argv[2]
    classPriorDelta = float(sys.argv[3])
    condProbDelta = float(sys.argv[4])

    modelFile = sys.argv[5]
    sysOutput = sys.argv[6]

    vect = repository.Repository()
    # read in the training file
    with open(trainFile) as inputF:
        lines = 1
        l = inputF.readline()
        while len(l.strip()) > 0:
            vect.readIntoDictsBinarized(l, "train")
            l = inputF.readline()
            if len(l) != 0: 
                lines += 1

    vect.addMissingTerms()

    with open(testFile) as inputF:
        lines = 1
        l = inputF.readline()
        while len(l.strip()) > 0:
            ilist = re.split('\s+', vect.binarize(l).strip())
            vect.buildWords(ilist, "test")
            l = inputF.readline()
            if len(l) != 0: 
                lines += 1

    # for now hardcoding classPriorD and condProbD
    bernNB = bernoulli.Bernoulli(vect, classPriorDelta, condProbDelta, lines)
    bernNB.bernoulliNB()

    print "how long did this take?"
    # report sys file
    reportFiles.reportSysFile(sysOutput, bernNB, vect)

    # report model file
    reportFiles.reportModelFile(modelFile, bernNB.classes)

    # print confusion matrix
    reportFiles.printConfusionMatrix(bernNB, vect)

if __name__ == '__main__':
        main()
