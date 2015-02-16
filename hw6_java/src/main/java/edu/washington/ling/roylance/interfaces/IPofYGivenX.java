package edu.washington.ling.roylance.interfaces;

import edu.washington.ling.roylance.instances.VectorInstance;

import java.util.Set;

/**
 * Created by mroylance on 2/15/15.
 */
public interface IPofYGivenX {
    double calculate(String className, VectorInstance vectorInstance);

    Set<String> getClassNames();
}
