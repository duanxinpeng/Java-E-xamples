import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.io.*;

public class SerializationTest {
    static class Person  {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
    public static byte[] toByteArray(Object obj) {
        /**
         * 将obj序列化为byte数组
         */
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            // writeObject 序列化
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            // 转为 byte 数组
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            // readObject 反序列化
            obj = objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static byte[] toBytesArrayProtoStuff(Person obj) {
        /**
         * 利用ProtoStuff进行序列化
         */
        RuntimeSchema<Person> schema = RuntimeSchema.createFrom(Person.class);
        byte[] bytes = ProtostuffIOUtil.toByteArray(obj,schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        return bytes;
    }

    public static Person toObjectProtoStuff(byte[] bytes) {
        /**
         * 利用ProtoStuff反序列化
         */
        RuntimeSchema<Person> schema = RuntimeSchema.createFrom(Person.class);
        Person obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes,obj,schema);
        return obj;
    }

    public static void main(String[] args) {
        /**
         * 使用ProtoStuff序列化有以下特点
         *      1. 不需要实现 Serializable 接口
         *      2. 只能对POJO进行序列化
         */
        Person person = new Person("duanxinpeng",25);
        byte[] res = toBytesArrayProtoStuff(person);
        Person person1 = (Person)toObjectProtoStuff(res);
        System.out.println(person1.name);
    }
}
