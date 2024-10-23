package com.ssf.pwdgen;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DicePassword {
    static final SecureRandom RANDOM;
    static {
        try {
            RANDOM = SecureRandom.getInstance("NativePRNGBlocking");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    DicePwdGenerator _dicePwdGenerator;
    List<String> _dicesValue = new ArrayList<>();


    public DicePassword(DicePwdGenerator dicePwdGenerator) {
        _dicePwdGenerator = dicePwdGenerator;
    }

    public List<String> getDicesValue() {
        return _dicesValue;
    }

    public String generate() {
        boolean includenumber = _dicePwdGenerator._includeNumber;
        StringBuffer sb = new StringBuffer();
        String wordSeparator = _dicePwdGenerator._wordSeparator;
        if(_dicePwdGenerator._wordSeparator.equalsIgnoreCase(DicePwdGenerator.RND_WORD_SEPARATOR))
            wordSeparator = String.valueOf(StrengthCalculator.SPECIAL_CHARS.get(DicePassword.RANDOM.nextInt(StrengthCalculator.SPECIAL_CHARS.size())));

        for(int i = 0; i<_dicePwdGenerator._dictionary._numWords; i++) {



            String words[] =  _dicePwdGenerator._dictionary.nextWord(_dicePwdGenerator._dictionary, _dicesValue);
            for(String word : words) {
                if(_dicePwdGenerator._capitalize)
                    word = capitalize(word);
                if(!sb.isEmpty()) {
                    sb.append(wordSeparator);
                }
                sb.append(word);
                if(includenumber && RANDOM.nextBoolean()){
                    includenumber = false;
                    sb.append(RANDOM.nextInt(0,10));
                }
            }

        }
        if(includenumber)
            sb.append(RANDOM.nextInt(0,10));
        return sb.toString();
    }

    private String capitalize(String word) {
        word = word.substring(0, 1).toUpperCase() + word.substring(1);
        return word;
    }
}
