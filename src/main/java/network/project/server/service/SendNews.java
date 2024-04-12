package network.project.server.service;

import network.project.util.Utility;
import network.project.vo.Message;
import network.project.vo.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;


/**
 * 服务器端推送消息
 */
public class SendNews implements Runnable{
    @Override
    public void run() {
        while (true){  // 多次推送
            System.out.println("请输入服务器要推送的信息[输入exit退出]");
            String content = Utility.readString(100);
            if ("exit".equals(content)){
                break;
            }
            Message message = new Message();
            message.setSender("server");
            message.setContent(content);
            message.setType(MessageType.ALL_MES.getMessage());
            message.setTime(new Date().toString());
            System.out.println("服务器推送消息给所有人 : " + content);

            // 获取所有socket
            List<Socket> sockets = ManageClientThreads.getAllUsers(null);
            ObjectOutputStream oos;
            for (Socket socket : sockets){
                try {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
