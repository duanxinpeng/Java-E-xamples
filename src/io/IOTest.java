package io;

import java.io.*;

public class IOTest {

    public void test1() {
        /**
         * IO
         *      1) 字符流
         *          Reader：BufferedReader、InputStreamReader、FileReader、StringReader、CharArrayReader
         *          Writer：BufferedWriter、OutputStreamWriter、StringWriter、FilterWriter
         *      2）字节流
         *          InputStream：FileInputStream、StringBufferInputStream
         *          OutputStream：FileOutputStream、FilterOutputStream
         */
        try {
            Writer writer = new FileWriter("test.txt");
            writer.write("duanxinpeng");
            writer.write('1');
            writer.flush();
            Reader reader = new FileReader("test.txt");
            System.out.println(reader.read());
            StringReader reader1 = new StringReader("test.txt");
            System.out.println(reader1.read());
            BufferedReader reader2 = new BufferedReader(reader);
            System.out.println(reader2.read());
            OutputStream outputStream = new FileOutputStream("byte.txt");
            outputStream.write(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void divide() {
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void test2() {
        divide();
    }
    public static void main(String[] args) {
        IOTest test = new IOTest();
        test.test2();
        Exception exception = new ArithmeticException();
    }
}
