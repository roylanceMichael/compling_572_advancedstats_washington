from __future__ import division
import os
import sys
import re
import math
import getVectors
from decimal import Decimal


class IG:
	def __init__(self, parent, splitWithFeature, splitWithoutFeature):
		self.parent = parent
		self.splitWithFeature = splitWithFeature
		self.splitWithoutFeature = splitWithoutFeature

	def calculateInformationGain(self):
		parentEntropy = self.parent.entropy()
		withFeatureSize = self.splitWithFeature.totalSize
		withFeatureEntropy = self.splitWithFeature.entropy()
		withoutFeatureSize = self.splitWithoutFeature.totalSize
		withoutFeatureEntropy = self.splitWithoutFeature.entropy()
		totalSize = self.parent.totalSize

		return parentEntropy - (withFeatureSize / totalSize * withFeatureEntropy + withoutFeatureSize / totalSize * withoutFeatureEntropy)

	def calculateInformationGainForChildren(self):
		self.splitWithFeature.informationGain()
		self.splitWithoutFeature.informationGain()