def reportModelFile(modelFile, rootS):
	with open(modelFile, "w") as outputF:
		for tree in rootS.reportTree():
			outputF.write(tree + "\n")

def reportSysFile(sysFile, rootS, vectorInstances):
	with open(sysFile, "w") as outputF:
		for vectorInstance in vectorInstances:
			outputF.write(rootS.reportClassificationResult(vectorInstance))

def printConfusionMatrix(rootS, vectorInstances, dataType):
	print "Confusion matrix for the %s data:\n" % (dataType)
	print "row is the truth, column is the system output\n"

	reportingDict = {}
	correctTotal = 0

	for vectorInstance in vectorInstances:
		expected = vectorInstance.className
		actual = rootS.reportClassPrediction(vectorInstance)

		if expected == actual:
			correctTotal += 1

		if expected in reportingDict:
			reportingDict[expected][actual] += 1
		else:
			reportingDict[expected] = { actual: 1 }
	headerColumn = "\t"
	for expectedKey in reportingDict:
		headerColumn = headerColumn + "\t" + expectedKey

	print headerColumn

	confusionMatrix = ""
	for expectedKey in reportingDict:
		confusionMatrix = confusionMatrix + "\t" + expectedKey + "\t"
		for actualKey in reportingDict[expectedKey]:
			print reportingDict[expectedKey][actualKey] + "\t"
		print "\n"

	print "%s accuracy =%s\n" % (dataType, correctTotal / len(vectorInstances))
