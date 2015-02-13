import sys
import operator
import model
import decode


def main():
    # get from command parameters
    testData = sys.argv[1]
    modelFile = sys.argv[2]
    sysOutput = sys.argv[3]

    m = model.Model()
    # read the model from file:
    with open(modelFile) as inF:
        mF_ = inF.readline()
	mF = mF_.strip()
	className = ""
        while len(mF) > 0:
            if len(mF.split()) > 2:
		    className = mF.split()[-1]
		    mF_ = inF.readline()
		    mF = mF_.strip()
	    m.read_into_dicts(mF, className)
	    mF_ = inF.readline()
	    mF = mF_.strip()

    # for reporting accuracy
    reportingDict = {}
    for className in m.features:
        reportingDict[className] = {}
        for otherClassName in m.features:
            reportingDict[className][otherClassName] = 0

    allClasses = {}
    correctTotal = 0
    totalVectors = 0

    # read in the test file
    with open(sysOutput, "w+") as outputF:
        with open(testData) as inputF:
            l = inputF.readline()
            n = 0
            while len(l.strip()) > 0:
                d = decode.Decode(m)
                d.read_into_dicts(l, n)
                d.getCondProb()
                d.classCondProb()

                # write to sys output
                outputF.write(d.reportSysRecord() + "\n")

                # return accuracy info [expected className; received className]
                acc = d.reportAcc()
                expected = acc[0]
                actual = acc[1]

                totalVectors += 1

                if expected == actual:
                    correctTotal += 1

                # append to allClasses
                if expected in allClasses:
                    allClasses[expected] += 1
                else:
                    allClasses[expected] = 1

                # append to reportingDict
                if expected in reportingDict:
                    if actual in reportingDict[expected]:
                        reportingDict[expected][actual] += 1
                    else:
                        reportingDict[expected][actual] = 1
                else:
                    reportingDict[expected] = {actual: 1}

                l = inputF.readline()
                n += 1
    
    # output acc
    headerColumn = "\t"
    for expectedKey in reportingDict:
        headerColumn = headerColumn + "\t" + expectedKey

    confusionMatrix = ""
    for expectedKey in reportingDict:
        confusionMatrix = confusionMatrix + expectedKey + "\t"
        for actualKey in reportingDict[expectedKey]:
            confusionMatrix = confusionMatrix + str(reportingDict[expectedKey][actualKey]) + "\t"
        confusionMatrix = confusionMatrix + "\n"

    print "Confusion matrix for the test data:\n"
    print "row is the truth, column is the system output\n"
    print headerColumn
    print confusionMatrix
    print "Test Accuracy: %s" % (float(correctTotal) / totalVectors) 


if __name__ == '__main__':
    main()
