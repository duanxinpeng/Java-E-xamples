package bio;

import java.io.*;
import java.net.Socket;

public class SocketHandler implements Runnable{
    public Socket socket;
    public SocketHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            while (true) {
                System.out.println("input:"+bufferedReader.readLine());
                OutputStream out = socket.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
                bufferedWriter.write("success\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
