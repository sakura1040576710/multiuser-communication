package network.socket.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCP03Server {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buf = new char[1024];
        int len = 0;
        while ((len = reader.read(buf)) != -1) {
            System.out.println(new String(buf, 0, len));
        }
        // 如果使用的是BufferedReader
//        BufferedReader bufferedReader = new BufferedReader(reader);
//        String s = bufferedReader.readLine();
//        System.out.println(s);

        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writer.write("hello, client");
        writer.flush();  // 字符流使用后，必须要关闭（close）或者刷新（flush）才能写入数据
        socket.shutdownOutput();

        writer.close();  // 关闭外层流即可
        reader.close();
        socket.close();

    }
}
