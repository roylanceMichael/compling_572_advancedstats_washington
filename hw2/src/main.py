import sys
import re
import getVectors
import dt
import s

def main():
    # get from command parameters
    getV = getVectors.GetVectors(3, 0.01)
    dtree = dt.DT()

    iFile = sys.argv[1]
    inputF = open(iFile)
    l = inputF.readline()
    while len(l.strip()) > 0:
        getV.read_into_dicts(l)
        l = inputF.readline()

    inputF.close()

    newS = s.S(getV, 0)
    newS.addVectors(getV.vectors)
    newS.informationGain()

    for tree in newS.reportTree():
        print tree

if __name__ == '__main__':
        main()
