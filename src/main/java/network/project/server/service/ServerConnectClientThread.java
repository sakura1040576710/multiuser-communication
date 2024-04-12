package network.project.server.service;

import network.project.vo.Message;
import network.project.vo.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * server -> 某个client
 */
public class ServerConnectClientThread extends Thread{

    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        System.out.println("服务器和客户端" + userId + "保持通信......");
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (MessageType.GET_ONLINE_USERS.equals(MessageType.getByMessage(message.getType()))){
                    System.out.println(message.getSender() + "需要在线用户列表");
                    String users = ManageClientThreads.getUsers();
                    Message returnMessage = new Message();
                    returnMessage.setType(MessageType.RETURN_ONLINE_USERS.getMessage());
                    returnMessage.setContent(users);
                    returnMessage.setSender(message.getGetter());
                    returnMessage.setGetter(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(returnMessage);
                } else if (MessageType.CLIENT_EXIT.equals(MessageType.getByMessage(message.getType()))){
                    System.out.println(message.getSender() + "退出");
                    // 将客户端对应的线程 从集合删除
                    ManageClientThreads.removeThread(message.getSender());
                    socket.close();
                    break;  // 退出线程
                } else if (MessageType.COMM_MES.equals(MessageType.getByMessage(message.getType()))){
                    // 私聊
                    String getterId = message.getGetter();
                    ServerConnectClientThread thread = ManageClientThreads.getThread(getterId);
                    // 当用户不存在时 需要暂存消息
                    if (thread == null){
                        System.out.println(getterId + " 用户不在线  已暂存消息");
                        ManageNotOnlineMessage.add(getterId, message);
                    } else{
                        ObjectOutputStream oos = new ObjectOutputStream(thread.socket.getOutputStream());
                        oos.writeObject(message);
                    }
                } else if (MessageType.ALL_MES.equals(MessageType.getByMessage(message.getType()))){
                    // 群发
                    List<Socket> sockets = ManageClientThreads.getAllUsers(userId);
                    for (Socket s : sockets){
                        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeObject(message);
                    }
                } else if (MessageType.FILE_MES.equals(MessageType.getByMessage(message.getType()))){
                    String getter = message.getGetter();
                    Socket socket = ManageClientThreads.getThread(getter).getSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                } else{
                    System.out.println("----------todo----------");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
