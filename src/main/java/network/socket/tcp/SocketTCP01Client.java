package network.socket.tcp;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTCP01Client {
    public static void main(String[] args) throws Exception {
        // 连接服务器
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);// InetAddress.getLocalHost()需要建立连接的服务器的ip
        System.out.println("client socket = " + socket.getClass());

        // 获取输出流
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello, server".getBytes());

        // 关闭流和socket
        outputStream.close();
        socket.close();
        System.out.println("client exit...");
    }
}
