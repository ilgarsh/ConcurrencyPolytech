package practice4.concurrency;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WordCounter {

    private Path path;

    private HashMap<String, Integer> words = new HashMap<>();

    public WordCounter(String path) {
        this.path = Paths.get(path);
        try {
            findWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    WordCounter(Path path){
        this.path = path;
        try {
            findWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void findWords() throws IOException {
        Files.lines(this.path)
                .flatMap((Function<String, Stream<String>>) s -> {
                    Pattern p = Pattern.compile("[а-яА-Я]+");
                    Matcher m = p.matcher(s);
                    List<String> matches = new ArrayList<>();
                    while(m.find()){
                        matches.add(m.group());
                    }
                    return matches.stream();
                })
                .map(String::toLowerCase)
                .forEach(s -> {
                    if (words.containsKey(s)) {
                        words.put(s, words.get(s) + 1);
                    } else {
                        words.put(s, 1);
                    }
                });
    }

    Path getPath() {
        return path;
    }

    HashMap<String, Integer> getWords() {
        return words;
    }

}
