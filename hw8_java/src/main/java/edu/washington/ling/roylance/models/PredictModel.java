package edu.washington.ling.roylance.models;

public class PredictModel {
    private int classification;

    private double fx;

    public int getClassification() {
        return this.classification;
    }

    public double getFx() {
        return this.fx;
    }

    public PredictModel setClassification(int value) {
        this.classification = value;
        return this;
    }

    public PredictModel setFx(double value) {
        this.fx = value;
        return this;
    }
}
