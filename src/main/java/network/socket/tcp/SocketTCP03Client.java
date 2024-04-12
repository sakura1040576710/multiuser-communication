package network.socket.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTCP03Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writer.write("hello, server");
        writer.flush();  // 字符流使用后，必须要关闭（close）或者刷新（flush）才能写入数据
        socket.shutdownOutput();
        // 如果使用的是BufferedReader 则通过插入一个换行符表示写入的内容结束 就不需要shutdownOutput了
        // 但是 必须要求对方使用readLine()读取
//        BufferedWriter bufferedWriter = new BufferedWriter(writer);
//        bufferedWriter.write("hello, server");
//        bufferedWriter.newLine();
//        bufferedWriter.flush();

        InputStream inputStream = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buf = new char[1024];
        int len = 0;
        while ((len = reader.read(buf)) != -1) {
            System.out.println(new String(buf, 0, len));
        }

        writer.close();
        reader.close();
        socket.close();
    }
}
