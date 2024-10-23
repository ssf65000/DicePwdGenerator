package com.ssf.pwdgen.dicts;

import com.ssf.pwdgen.DiceDictionary;

public class DiceDictionaryDiceware extends DiceDictionary {
    public DiceDictionaryDiceware(int numWords) throws Exception {
        super(numWords);
        setDictionary(readDictionaryFile("diceware.wordlist.txt", 11111, 66666));
    }

    @Override
    public String getDictionaryType() {
        return "Diceware";
    }

    @Override
    public String getDices() {
        return getDices(5);
    }

}
