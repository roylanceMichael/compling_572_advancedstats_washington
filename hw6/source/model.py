import re

class Model:
    def __init__(self):
        self.features = {}   # {className1: {{f1:1}, {f2:1}, {f3:1}}, className2: {}} - a list of feature vectors represented as dictionaries


    def read_into_dicts(self, line_of_input, className):
        ilist = re.split('\s+', line_of_input.strip())

        if className in self.features:
            self.features[className][ilist[0]] = float(ilist[1])
        else:
            self.features[className] = {ilist[0] : float(ilist[1])}
