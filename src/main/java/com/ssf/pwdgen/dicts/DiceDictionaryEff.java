package com.ssf.pwdgen.dicts;

import com.ssf.pwdgen.DiceDictionary;

public class DiceDictionaryEff extends DiceDictionary {
    public DiceDictionaryEff(int numWords) throws Exception {
        super(numWords);
        setDictionary(readDictionaryFile("eff_large_wordlist.txt", 11111,66666));
    }

    @Override
    public String getDictionaryType() {
        return "Eff";
    }
    @Override
    public String getDices() {
        return getDices(5);
    }

}
