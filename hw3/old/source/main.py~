import sys
import getVectors
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

    vect = getVectors.GetVectors()
    # read in the training file
    with open(trainFile) as inputF:
        lines = 1
        l = inputF.readline()
        while len(l.strip()) > 0:
            vect.read_into_dicts(l)
            l = inputF.readline()
            if len(l) != 0: 
                lines += 1
    
    vect.addMissingTerms()

    # for now hardcoding classPriorD and condProbD
    bernNB = bernoulli.Bernoulli(vect, classPriorDelta, condProbDelta, lines)
    bernNB.bernoulliNB()

    # report sys file
    reportFiles.reportSysFile(sysOutput, bernNB, trainFile, testFile)

    # report model file
    reportFiles.reportModelFile(modelFile, bernNB.classes)

    # print confusion matrix
    reportFiles.printConfusionMatrix(bernNB, trainFile, testFile, vect)

if __name__ == '__main__':
        main()
