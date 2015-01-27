import re
import word

# todo: refactor to read files exactly once
def getWordListsFromFile(fileName):
	with open(fileName) as inputF:
		line = inputF.readline()
		while len(line.strip()) > 0:
			yield getWords(line)
			line = inputF.readline()

def reportSysFileInternal(reportType, words, outputF, model):
	outputF.write("%%%%% " + reportType + " data:\n")
	i = 0
	for w in words:
		outputF.write(model.reportClassificationResultForSysFile(str(i) + reportType, i, w) + "\n")
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

	currentIdx = 0
	for wordList in wordLists:
		(expected, actual) = bernoulliNB.getClassificationResultForConfusionMatrix(str(currentIdx) + dataType)
		currentIdx += 1
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

	confusionMatrix = ""
	for expectedKey in reportingDict:
		confusionMatrix = confusionMatrix + expectedKey + "\t"
		for actualKey in reportingDict[expectedKey]:
			confusionMatrix = confusionMatrix + str(reportingDict[expectedKey][actualKey]) + "\t"
		confusionMatrix = confusionMatrix + "\n"

	print confusionMatrix
	print "%s accuracy =%s\n" % (dataType, float(correctTotal) / totalVectors)

def printConfusionMatrix(bernoulliNB, vectorRepo):
	printConfusionMatrixInternal(bernoulliNB, vectorRepo.trainVectors, "training", vectorRepo)
	printConfusionMatrixInternal(bernoulliNB, vectorRepo.testVectors, "test", vectorRepo)

def reportSysFile(sysOutput, model, vectorRepo):
    # read in the training file for classification
	with open(sysOutput, "w") as outputF:
		reportSysFileInternal("training", vectorRepo.trainVectors, outputF, model)
		reportSysFileInternal("test", vectorRepo.testVectors, outputF, model)

def reportModelFile(modelFile, classes):
    with open(modelFile, "w") as outputF:
        outputF.write("%%%%% prior prob P(c) %%%%%" + "\n")
        
        for className in classes:
            outputF.write(str(classes[className].className) + "\t" + str(classes[className].prior) + "\t" + str(classes[className].logprior) + "\n")

        for className in classes:
            outputF.write("%%%%% conditional prob P(f|c) c=" + className + " %%%%%" + "\n")
            for feat in sorted(classes[className].probs):
                outputF.write(str(feat) + "\t" + className + "\t" + str(classes[className].probs[feat][0]) + "\t" + str(classes[className].probs[feat][1]) + "\n")