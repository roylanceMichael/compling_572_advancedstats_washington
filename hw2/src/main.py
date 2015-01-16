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

    newS.informationGain()

    if newS.highestIgInstance != None:
        featureSplit = newS.highestIgInstance.splitWithFeature.featureSplitOn
        featureWithTotal = newS.highestIgInstance.splitWithFeature.totalSize
        featureWithoutTotal = newS.highestIgInstance.splitWithoutFeature.totalSize
        informationGain = newS.highestIgInstance.calculateInformationGain()

        print "feature %s (%s/%s) with %s was highest!" % (featureSplit, featureWithTotal, featureWithoutTotal, informationGain)
    else:
        print "for some odd reason, no highest feature was found"

    # let's calculate local probabilities
    # for vectorTuple in newS.splitVectorsOnFeatures():
        # pass
        # print "feature split 1 %s %s with size %s" % (vectorTuple[0].featureSplitOn, vectorTuple[0].hasFeatureSplitOn, vectorTuple[0].totalSize)
        # print "feature split 2 %s %s with size %s" % (vectorTuple[1].featureSplitOn, vectorTuple[1].hasFeatureSplitOn, vectorTuple[1].totalSize)

if __name__ == '__main__':
        main()
