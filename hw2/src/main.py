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
    sysOutput = sys.argv[6]

    # acc file is implied, we'll use print
    # to communicate with that file
    vectorRepo = getVectors.GetVectors(3, 0.01)
    dtree = dt.DT()

    # read in the training file into our repository
    with open(trainFile) as inputF:
        l = inputF.readline()
        while len(l.strip()) > 0:
            vectorRepo.read_into_dicts(l)
            l = inputF.readline()

    # create the root item
    rootS = s.S(vectorRepo, 0)
    rootS.addVectors(vectorRepo.vectors)
    rootS.informationGain()

    # report to file now
    reportFiles.reportModelFile(modelFile, rootS)

if __name__ == '__main__':
        main()
