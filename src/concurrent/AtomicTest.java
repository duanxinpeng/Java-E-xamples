package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger a = new AtomicInteger();
        a.set(10);
        System.out.println(a.get());
    }
}
