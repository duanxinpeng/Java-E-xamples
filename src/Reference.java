import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Reference {
    public static void softReferenceTest() {
        SoftReference<String> softReference = new SoftReference<>(new String("hello"));
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());
    }
    public static void weakReferenceTest() {
        WeakReference<String> weakReference = new WeakReference<>(new String("hello"));
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
    }

    public static void phantomReferenceTest() {
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
        // 不同于前面几种引用，该引用有两个参数
        PhantomReference<String> phantomReference = new PhantomReference<>(new String("hello"),referenceQueue);
        System.out.println(phantomReference.get());
        System.gc();
        System.out.println(referenceQueue.poll());
    }
    public static void main(String[] args) {
        //softReferenceTest();
        //weakReferenceTest();
        phantomReferenceTest();
    }
}
