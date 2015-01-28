import sys
import train
import knn
#import reportFiles


def main():
    # get from command parameters
    trainFile = sys.argv[1]
    testFile = sys.argv[2]
#    kval = float(sys.argv[3])
#    similarity_func = float(sys.argv[4])
#    sysOutput = sys.argv[5]

    tr = train.Train()
    # read in the training file
    with open(trainFile) as inputF:
        l = inputF.readline()
        while len(l.strip()) > 0:
            tr.read_into_dicts(l)
            l = inputF.readline()

#    for key in tr.vectors:
#        print key, tr.vectors[key].className

    # read in the test file and classify
    with open(testFile) as inputF:
        l = inputF.readline()
        while len(l.strip()) > 0:
#            knn.KNN(l, tr)
            l = inputF.readline()    

    # report sys file
#    reportFiles.reportSysFile(sysOutput, bernNB, trainFile, testFile)

    # print confusion matrix
#    reportFiles.printConfusionMatrix(bernNB, trainFile, testFile, vect)

if __name__ == '__main__':
        main()
