package network.socket.homework;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用字符流的方式编写一个客户端和一个服务端
 * 客户端发送'name' 服务器端接收到后返回'我是nova'
 * 客户端发送'hobby' 服务器端接收到后 返回'编写java程序'
 * 不是以上两个问题 则回复'你说啥呢'
 */
public class Homework01Server {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("server is listening on 9999 port....");
        Socket socket = serverSocket.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String content = br.readLine();
        System.out.println(content);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        if ("name".equals(content)){
            bw.write("我是ppz");
        }else if ("hobby".equals(content)){
            bw.write("编写Java程序");
        }else{
            bw.write("你说啥呢");
        }
        bw.newLine();
        bw.flush();

        bw.close();
        br.close();
        socket.close();
    }
}
