package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerNioServer implements Runnable{
    private ServerSocketChannel serverSocketChannel;
    private Selector selector; //多路复用器、事件轮询器
    // volatile？另外一个线程调用stop
    private volatile boolean stop = false;
    public MultiplexerNioServer(int port) {
        try {
            // 获得一个serverChannel
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            // 获得一个多路复用器
            // selector会管理一个ServerSocketChannel和若干个SocketChannel！
            // ServerSocketChannel上面是连接操作，SocketChannel上面是读写操作！
            // selector 放到操作系统中就相当于一个轻量级的线程管理器？
            selector = Selector.open();
            // 注册回调函数？
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("step1: ServerSocketChannel");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                // 阻塞等待，一直到有I/O操作并返回可进行I/O操作的数量！
                int client = selector.select(); // 阻塞？
                System.out.println(client);
                System.out.println("client:"+client);
                if (client == 0)
                    continue;
                //
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    handle(key);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handle(SelectionKey key) throws IOException {
        if (key.isValid()) {
            // 连接事件
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept(); // 3次握手
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);// 连接建立后关注事件
            }
            // 读事件
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer readbuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readbuffer);
                if (readBytes > 0) {
                    readbuffer.flip();//读写模式反转
                    byte[] bytes = new byte[readbuffer.remaining()];
                    readbuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("input:"+body);
                    res(sc,body);
                }
            }
        }
    }
    public void res(SocketChannel channel, String response) throws IOException {
        if (response != null && response.length()>0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
            System.out.println("res end");
        }
    }
}
