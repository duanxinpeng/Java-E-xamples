import java.io.IOException;
import java.net.ServerSocket;

public class RandomPort {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println(serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
