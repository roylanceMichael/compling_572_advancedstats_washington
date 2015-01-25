def reportSysFile(sysOutput, bernNB, vect, testFile):
    # read in the training file for classification
    with open(testFile) as inputF:
        with open(sysOutput, "w") as outputF:
            l = inputF.readline()
            i = 0
            while len(l.strip()) > 0:
                w = vect.getWords(l)
                outputF.write(bernNB.reportClassificationResult(i, w) + "\n")
                l = inputF.readline()

                i += 1

def reportModelFile(modelFile, classes):
    with open(modelFile, "w") as outputF:
        outputF.write("%%%%% prior prob P(c) %%%%%" + "\n")
        
        for className in classes:
            outputF.write(str(classes[className].className) + "\t" + str(classes[className].prior) + "\t" + str(classes[className].logprior) + "\n")
            
        for className in classes:
            outputF.write("%%%%% conditional prob P(f|c) c=" + className + " %%%%%" + "\n")
            for feat in sorted(classes[className].probs):
                outputF.write(str(feat) + "\t" + className + "\t" + str(classes[className].probs[feat][0]) + "\t" + str(classes[className].probs[feat][1]) + "\n")