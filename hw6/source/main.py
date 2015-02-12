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

    reportingDict = {}
    for className in m.features:
        reportingDict[className] = {}
        for otherClassName in m.features:
            reportingDict[className][otherClassName] = 0

    correctTotal = 0
    totalVectors = 0
    currentIdx = 0

    # read in the training file
    with open(sysOutput, "w+") as outputF:
        with open(testData) as inputF:
            l = inputF.readline()
            n = 0
            while len(l.strip()) > 0:
                d = decode.Decode(m)
                d.read_into_dicts(l, n)
            #            print d.doc
                d.getCondProb()
                d.classCondProb()
            #            print d.P

                # write to sys output
                outputF.write(d.reportSysRecord() + "\n")
                
                l = inputF.readline()
                n += 1


if __name__ == '__main__':
    main()
