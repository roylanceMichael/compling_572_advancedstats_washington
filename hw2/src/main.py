import sys
import re
import getVectors
import dt
import s


def main():
    getV = getVectors.GetVectors()
    dtree = dt.DT()

    iFile = sys.argv[1]
    inputF = open(iFile)
    l = inputF.readline()
    while len(l.strip()) > 0:
        getV.read_into_dicts(l)
        l = inputF.readline()

    inputF.close()

    newS = s.S()
    newS.addVectors(getV.vectors)
    print newS.entropy()

if __name__ == '__main__':
        main()
