from __future__ import division
import sys
import modelE
import model
import decode


def main():
    # get from command parameters
    trainingData = sys.argv[1]
    outputFile = sys.argv[2]

    if len(sys.argv) == 3:
        modelExp = modelE.ModelE()
        # read the training data from file:
        totalF = 0
        with open(trainingData) as inF:
            l = inF.readline()
            while len(l.strip()) > 0:
                totalF += 1
                modelExp.read_into_dicts(l)
                l = inF.readline()

        p = 1 / len(modelExp.classNames)

        with open(outputFile, "w+") as outputF:
            outputF.write(modelExp.outputModelE_(p, totalF))

    if len(sys.argv) == 4:
        modelFile = sys.argv[3]

        m = model.Model()
        # read the model from file
        with open(modelFile) as inmF:
            mF_ = inmF.readline()
            mF = mF_.strip()
            className = ""
            while len(mF) > 0:
                if len(mF.split()) > 2:
		    className = mF.split()[-1]
		    mF_ = inmF.readline()
		    mF = mF_.strip()
                m.read_into_dicts(mF, className)
                mF_ = inmF.readline()
                mF = mF_.strip()

        modelExp = modelE.ModelE()
        totalF = 0
        with open(trainingData) as inputF:
            l = inputF.readline()
            while len(l.strip()) > 0:
                totalF += 1
                modelExp.read_into_dicts(l)
                d = decode.Decode(m)
                d.read_into_dicts(l, totalF)
                d.getCondProb()
                d.classCondProb()
                l = inputF.readline()

        with open(outputFile, "w+") as outputF:
            outputF.write(modelExp.outputModelE_model(d, totalF))


if __name__ == '__main__':
    main()
