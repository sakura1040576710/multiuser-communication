package network.socket.homework;

import network.socket.StreamUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Homework03Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        bw.write("心动");
        bw.newLine();
        bw.flush();

        InputStream is = socket.getInputStream();
        byte[] bytes = StreamUtils.streamToByteArray(is);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("src/main/resources/心动1.m4p"));
        bos.write(bytes);

        bos.close();
        is.close();
        bw.close();
        socket.close();
    }
}
