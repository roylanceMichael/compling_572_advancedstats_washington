import sys
import re


def main():
	threshold = sys.argv[1]

	for line in sys.stdin:
		ilist = re.split('\s+', line)
		if float(ilist[1]) >= float(threshold):
			print line.strip()


if __name__ == '__main__':
    main()
