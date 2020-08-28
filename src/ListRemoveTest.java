import java.util.ArrayList;
import java.util.Iterator;

public class ListRemoveTest {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
//        for (int i = 0; i < list.size(); i++) {
//            list.remove(i);
//        }
        //list.forEach(list::remove);
//        for (Iterator t = list.iterator();t.hasNext();){
//            t.next();
//            t.remove();
//        }
        for (Integer i : list) {
            list.remove(i);
        }
        System.out.println(list.toString());
    }
}
