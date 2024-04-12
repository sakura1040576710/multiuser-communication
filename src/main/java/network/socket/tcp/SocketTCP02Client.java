package network.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTCP02Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello, server".getBytes());
        socket.shutdownOutput();  // 设置结束标记

        InputStream inputStream = socket.getInputStream();
        byte[] buf=new byte[1024];
        int len=0;
        while ((len = inputStream.read(buf)) != -1) {
            System.out.println(new String(buf, 0, len));
        }

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
