package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Word {
    private final String rawText;


    public static Set<String> stopWords = new HashSet<>();

    private Word(String rawText){
        this.rawText = rawText;
    }
    public String getPrefix() {
        String regexPrefix = "^[\\W]*";
        Matcher matcher = Pattern.compile(regexPrefix).matcher(this.rawText);
        return !isValidWord() ? "" :  (matcher.find()) ? matcher.group(0):"";
    }

    public String getText() {
        String regexTextPart = "[a-zA-Z-]+";
        if (isValidWord()) {
            Matcher matcher = Pattern.compile(regexTextPart).matcher(this.rawText);
            if (matcher.find()) {
                return matcher.group(0);
            }
        }
        return this.rawText;
    }

    public String getSuffix() {
        String regexSuffix = "('s)?[\\W]*$";
        Matcher matcher = Pattern.compile(regexSuffix).matcher(this.rawText);
        String s = (matcher.find()) ? matcher.group(0) : "";
        return isValidWord() ? s : "";
    }
    public  boolean isValidWord(){
        String regexValid = "^\\W*[a-zA-z-]+('s)?[\\W]*$";
        String regexFullSymbol = "^[\\W]+$";
        if (!Pattern.matches(regexFullSymbol, this.rawText) && Pattern.matches(regexValid, this.rawText)) return true;
        else return false;
    }
    public boolean isKeyword() {
        return isValidWord() && !getText().isEmpty() && !stopWords.contains(getText().toLowerCase());
    }



    @Override
    public boolean equals(Object obj) {
        Word word = (Word) obj;
        return getText().equalsIgnoreCase(word.getText());
    }

    @Override
    public String toString(){
        return this.rawText;
    }
    public static Word createWord(String rawText) {
           return new Word(rawText.trim());
    }



    public static boolean loadStopWords(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line.toLowerCase());
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

