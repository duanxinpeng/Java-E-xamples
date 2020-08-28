package bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {
    // 可用telnet连接该服务程序！！
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8090);
        System.out.println("step1: new ServerSocket(8090)");
        ServerHandlerExcutePool pool = new ServerHandlerExcutePool(50,100);
        while (true) {
            Socket client = server.accept();
            System.out.println("step2: client\t"+ client.getPort());
            //new Thread(new SocketHandler(client)).start();
            // 用线程池管理线程！
            pool.execute(new SocketHandler(client));
        }
    }
}
