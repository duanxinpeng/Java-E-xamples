package nio;

public class NioServer {
    public static void main(String[] args) {
        int port = 8090;
        MultiplexerNioServer nioServer = new MultiplexerNioServer(port);
        new Thread(nioServer,"nioserver-001").start();
    }
}
