def reportModelFile(modelFile, rootS):
	with open(modelFile, "w") as outputF:
		for tree in rootS.reportTree():
			outputF.write(tree + "\n")

def reportSysFile(sysFile, rootS, vectorInstances):
	with open(sysFile, "w") as outputF:
		for vectorInstance in vectorInstances:
			outputF.write(rootS.reportClassificationResult(vectorInstance) + "\n")

def printConfusionMatrix(rootS, vectorInstances, dataType, vectorRepo):
	print "Confusion matrix for the %s data:\n" % (dataType)
	print "row is the truth, column is the system output\n"

	reportingDict = {}
	for className in vectorRepo.allClassNames:
		reportingDict[className] = {}
		for otherClassName in vectorRepo.allClassNames:
			reportingDict[className][otherClassName] = 0

	correctTotal = 0
	totalVectors = 0

	for vectorInstance in vectorInstances:
		expected = vectorInstance.className
		actual = rootS.reportClassPrediction(vectorInstance)
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
