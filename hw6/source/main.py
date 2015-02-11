import sys
import operator
import model
import decode


def main():
    # get from command parameters
    testData = sys.argv[1]
    modelFile = sys.argv[2]
#    sysOutput = sys.argv[3]

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

#    for key in m.features:
#	    print key, len(m.features[key])

    # read in the training file
    with open(testData) as inputF:
        l = inputF.readline()
        while len(l.strip()) > 0:
            d = decode.Decode()
            d.read_into_dicts(l)
            l = inputF.readline()
	    print d.doc


if __name__ == '__main__':
    main()
