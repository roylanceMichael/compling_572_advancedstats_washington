package edu.washington.ling.roylance;

import edu.washington.ling.roylance.model.Instance;
import edu.washington.ling.roylance.services.Store;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.out.println("There must be 3 arguments!");
            return;
        }

        String trainFile = args[0];
        String modelFile = args[1];
        String minGain = args[2];

        Store store = Instance.factory(trainFile);
        // The initial annotator simply tags each document with the first class in the training data (e.g.,
        // if the training data is train2.txt, the first class would be “guns”).
    }
}
