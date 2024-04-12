package network.socket.tcp;


import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket-tcp
 * server
 */
public class SocketTCP01Server {

    public static void main(String[] args) throws Exception {
        // 监听9999端口 要求本机的9999端口空闲
        // ServerSocket可以通过accept()方法返回多个socket（多个客户端连接）
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("server is listening on 9999 port...");
        // 当没有客户端连接9999端口时 程序会阻塞 等待连接
        // 如果有客户端连接 则会返回socket对象 程序继续运行
        Socket socket = serverSocket.accept();
        System.out.println("server socket = " + socket.getClass());

        // 获取输入流
        InputStream inputStream = socket.getInputStream();
        byte[] buf = new byte[1024];
        int readLen = 0;
        while (( readLen = inputStream.read(buf)) != -1){
            System.out.println(new String(buf,0, readLen));
        }

        inputStream.close();
        socket.close();
    }
}
