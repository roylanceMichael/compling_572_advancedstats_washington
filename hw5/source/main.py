import sys
import repo

def main():
	newRepo = repo.Repo()

	# process this in some repository
	for line in sys.stdin:
		newRepo.read_into_dicts(line)

	for key in newRepo.featureVector:
		print "%s - %s" % (key, newRepo.featureVector[key])

if __name__ == '__main__':
    main()