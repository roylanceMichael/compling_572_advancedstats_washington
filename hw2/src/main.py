import sys
import re
import getVectors
import dt
import s


def main():
    getV = getVectors.GetVectors()
    dtree = dt.DT()

    iFile = sys.argv[1]
    inputF = open(iFile)
    l = inputF.readline()
    while len(l.strip()) > 0:
        getV.read_into_dicts(l)
        l = inputF.readline()

    inputF.close()

    newS = s.S()
    newS.addVectors(getV.vectors)

    # let's calculate local probabilities
    for vectorTuple in newS.splitVectorsOnFeatures():
        print "feature split 1 %s %s with size %s" % (vectorTuple[0].featureSplitOn, vectorTuple[0].hasFeatureSplitOn, vectorTuple[0].totalSize)
        print "feature split 2 %s %s with size %s" % (vectorTuple[1].featureSplitOn, vectorTuple[1].hasFeatureSplitOn, vectorTuple[1].totalSize)

if __name__ == '__main__':
        main()
