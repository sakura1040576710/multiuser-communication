package network.socket.picture_tcp;

import network.socket.StreamUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileUploadServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("server is on 8888 port...");
        Socket socket = serverSocket.accept();

        // 读取客户端发送的数据
        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] bytes = StreamUtils.streamToByteArray(bis);  // 图片的字节数组

        // 输出图片信息
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("src/main/resources/target.JPG"));
        bos.write(bytes);

        // 写回信息
        OutputStream os = socket.getOutputStream();
        os.write("save picture successful !".getBytes());
        socket.shutdownOutput();

        os.close();
        bos.close();
        bis.close();
        socket.close();
    }
}
