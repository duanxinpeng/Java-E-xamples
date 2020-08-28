import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        // \\ 转义字符 \\\\ 表示 \
        System.out.println(Pattern.matches("\\n","a"));
        //^a以a开始 a$ 以 a 结束 . 匹配任意字符    *0次或多次， + 一次或多次   ？ 0次或一次，
        System.out.println(Pattern.matches("^ab*c+d?a$","abbbbcccda"));
        //{n} 正好匹配n次   {n,}至少匹配n次  {n,m} 至少n次，至多m次
        System.out.println(Pattern.matches("a{2}b{1,}c{3,5}","aabcccc"));
        // ? 用于其他 * + {n}等限定符之后时，表示非贪心的匹配策略，即匹配最短字符串
        System.out.println(Pattern.matches("a*?","aaa"));
        //(pattern) 匹配pattern 并捕获该匹配的子表达式
        //(?:pattern) 非捕获匹配
        //
        System.out.println(Pattern.matches("Windows (?=95|98|NT|2000)","Windows 2000"));
        // x|y 匹配x或y
        System.out.println(Pattern.matches("(x|y)acv","xacv"));
        // [xyz] 字符集
        System.out.println(Pattern.matches("[abc]","a"));
        // [^xyz] 反向字符集
        System.out.println(Pattern.matches("[^abc]","."));
        // [a-z] 字符范围  [^a-z] 反向字符范围
        System.out.println(Pattern.matches("[^a-z]","B"));
        // \b 匹配一个字符边界 er\b 匹配 never 中的er 但不匹配 verb中的er   \B 匹配非字符边界
        System.out.println(Pattern.matches("(.*)er\\b(.*)","neverb"));
        // \d == [0-9]  \D == [^0-9]
        System.out.println(Pattern.matches("\\d","1"));
        // \s 匹配任何 空白字符 == [\f\n\r\t\v]   \S 匹配任何非空白字符
        System.out.println(Pattern.matches("\\s"," "));
        // \w 匹配任何字类字符，包括下划线 == [A-Za-z0-9_]  \W 匹配任何非字类字符
        System.out.println(Pattern.matches("\\w","_"));
    }
}
