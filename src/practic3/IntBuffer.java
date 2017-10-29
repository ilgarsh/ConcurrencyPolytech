package practic3;

import java.util.List;

public interface IntBuffer {
    void read();
    void write();
    List<Integer> getReadMessages();
    List<Integer> getSourceMessages();
}
