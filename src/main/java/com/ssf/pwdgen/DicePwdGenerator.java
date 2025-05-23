package com.ssf.pwdgen;

public class DicePwdGenerator {
    int _hashPasswordLength = -1;
    int _numWords = 5;
    int _numPairs = 3;
    String _wordSeparator = "@#$%*-_+=";
    boolean _sameSeparator = false;
    boolean _capitalize = false;
    String _dictionaryType = "Common";
    boolean _quiet = false;
    int _numPasswords = 1;
    boolean _includeNumber = false;

    DiceDictionary _dictionary;

    public DicePwdGenerator(String[] args) {
        try {
            parseCommandLine(args);
            debug("Initializing dictionary : "+_dictionaryType);
            _dictionary = DiceDictionary.instance (this);
            debug("Dictionary size : "+_dictionary.getDictionarySize());
            generate();
        } catch (Exception e) {
            System.err.println("Error parsing command line : "+e.getMessage());
            usage();
        }
    }
    private void generate() {
        for(int i = 0; i < _numPasswords; i++) {
            debug("Generating password #"+i);
            DicePassword password = new DicePassword(this);
            String pwd = password.generate();
            System.out.println(pwd);
            debug("\t"+StrengthCalculator.passwordStrengthCalculator(pwd));
            debug("\tDices value");
            for(String str : password.getDicesValue()){
                debug("\t"+str);
            }
            debug("");
        }
    }

    private void parseCommandLine(String[] args) throws Exception{
        for(int i=0; i < args.length; i++) {
            switch(args[i]) {
                case "-numwords":
                    _numWords = Integer.parseInt(args[i+1]);
                    i++;
                    break;
                case "-numpairs":
                    _numPairs = Integer.parseInt(args[i+1]);
                    i++;
                    break;
                case "-separator":
                    _wordSeparator = args[i+1];
                    i++;
                    break;
                case "-sameseparator":
                    _sameSeparator = true;
                    break;
                case "-capitalize":
                    _capitalize = true;
                    break;
                case "-dictionary":
                    _dictionaryType = args[i+1];
                    i++;
                    break;
                case "-quiet":
                    _quiet = true;
                    break;
                case "-numpasswords":
                    _numPasswords = Integer.parseInt(args[i+1]);
                    i++;
                    break;
                case "-hashpassword":
                    _hashPasswordLength = Integer.parseInt(args[i+1]);
                    i++;
                    break;
                case "-includenumber":
                    _includeNumber = true;
                    break;
                case "-help":
                case "-h":
                case "-?":
                    usage();
                    break;
                default:
                    throw new Exception("Unknown argument : "+args[i]);
            }
        }
    }
    private void debug(String str){
        if(_quiet)
            return;
        System.out.println(str);
    }
    private void usage(){
        System.out.println("""
Usage: java -jar DicePwdGenerator.jar [-numpasswords N] [-numwords N] [-separator C] [-capitalize] [-includenumber] [-dictionary Eff | Diceware | Common] [-quiet]
\t-numpasswords N - number of passwords to generate. Default - 1
\t-numwords N - number of words in password. Default - 5
\t-numpairs N - number of noun/adjective pairs for Common dictionary. Default - 3
\t-separator C - set of characters to separate words in password. Default - random symbols from set of @ # $ % * - _ + =
\t-sameseparator - use same separator between words. Default - false 
\t-capitalize - Capitalize words in password. Default false
\t-includenumber - Include single digit in password. Default false
\t-dictionary Eff | Diceware | Common - dictionary to use to generate password. Default - Common
\t    Eff - dictionary created by Electronic Frontier Foundation. https://www.eff.org/dice
\t    Diceware - dictionary create by  A G Reinhold : https://theworld.com/~reinhold/diceware.html
\t    Common - most common nouns(7776 words) and adjectives(216 words).
\t-hashPassword <length> - produce hash of the generated passwords with maximum length <length> instead of password 
\t-quiet - Quiet password generation, e.g. output only password
\t-help - this help page
""");
        System.exit(1);
    }

    public static void main(String[] args) {
        new DicePwdGenerator(args);
    }
}
