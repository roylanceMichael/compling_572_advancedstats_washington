import sys
import getVectors
import bernoulli
import classInstance
import reportFiles


def main():
    # get from command parameters
    trainFile = sys.argv[1]
    testFile = sys.argv[2]
    modelFile = sys.argv[3]

    print "preparing the classifier..."
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
    bernNB = bernoulli.Bernoulli(vect, 1, 1, lines)
    bernNB.bernoulliNB()

    print "classifying test file..."
    train = getVectors.GetVectors()
    # read in the training file for classification
    with open(testFile) as inputF:
        l = inputF.readline()
        i = 0
        while len(l.strip()) > 0:
            w = train.getWords(l)
            bernNB.reportClassificationResult(i, w)
            l = inputF.readline()
            i += 1

    # report model file
    reportFiles.reportModelFile(modelFile, bernNB.classes)

if __name__ == '__main__':
        main()
