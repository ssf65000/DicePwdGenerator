package com.ssf.pwdgen.dicts;

import com.ssf.pwdgen.DiceDictionary;

import java.util.List;
import java.util.Map;

public class DiceDictionaryCommon extends DiceDictionary {
    Map<String, String> _adj;
    public DiceDictionaryCommon(int numWords) throws Exception {
        super(numWords);
        setDictionary(readDictionaryFile("NounList.txt", 11111, 66666));
        _adj = readDictionaryFile("AdjectivesList.txt", 111, 666);
    }

    @Override
    public String getDictionaryType() {
        return "Common";
    }
    @Override
    public String getDices() {
        return getDices(5);
    }

    @Override
    public String[] nextWord(DiceDictionary dictionary, List<String> dicesValue) {
        String diceValueN = getDices();
        String diceValueA = getDices(3);
        dicesValue.add(diceValueA);
        dicesValue.add(diceValueN);
        return new String[]{getWord(_adj, diceValueA), getWord(diceValueN)};
    }

    @Override
    public int getDictionarySize(){
        return super.getDictionarySize() + _adj.size();
    }

}
