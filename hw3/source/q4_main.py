import sys
import repository
import multinomial
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
    binarizeFeatures = int(sys.argv[7])

    vect = repository.Repository()
    # read in the training file
    with open(trainFile) as inputF:
        lines = 1
        l = inputF.readline()
        while len(l.strip()) > 0:
            if binarizeFeatures == 1:
                vect.readIntoDictsBinarized(l)
            else:
                vect.readIntoDictsNormal(l)
                
            l = inputF.readline()
            if len(l) != 0: 
                lines += 1
    
    multinomNB = multinomial.Multinomial(vect, classPriorDelta, condProbDelta, lines)
    multinomNB.multinomialNB()

    # report sys file
    reportFiles.reportSysFile(sysOutput, multinomNB, trainFile, testFile)

    # report model file
    reportFiles.reportModelFile(modelFile, multinomNB.classes)

    # print confusion matrix
    reportFiles.printConfusionMatrix(multinomNB, trainFile, testFile, vect)

if __name__ == '__main__':
        main()
