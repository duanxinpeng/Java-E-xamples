import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JDK8新特性 {
    public static void main(String[] args) {
        //Lambda 表达式
        new Thread(()-> System.out.println(1)).start();
        //方法引用
        List<String> test = new ArrayList<>();
        test.add("duan");
        test.add("xin");
        test.forEach(System.out::println);
        //Stream
        int[] a = {1,2,3};
        System.out.println(Arrays.stream(a).summaryStatistics().getSum());

        Random random = new Random();
        System.out.println(random.ints().limit(10));
    }

    /**
     * 1.本质上是对java集合运算和表达的高阶抽象
     * 2. 他们支持中间操作（map,filter,sort)和终端操作(count, findFirst, forEach, reduce)
     * 3. collect就是一个归约操作
     */
    public static void streamTest() {
        // foreach
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);

        // map
        List<Integer> numbers = Arrays.asList(3,2,2,3,7,3,5);
        List<Integer> squareList = numbers.stream().map(i -> i*i).distinct().collect(Collectors.toList());

        // filter
        List<String> strings = Arrays.asList("abc","","bc","efg","abcd","","jkl");
        long count = strings.stream().filter(string -> string.isEmpty()).count();

        // limit方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据：
        random.ints().limit(10).forEach(System.out::println);

        // sorted
        random.ints().limit(10).sorted().forEach(System.out::println);

        System.out.println("使用java 7");

    }
}
