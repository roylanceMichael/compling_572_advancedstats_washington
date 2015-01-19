import sys
import re
import os


def main():

  fn1 = sys.argv[1]
  fn2 = sys.argv[2]
  f1 = open(fn1, 'ru')
  f2 = open(fn2, 'w')

  line = f1.readline()
  while line:
    f2.write(re.sub(r':[123456789]\d*', r':1', line))
    line = f1.readline()
  
  f1.close()
  f2.close()

  
if __name__ == '__main__':
  main()
