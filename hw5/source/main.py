import sys
import operator
import repo


def main():
	newRepo = repo.Repo()

	# process this in some repository
	for line in sys.stdin:
		newRepo.read_into_dicts(line)
	newRepo.getClassExpected()
	newRepo.chiSq()

	for key in sorted(newRepo.chi2, key=newRepo.chi2.get, reverse=True):
		print key, '\t', newRepo.chi2[key], '\t', newRepo.featureVector[key]

if __name__ == '__main__':
    main()
