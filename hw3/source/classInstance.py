class ClassInstance:
	def __init__(self, className, prior, logprior, condProb_ooc, logCondProb_ooc, probs):
		self.className = className
		self.prior = prior
		self.logprior = logprior
		self.condProb_ooc = condProb_ooc
		self.logCondProb_ooc = logCondProb_ooc
		self.probs = probs