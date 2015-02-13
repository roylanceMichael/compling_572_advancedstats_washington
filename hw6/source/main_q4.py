from __future__ import division
import sys
import modelE
import model
import decode


def main():
    # get from command parameters
    trainingData = sys.argv[1]
    outputFile = sys.argv[2]

    m = modelE.ModelE()
    # read the training data from file:
    with open(trainingData) as inF:
        l = inF.readline()
        while len(l.strip()) > 0:
            m.read_into_dicts(l)
            l = inF.readline()

    totalF = len(m.allFeat)

    if len(sys.argv) == 3:
        p = 1 / len(m.features)

        with open(outputFile, "w+") as outputF:
            outputF.write(m.outputModelE_(p, totalF))

    if len(sys.argv) == 4:
        modelFile = sys.argv[3]
        print "grabbed the model file!"

        m = model.Model()
        # read the model from file
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

#        with open(outputFile, "w+") as outputF:
#            outputF.write(m.outputModelE_model(p, totalF))

### think about how to read the training file only once.
### If decoding with model file - need to do that first


if __name__ == '__main__':
    main()
