package com.ssf.pwdgen;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
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
        char wordSeparator=Character.MIN_VALUE;
        boolean includenumber = _dicePwdGenerator._includeNumber;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<_dicePwdGenerator._dictionary._numWords; i++) {
            String words[] =  _dicePwdGenerator._dictionary.nextWord(_dicePwdGenerator._dictionary, _dicesValue);
            for(String word : words) {
                if(wordSeparator==Character.MIN_VALUE || (!_dicePwdGenerator._sameSeparator))
                    wordSeparator = getWordSeparator();
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
        String ret = sb.toString();
        if(_dicePwdGenerator._hashPasswordLength != -1)
            ret = hashPassword(ret, _dicePwdGenerator._hashPasswordLength);
        return ret;
    }

    private String capitalize(String word) {
        word = word.substring(0, 1).toUpperCase() + word.substring(1);
        return word;
    }
    private char getWordSeparator(){
        return _dicePwdGenerator._wordSeparator.charAt(RANDOM.nextInt(_dicePwdGenerator._wordSeparator.length()));
    }
    private String hashPassword(String passphrase, int hashPasswordLength) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passphrase.getBytes(StandardCharsets.UTF_8));
            String base64Hash = Base64.getEncoder().encodeToString(hash);
            base64Hash = base64Hash.substring(0, Integer.min(hashPasswordLength, base64Hash.length()));
            if(base64Hash.endsWith("="))
                base64Hash = base64Hash.substring(0,base64Hash.length() - 1) + getWordSeparator();
            return base64Hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }
}
