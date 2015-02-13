import sys
import empiricalE


def main():
    # get from command parameters
    trainingData = sys.argv[1]
    outputFile = sys.argv[2]

    e = empiricalE.Empirical()
    # read the model from file:
    with open(trainingData) as inF:
        l = inF.readline()
        while len(l.strip()) > 0:
            e.read_into_dicts(l)
            l = inF.readline()

    totalF = len(e.allFeat)

    with open(outputFile, "w+") as outputF:
        outputF.write(e.outputEmpirical(totalF))


if __name__ == '__main__':
    main()
