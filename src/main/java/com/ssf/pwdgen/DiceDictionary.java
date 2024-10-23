package com.ssf.pwdgen;

import com.ssf.pwdgen.dicts.DiceDictionaryDiceware;
import com.ssf.pwdgen.dicts.DiceDictionaryEff;
import com.ssf.pwdgen.dicts.DiceDictionaryCommon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DiceDictionary {
   private  Map<String, String> _dictionary;
    int _numWords;

    public static DiceDictionary instance(DicePwdGenerator pwdgen) throws Exception {
        if("Eff".equalsIgnoreCase(pwdgen._dictionaryType))
            return new DiceDictionaryEff(pwdgen._numWords);
        else if("Diceware".equalsIgnoreCase(pwdgen._dictionaryType))
            return new DiceDictionaryDiceware(pwdgen._numWords);
        else if("Common".equalsIgnoreCase(pwdgen._dictionaryType))
            return new DiceDictionaryCommon(pwdgen._numPairs);
        else
            throw new IllegalArgumentException("Invalid dictionary : "+pwdgen._dictionaryType);

    }

    public DiceDictionary(int numWords) throws Exception {
        _numWords = numWords;
    }

    public abstract String getDictionaryType();

    public Map<String, String> getDictionary() {
        return _dictionary;
    }
    public int getDictionarySize() {
        if(_dictionary == null)
            return 0;
        return _dictionary.size();
    }

    public void setDictionary(Map<String, String> dictionary) {
        _dictionary = dictionary;
    }

    protected Map<String, String> readDictionaryFile(String filename, int minDice, int maxDice) throws Exception {
        Map<String, String> ret = new HashMap<>();
        try (InputStream inputStream = DicePwdGenerator.class.getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String str;
            int lineNum = 0;
            while( (str = reader.readLine()) != null) {
                lineNum++;
                str = str.trim();
                if(str.length() == 0)
                    continue;
                int idx = str.indexOf('\t');
                if(idx == -1)
                    idx = str.indexOf(' ');
                String dice = "";
                String word = "";
                if(idx != -1){
                    dice = str.substring(0, idx).trim();
                    word = str.substring(idx).trim();
                }
                if(dice.length() == 0 || word.length() == 0)
                    throw new Exception("Invalid dictionary : "+getDictionaryType()+" at line "+lineNum+" '"+str+"'");
                try {
                    int diceInt = Integer.parseInt(dice);
                    if (diceInt < minDice || diceInt > maxDice)
                        throw new Exception("Minimum value "+minDice+", maximum value "+maxDice);
                }catch(Exception e){
                    throw new Exception("Invalid dice value : '"+dice+"' at line "+lineNum+" : "+e.getMessage());
                }
                ret.put(dice, word);
            }
        } catch (IOException e) {
            throw new Exception("Error reading dictionary file : "+e.getMessage(), e);
        }
        return ret;
    }

    public abstract String getDices();

    public String getDices(int numDices){
        String dicesValue = "";
        for(int i = 0; i<numDices; i++) {
            int nextDice = DicePassword.RANDOM.nextInt(1,7);
            dicesValue += nextDice;
        }
        return dicesValue;
    }

    public String getWord(String dicesValue) {
        return getWord(_dictionary, dicesValue);
    }
    public String getWord(Map<String, String> dictionary, String dicesValue) {
        String word = dictionary.get(dicesValue);
        if(word == null)
            throw new RuntimeException("Invalid dice value : "+dicesValue);
        return word;
    }

    public String[] nextWord(DiceDictionary dictionary, List<String> dicesValue) {
        String diceValue = getDices();
        dicesValue.add(diceValue);
        return new String[]{getWord(diceValue)};
    }
}
