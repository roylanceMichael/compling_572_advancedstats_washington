package edu.washington.ling.roylance.builders;

public interface IFactory<I,O> {
    O create(I input);
}
