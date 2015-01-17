def reportModelFile(modelFile, rootS):
	with open(modelFile, "w") as outputF:
		for tree in rootS.reportTree():
			outputF.write(tree + "\n")