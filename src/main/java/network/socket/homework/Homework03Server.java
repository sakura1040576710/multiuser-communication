package network.socket.homework;

import network.socket.StreamUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Homework03Server {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();

        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = br.readLine();
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        byte[] bytes;
        if ("心动".equals(s)){
            FileInputStream fis = new FileInputStream(new File("src/main/resources/心动.m4p"));
            bytes = StreamUtils.streamToByteArray(fis);
            fis.close();
        }else{
            bytes = "null".getBytes();
        }
        bos.write(bytes);
        socket.shutdownOutput();

        is.close();
        bos.close();
        socket.close();
    }
}
