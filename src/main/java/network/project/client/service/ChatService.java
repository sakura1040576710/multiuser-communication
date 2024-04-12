package network.project.client.service;

import network.project.vo.Message;
import network.project.vo.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 私聊/群发消息
 */
public class ChatService {

    /**
     * 私聊
     */
    public void chatToOne(String senderId, String getterId, String content){
        Message message = new Message();
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setTime(new Date().toString());
        message.setType(MessageType.COMM_MES.getMessage());
        System.out.println(senderId + " 对 " + getterId + " 说 " + content);

        try {
            ClientConnectServerThread thread = ManageClientConnectServerThread.getThread(senderId);
            ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群聊
     */
    public void chatToAll(String senderId, String content){
        Message message = new Message();
        message.setSender(senderId);
        message.setContent(content);
        message.setTime(new Date().toString());
        message.setType(MessageType.ALL_MES.getMessage());
        System.out.println(senderId + " 对 所有人" + " 说 " + content);

        try {
            ClientConnectServerThread thread = ManageClientConnectServerThread.getThread(senderId);
            ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
