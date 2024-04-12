package network.project.client.service;

import network.project.vo.Message;
import network.project.vo.MessageType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{

    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        System.out.println("客户端线程，等待读取从服务器端发送的消息...");
        // thread需要在后台和服务器通信，所以需要循环
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());  // 如果服务器没有发送数据 会阻塞在这里
                Message message = (Message) ois.readObject();
                if (MessageType.RETURN_ONLINE_USERS.equals(MessageType.getByMessage(message.getType()))){
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n========当前在线用户列表========");
                    for (String user : onlineUsers){
                        System.out.println("用户 : " + user);
                    }
                } else if (MessageType.COMM_MES.equals(MessageType.getByMessage(message.getType()))){
                    System.out.println("\n" + message.getSender()
                                        + " 对 " + message.getGetter() + " 说 : "
                                        + message.getContent());
                } else if (MessageType.ALL_MES.equals(MessageType.getByMessage(message.getType()))){
                    System.out.println("\n" + message.getSender()
                            + " 对 大家" + " 说 : "
                            + message.getContent());
                } else if (MessageType.FILE_MES.equals(MessageType.getByMessage(message.getType()))){
                    System.out.println("\n" + message.getSender() + " 给 " + message.getGetter()
                                        + " 发文件: " + message.getSrc() + " 到我的电脑的目录 " + message.getDest());
                    // 取出字节数组 写出到磁盘
                    FileOutputStream fos = new FileOutputStream(message.getDest());
                    fos.write(message.getFile());
                    fos.close();
                    System.out.println("\n 保存文件成功");
                }else{
                    System.out.println("----------todo----------");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
