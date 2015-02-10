import sys
import re


def main():
	sentP0Value = float(sys.argv[1])

	chiDictionary = { 0:0, 0.10:4.605, 0.05:5.991, 0.025:7.378, 0.01:9.210, 0.001:13.816}

	if sentP0Value not in chiDictionary:
		print "please send a value that is in the chi dictionary!"
		return
	
	for line in sys.stdin:
		ilist = re.split('\s+', line)
		if float(ilist[1]) >= chiDictionary[sentP0Value]:
			print line.strip()


if __name__ == '__main__':
    main()
