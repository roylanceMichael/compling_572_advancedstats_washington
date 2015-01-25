import re

def getWords(line):
    ilist = re.split('\s+', line.strip())

    words = [ilist[0]]
    for i in ilist[1:]:
        pair = i.split(':')
        words.append(pair[0])
    return words

# todo: refactor to read files exactly once
def getWordListsFromFile(fileName):
	with open(fileName) as inputF:
		line = inputF.readline()
		while len(line.strip()) > 0:
			yield getWords(line)
			line = inputF.readline()

def reportSysFileInternal(reportType, fileToRead, outputF, model):
	outputF.write("%%%%% " + reportType + " data:\n")
	with open(fileToRead) as inputF:
		l = inputF.readline()
		i = 0
		while len(l.strip()) > 0:
			w = getWords(l)
			outputF.write(model.reportClassificationResultForSysFile(i, w) + "\n")
			l = inputF.readline()
			i += 1

def printConfusionMatrixInternal(bernoulliNB, wordLists, dataType, vectorRepo):
	print "Confusion matrix for the %s data:\n" % (dataType)
	print "row is the truth, column is the system output\n"

	reportingDict = {}
	for className in vectorRepo.classProbability:
		reportingDict[className] = {}
		for otherClassName in vectorRepo.classProbability:
			reportingDict[className][otherClassName] = 0

	correctTotal = 0
	totalVectors = 0

	for wordList in wordLists:
		(expected, actual) = bernoulliNB.getClassificationResultForConfusionMatrix(wordList)
		totalVectors += 1

		if expected == actual:
			correctTotal += 1

		if expected in reportingDict:
			if actual in reportingDict[expected]:
				reportingDict[expected][actual] += 1
			else:
				reportingDict[expected][actual] = 1
		else:
			reportingDict[expected] = { actual: 1 }

	headerColumn = "\t"
	for expectedKey in reportingDict:
		headerColumn = headerColumn + "\t" + expectedKey

	print headerColumn

	confusionMatrix = "\t"
	for expectedKey in reportingDict:
		confusionMatrix = confusionMatrix + expectedKey + "\t"
		for actualKey in reportingDict[expectedKey]:
			confusionMatrix = confusionMatrix + str(reportingDict[expectedKey][actualKey]) + "\t"
		confusionMatrix = confusionMatrix + "\n"

	print confusionMatrix
	print "%s accuracy =%s\n" % (dataType, float(correctTotal) / totalVectors)

def printConfusionMatrix(bernoulliNB, trainFile, testFile, vectorRepo):
	printConfusionMatrixInternal(bernoulliNB, getWordListsFromFile(trainFile), "training", vectorRepo)
	printConfusionMatrixInternal(bernoulliNB, getWordListsFromFile(testFile), "test", vectorRepo)

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