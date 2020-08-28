import java.util.ArrayList;

public class Genericity {
    public static void main(String[] args) {
        ArrayList list = new ArrayList<>();
        list.add(123);
        list.add("duan");
        for (int i = 0; i < 2; i++) {
            System.out.println(list.get(i));
        }
    }
}
