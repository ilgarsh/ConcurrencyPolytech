package practice4.concurrency;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextFinder {
    private Path folder;
    private List<WordCounter> words = new ArrayList<>();

    private TextFinder(String folder) {
        this.folder = Paths.get(folder);
        try {
            words = Files.walk(this.folder)
                    .filter(Files::isRegularFile)
                    .map(WordCounter::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<WordCounter> getWords() {
        return words;
    }

    public Path getFolder() {
        return folder;
    }


    public static void main(String[] args) {
        TextFinder textFinder =
                new TextFinder("/home/ilgarsh/IdeaProjects/ConcurrencyPolytech/labs/Practik4/txt");

        textFinder
                .getWords()
                .forEach(
                        wordCounter -> {
                            System.out.println(wordCounter.getPath().getFileName());
                            wordCounter.getWords().forEach((s, integer) -> {
                                if (integer > 8) {
                                    System.out.println(s + ": " + integer);
                                }
                            });
                        });
    }
}
