import sys
import re
import getVectors
import dt


def main():
    getV = getVectors.GetVectors()
    dtree = dt.DT()

    iFile = sys.argv[1]
    inputF = open(iFile)
    l = inputF.readline()
    while len(l.strip()) > 0:
	getV.read_into_dicts(l)
        l = inputF.readline()

    p = dtree.findClassDistribution(getV.vectors)
    print dtree.entropy(p)
    inputF.close()

if __name__ == '__main__':
        main()
