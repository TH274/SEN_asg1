package engine;

import java.util.ArrayList;
import java.util.List;

public class Result implements Comparable<Result> {
    private Doc doc;
    private List<Match> matches;
    public Result(Doc d, List<Match> matches){
        this.doc = d;
        this.matches = matches;
    }

    public Doc getDoc() {
        return doc;
    }

    public List<Match> getMatches(){
        return matches;
    }

    public int getMatchCount() {
        return matches.size();
    }
    public int getTotalFrequency(){
        int totalfreg = 0;
        for(Match match : matches){
            totalfreg += match.getFreq();
        }
        return totalfreg;
    }
    public double getAverageFirstIndex(){
        int totalIndexes = 0;
        for(Match match : matches) {
            totalIndexes += match.getFirstIndex();
        }
        if (matches.size() > 0) {
            return (double) totalIndexes / matches.size();
        }
        return 0.0;
    }

    public String htmlHighlight() {
        StringBuilder resultHtml = new StringBuilder();
        List<Word> titleWords = doc.getTitle();
        List<Word> bodyWords = doc.getBody();

        boolean inTitle = true;

        resultHtml.append("<h3>"); // Add the opening <h3> tag at the beginning

        for (Word word : titleWords) {
            String htmlTag = getHtmlTag(word, inTitle);

            if (!htmlTag.isEmpty()) {
                if(!word.getPrefix().isEmpty())  resultHtml.append(word.getPrefix().trim());
                resultHtml.append(htmlTag);
                resultHtml.append(word.getText());
                resultHtml.append("</" + htmlTag.substring(1) + "");
                if(!word.getSuffix().isEmpty())  resultHtml.append(word.getSuffix().trim());
            } else {
                resultHtml.append(word);
            }
            if(titleWords.indexOf(word) != titleWords.size() -1)
            resultHtml.append(" ");
        }
        resultHtml.append("</h3>"); // Close the <h3> tag for the title

        inTitle = false;
        resultHtml.append("<p>"); // Open <p> tag for the body section

        for (Word word : bodyWords) {
            String htmlTag = getHtmlTag(word, inTitle);
            if (!htmlTag.isEmpty()) {
                if(!word.getPrefix().isEmpty())  resultHtml.append(word.getPrefix().trim());
                resultHtml.append(htmlTag);
                resultHtml.append(word.getText());
                resultHtml.append("</" + htmlTag.substring(1) + "");
                if(!word.getSuffix().isEmpty()) {
                    resultHtml.append(word.getSuffix().trim());
                }
            } else {
                resultHtml.append(word);
            }
            if(bodyWords.indexOf(word) != bodyWords.size() -1)
            resultHtml.append(" ");
        }
        // Close the <p> tag for the body section
        resultHtml.append("</p>");
        String result =  resultHtml.toString();
        result = result.replaceAll(" +</p>", "</p>");
        return result;
    }

    private String getHtmlTag(Word word, boolean inTitle) {
        for (Match match : matches) {
            if (match.getWord().equals(word)) {
                if (inTitle) {
                    return "<u>";
                } else {
                    return "<b>";
                }
            }
        }

        // If the word doesn't match any term in matches, return the word itself
        return "";
    }


    @Override
    public int compareTo(Result otherResult){
        if(this.getMatchCount() != otherResult.getMatchCount()){
            return Integer.compare(otherResult.getMatchCount(),this.getMatchCount());
        } else if (this.getTotalFrequency() != otherResult.getTotalFrequency()) {
            return Integer.compare(otherResult.getTotalFrequency(),this.getTotalFrequency());
        } else {
            return Double.compare(this.getAverageFirstIndex(),otherResult.getAverageFirstIndex());
        }
    }
}

