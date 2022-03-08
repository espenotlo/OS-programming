package norseninja.producer_consumer;

class Buffer {

    private final char [] buffer;
    private int count = 0;

    public Buffer(int size) {
        buffer = new char[size];
    }

    public synchronized boolean put(char c) {
        if (count < buffer.length) {
            buffer[count] = c;
            count++;
            return true;
        } else return false;
    }
    public synchronized char get() {
        if (count > 0) {
            char c = buffer[0];
            count--;
            for (int i = 1; i < count; i++) {
                buffer[i-1] = buffer[i];
            }
            return c;
        } else return '\u0000';
    }

    public int getCount() {
        return count;
    }
}