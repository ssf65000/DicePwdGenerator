package com.ssf.pwdgen;

import java.util.Arrays;
import java.util.List;

public class StrengthCalculator {
    public static List SPECIAL_CHARS =  Arrays.asList('`','~','!','@','#','$','%','^','&','*','(',')','-','_','=','+','{','[',']','}','\\','|',';',':','\'','"',',','<','>','.','/','?');


    private static int getPoolSize(String password){
        if(password == null || password.length()==0)
            return 0;
        boolean digitsPresent = false;
        boolean lowerCasePresent = false;
        boolean upperCasePresent = false;
        boolean specialSymbolsPresent = false;
        char pwdArray[] = password.toCharArray();
        for(char c : pwdArray){
            if((!digitsPresent) && Character.isDigit(c))
                digitsPresent = true;
            else if ((!lowerCasePresent) && Character.isLowerCase(c))
                lowerCasePresent = true;
            else if ((!upperCasePresent) && Character.isUpperCase(c))
                upperCasePresent = true;
            else if((!specialSymbolsPresent) && SPECIAL_CHARS.contains(c))
                specialSymbolsPresent = true;
        }
        int poolSize = 0;
        if (digitsPresent)
            poolSize = 10;
        if (lowerCasePresent)
            poolSize += 26;//english
        if(upperCasePresent)
            poolSize += 26;//english
        if(specialSymbolsPresent)
            poolSize += SPECIAL_CHARS.size();//standard MAC keyboard with numpad
        return poolSize;
    }

    public static PasswordStrength passwordStrengthCalculator(String pwd){
        PasswordStrength ret = new PasswordStrength();
        ret.setPoolSize(getPoolSize(pwd));
        if(ret.getPoolSize() != 0){
            ret.setBitsEntropy(pwd.length() * Math.log(ret.getPoolSize())/Math.log(2));
            ret.setBruteForceGuesses(Math.pow(ret.getPoolSize(), pwd.length()));
            ret.setLog10guesses(Math.log10(ret.getBruteForceGuesses()));
            if(ret.getBruteForceGuesses() <1e3)
                ret.setBruteForceScore(0);
            else if(ret.getBruteForceGuesses() <1e6)
                ret.setBruteForceScore(1);
            else if(ret.getBruteForceGuesses() <1e8)
                ret.setBruteForceScore(2);
            else if(ret.getBruteForceGuesses() < 1e10)
                ret.setBruteForceScore(3);
            else
                ret.setBruteForceScore(4);
        }
        return ret;
    }
}
