package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Query {
    private List<Word> keywords;

    public Query(String searchPhrase){
        String[] words = searchPhrase.split(" ");
        keywords = new ArrayList<>();
        for (String word : words){
            Word wordObj = Word.createWord(word);
            if (wordObj.isKeyword()) {
                keywords.add(wordObj);
            }
        }
    }
    public List<Word> getKeywords(){
        return keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();
        List<Word> titleWords = d.getTitle();
        List<Word> bodyWords = d.getBody();

        for (Word keyword : keywords) {
            for (int i = 0; i < bodyWords.size(); i++) {
                if (bodyWords.get(i).getText().equalsIgnoreCase(keyword.getText())) {
                    matches.add(new Match(d, keyword, 1, i + titleWords.size()));
                }
            }
            for (int i = 0; i < titleWords.size(); i++) {
                if (titleWords.get(i).getText().equalsIgnoreCase(keyword.getText())) {
                    matches.add(new Match(d, keyword, 1, i));
                }
            }
        }

        Collections.sort(matches, new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                return Integer.compare(match1.getFirstIndex(), match2.getFirstIndex());
            }
        });

        return matches;
    }

}
