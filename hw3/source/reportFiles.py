import re

def getWords(line):
    ilist = re.split('\s+', line.strip())

    words = [ilist[0]]
    for i in ilist[1:]:
        pair = i.split(':')
        words.append(pair[0])
    return words

def reportSysFileInternal(reportType, fileToRead, outputF, model):
	outputF.write("%%%%% " + reportType + " data:\n")
	with open(fileToRead) as inputF:
		l = inputF.readline()
		i = 0
		while len(l.strip()) > 0:
			w = getWords(l)
			outputF.write(model.reportClassificationResult(i, w) + "\n")
			l = inputF.readline()
			i += 1

def reportSysFile(sysOutput, model, trainFile, testFile):
    # read in the training file for classification
	with open(sysOutput, "w") as outputF:
		reportSysFileInternal("training", trainFile, outputF, model)
		reportSysFileInternal("test", testFile, outputF, model)

def reportModelFile(modelFile, classes):
    with open(modelFile, "w") as outputF:
        outputF.write("%%%%% prior prob P(c) %%%%%" + "\n")
        
        for className in classes:
            outputF.write(str(classes[className].className) + "\t" + str(classes[className].prior) + "\t" + str(classes[className].logprior) + "\n")

        for className in classes:
            outputF.write("%%%%% conditional prob P(f|c) c=" + className + " %%%%%" + "\n")
            for feat in sorted(classes[className].probs):
                outputF.write(str(feat) + "\t" + className + "\t" + str(classes[className].probs[feat][0]) + "\t" + str(classes[className].probs[feat][1]) + "\n")