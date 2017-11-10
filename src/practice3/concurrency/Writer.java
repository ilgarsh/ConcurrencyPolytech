package practice3.concurrency;

import java.util.List;

public interface Writer {
    void write();
    List<Integer> getMessages();
}
