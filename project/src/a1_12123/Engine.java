package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Engine {
    private List<Doc> docs;

    public Engine() {
        docs = new ArrayList<>();
    }
    public int loadDocs(String dirname){
        File folder = new File(dirname);
        File[] files = folder.listFiles();

        if (files == null) {
            return 0;
        }

        Arrays.sort(files);
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")){
                try {
                    Scanner scanner = new Scanner(file);
                    StringBuilder content = new StringBuilder();
                    while (scanner.hasNextLine()){
                        content.append(scanner.nextLine()).append("\n");
                    }
                    Doc doc = new Doc(content.toString());
                    docs.add(doc);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return docs.size();
    }
    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();

        for (Doc doc : docs) {
            List<Match> matches = new ArrayList<>();
            List<Word> titleWords = doc.getTitle();
            List<Word> bodyWords = doc.getBody();

            for (Word keyword : q.getKeywords()) {
                int titleMatchCount = 0;
                int bodyMatchCount = 0;

                // Count matches in the title
                for (int i = 0; i < titleWords.size(); i++) {
                    if (titleWords.get(i).equals(keyword)) {
                        titleMatchCount++;
                    }
                }

                // Count matches in the body
                for (int i = 0; i < bodyWords.size(); i++) {
                    if (bodyWords.get(i).equals(keyword)) {
                        bodyMatchCount++;
                    }
                }

                if (titleMatchCount > 0 || bodyMatchCount > 0) {
                    // Add matches for this keyword to the list
                    matches.add(new Match(doc, keyword, titleMatchCount + bodyMatchCount, titleMatchCount));
                }
            }

            if (!matches.isEmpty()) {
                results.add(new Result(doc, matches));
            }
        }

        results.sort(Result::compareTo);
        return results;
    }


    public String htmlResult(List<Result> results) {
        StringBuilder html = new StringBuilder();
        for (Result result : results) {
            html.append(result.htmlHighlight().trim());
        }
        return html.toString();
    }
}
