import org.openjdk.jol.info.ClassLayout;

public class ObjectStructureTest {
    public static void main(String[] args) {
        /**
         * 通过jol工具来查看Object对象的结构！
         */
        Object o = new Object();
        int[] t = new int[5];
        String s = ClassLayout.parseInstance(t).toPrintable();
        System.out.println(s);
    }
}
