package com.ssf.pwdgen;

public class PasswordStrength {
    int _poolSize;
    double _bitsEntropy;
    double _bruteForceGuesses;
    double _log10guesses;
    double _bruteForceScore;

    public PasswordStrength(){
        _poolSize = 0;
        _bitsEntropy = 0;
        _bruteForceGuesses = 0;
        _log10guesses = 0;
        _bruteForceScore = 0;
    }

    public double getBruteForceGuesses() {
        return _bruteForceGuesses;
    }

    public void setBruteForceGuesses(double bruteForceGuesses) {
        _bruteForceGuesses = bruteForceGuesses;
    }

    public double getLog10guesses() {
        return _log10guesses;
    }

    public void setLog10guesses(double log10guesses) {
        _log10guesses = log10guesses;
    }

    public int getPoolSize() {
        return _poolSize;
    }

    public void setPoolSize(int poolSize) {
        _poolSize = poolSize;
    }

    public double getBruteForceScore() {
        return _bruteForceScore;
    }

    public void setBruteForceScore(double bruteForceScore) {
        _bruteForceScore = bruteForceScore;
    }

    public double getBitsEntropy() {
        return _bitsEntropy;
    }

    public void setBitsEntropy(double bitsEntropy) {
        _bitsEntropy = bitsEntropy;
    }

    @Override
    public String toString() {
        return "PasswordStrength{" +
                "bitsEntropy=" + String.format("%.2f",_bitsEntropy) +
                ", poolSize=" + _poolSize +
                ", Brute Force Guesses=" + _bruteForceGuesses +
                ", log10guesses=" + String.format("%.2f", _log10guesses) +
                ", Brute Force Score=" + _bruteForceScore +
                '}';
    }
}
