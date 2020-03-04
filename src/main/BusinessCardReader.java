package main;
import java.io.*;
import opennlp.tools.namefind.*;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.xdrop.fuzzywuzzy.*;


//---For more in-depth explanation of thought process, please see readme---

public class BusinessCardReader {

    private String file;

    public BusinessCardReader(String filePara) throws IOException {
        //One file per object
        file=filePara;
    }

    //Reads information from text file and processes it.
    public ContactInfo readFromCard() throws FileNotFoundException {
        //Private variables for name, email, and phone number. Setting these to "" to return emptystring if no
        //viable data is parsed.
        String name = "";
        String email = "";
        String phoneNumber = "";

        //Variable to store degree of closeness for name.
        double nameStat = 0;

        //Regex patterns to handle number and email cases
        Pattern numPattern = Pattern.compile("\\d?-? *?\\(?\\d{3}\\)?-? ? *?\\(?\\d{3}\\)?-? *?\\(?\\d{4}\\)?");
        Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");

        try {
            //Read from file
            String strCurrentLine;
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] wordsForPhone = new String[] {"tele","mobile","phone","telephone","cell","dial","call","mob"};

            //Sets up model for name recognition tool
            InputStream inputStream = getClass().getResourceAsStream("en-ner-person.bin");;
            TokenNameFinderModel model = new TokenNameFinderModel(inputStream);;
            NameFinderME nameFinder = new NameFinderME(model);

            //Reads from file
            while ((strCurrentLine = br.readLine()) != null) {
                //Sets up matchers for each line from regex
                Matcher emailMatcher = emailPattern.matcher(strCurrentLine);
                Matcher numMatcher = numPattern.matcher(strCurrentLine);

                //Match with email: This case is fairly straightforward. If the line includes a match with an email-
                //there's probably a valid email in the line.
                if (emailMatcher.find()){
                    email = emailMatcher.group();

                //Match with number: Checks if line includes number in U.S. format.
                }else if (numMatcher.find()){
                    String lowerCase=strCurrentLine.toLowerCase();
                    //Case if number is not fax number

                    if (!lowerCase.contains("fax")){
                        phoneNumber = strCurrentLine;
                    }else {
                        //Case if number is fax number: breaks line into tokens and checks if any of them bears
                        //similarity to several words that may be used in place of "phone".
                        //Regex splits based on all special characters.
                        String[] tokens = strCurrentLine.split("[^A-Za-z0-9]");
                        for (int i=0;i<tokens.length;i++) {
                            for (String phoneWord : wordsForPhone) {
                                if (FuzzySearch.ratio(phoneWord,tokens[i].toLowerCase())>=50) {
                                    phoneNumber = strCurrentLine;
                                    break;
                                }
                            }
                        }
                    }
                    phoneNumber=phoneNumber.replaceAll("[^0-9]","");
                }

                //Match with name: in event string only has letters, name can be matched using Natural Language Processor
                // library.
                else if  (strCurrentLine.matches("^[ A-Za-z]+$")){
                    String[] tokens = strCurrentLine.split("\\s+");
                    boolean allCaps=true;

                    //First,check if every word starts with capital
                    for (String token : tokens){
                        if (token.length()>0&&!Character.isUpperCase(token.charAt(0))){
                            allCaps=false;
                        }
                    }

                    //Then, we check how much of the list of string tokens is recognized by NLP as name, by
                    //checking the spans against the total list of string tokens.
                    if (allCaps) {
                        Span nameSpans[] = nameFinder.find(tokens);
                        int curr=0;
                        for (Span s: nameSpans){
                            curr+=s.getEnd()-s.getStart();
                        }
                        if (((double)curr/tokens.length) > nameStat){
                            name=strCurrentLine;
                            nameStat=(double) curr/tokens.length;
                        }
                    }
                }
            }
        }catch (IOException e){
            throw new FileNotFoundException("File not found");
        }
        return new ContactInfo(name,email,phoneNumber);
    }

    //Tokenizer required for use of NLP. Sourced from internet- not my own work.
    public String[] tokenize(String sentence) throws IOException{
        InputStream inputStreamTokenizer = getClass().getResourceAsStream("en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);
        TokenizerME tokenizer = new TokenizerME(tokenModel);
        return tokenizer.tokenize(sentence);
    }
}

