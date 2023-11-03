package engine;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        title = new ArrayList<>();
        body = new ArrayList<>();

        String[] lines = content.split("\n");

        if (lines.length >= 1) {
            String[] titleWords = lines[0].replace("\r"," ").split("\\s+");
            for (String word : titleWords) {
                Word wordObj = Word.createWord(word);
                title.add(wordObj);
            }
        }

        if (lines.length >= 2) {
            String[] bodyWords = lines[1].replace("\r"," ").split("\\s+");
            for (String word : bodyWords) {
                Word wordObj = Word.createWord(word);
                body.add(wordObj);
            }
        }
    }

    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doc)) return false;
        Doc otherDoc = (Doc) o;
        return title.equals(otherDoc.title) && body.equals(otherDoc.body);
    }
}


