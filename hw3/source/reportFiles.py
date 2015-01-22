def reportModelFile(modelFile, classes):
    with open(modelFile, "w") as outputF:
        outputF.write("%%%%% prior prob P(c) %%%%%" + "\n")
        
        for className in classes:
            outputF.write(str(classes[className].className) + "\t" + str(classes[className].prior) + "\t" + str(classes[className].logprior) + "\n")

        outputF.write("%%%%% conditional prob P(f|c) %%%%%" + "\n" + "%%%%% conditional prob P(f|c) c=talk.politics.guns %%%%%" + "\n")
        
        for feat in sorted(classes['talk.politics.guns'].probs):
            outputF.write(str(feat) + "\t" + "talk.politics.guns" + "\t" + str(classes['talk.politics.guns'].probs[feat][0]) + "\t" + str(classes['talk.politics.guns'].probs[feat][1]) + "\n")

        outputF.write("%%%%% conditional prob P(f|c) c=talk.politics.mideast %%%%%" + "\n")
        
        for feat in sorted(classes['talk.politics.mideast'].probs):
            outputF.write(str(feat) + "\t" + "talk.politics.mideast" + "\t" + str(classes['talk.politics.mideast'].probs[feat][0]) + "\t" + str(classes['talk.politics.mideast'].probs[feat][1]) + "\n")

        outputF.write("%%%%% conditional prob P(f|c) c=talk.politics.misc %%%%%" + "\n")
        
        for feat in sorted(classes['talk.politics.misc'].probs):
            outputF.write(str(feat) + "\t" + "talk.politics.misc" + "\t" + str(classes['talk.politics.misc'].probs[feat][0]) + "\t" + str(classes['talk.politics.misc'].probs[feat][1]) + "\n")


#def reportSysFile(sysFile, i, instanceName, classification):

