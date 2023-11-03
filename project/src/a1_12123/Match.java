package engine;

public class Match implements Comparable<Match> {

    private Doc doc;
    private Word word;
    private int frequency;
    private int firstIndex;
    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.doc = d;
        this.word = w;
        this.frequency = freq;
        this.firstIndex = firstIndex;
    }

    public Word getWord(){
        return word;
    }
    public int getFreq(){
        return frequency;
    }
    public int getFirstIndex(){
        return firstIndex;
    }
    public int compareTo(Match otherMatch) {
        return Integer.compare(this.firstIndex, otherMatch.firstIndex);
    }
}
