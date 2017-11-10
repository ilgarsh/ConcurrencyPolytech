package practice3.concurrency;

import java.util.List;

public interface Reader {
    void read();
    List<Integer> getReadMessages();
}
