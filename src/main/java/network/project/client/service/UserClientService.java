package network.project.client.service;


import network.project.vo.Message;
import network.project.vo.MessageType;
import network.project.vo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 完成用户登陆验证和注册
 */
public class UserClientService {

    private User user;
    private Socket socket;

    // 根据userId和password到服务器验证该用户是否合法
    public boolean checkUser(String userId,String password){
        boolean b = false;
        user = new User(userId,password);

        // 连接到服务器
        try {
            socket = new Socket(InetAddress.getLocalHost(),9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
//            socket.shutdownOutput();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();
            if (MessageType.LOGIN_SUCCESS.equals(MessageType.getByMessage(message.getType()))){  // 登陆成功
                // 让线程持有socket 和服务器端保持通信（这里开启了一个新的线程）
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                // 集中管理线程
                ManageClientConnectServerThread.addThread(userId, ccst);

                b = true;
            }else{
                // 登陆失败 关闭socket
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 向服务端请求在线用户列表
     */
    public void onlineUser(String userId){
        Message message = new Message();
        message.setType(MessageType.GET_ONLINE_USERS.getMessage());
        message.setSender(userId);
        message.setGetter("server");

        try {
            // 获取当前线程的socket
            ClientConnectServerThread thread = ManageClientConnectServerThread.getThread(userId);
            Socket socket = thread.getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出连接
     */
    public void logout(String userId){
        Message message = new Message();
        message.setType(MessageType.CLIENT_EXIT.getMessage());
        message.setSender(userId);
        message.setGetter("getter");

        try {
            // 获取当前线程的socket
            ClientConnectServerThread thread = ManageClientConnectServerThread.getThread(userId);
            Socket socket = thread.getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            System.out.println(userId + "退出系统");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
