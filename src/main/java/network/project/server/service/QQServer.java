package network.project.server.service;

import network.project.vo.Message;
import network.project.vo.MessageType;
import network.project.vo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务端
 * 监听端口并保持通信
 */
public class QQServer {

    private ServerSocket ss = null;

    // 集合模拟数据库
    // hashmap不是线程安全的
    // concurrentHashMap是线程安全的
    private static Map<String, User> validUsers = new HashMap<>();

    static {
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("400", new User("400", "123456"));
        validUsers.put("500", new User("500", "123456"));
    }

    private boolean checkUser(String userId, String password) {
        User user = validUsers.get(userId);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    public QQServer() {
        try {
            System.out.println("服务器在9999端口监听....");
            // 启动推送新闻的线程
            new Thread(new SendNews()).start();
            ss = new ServerSocket(9999);
            while (true) {  // 当和某个客户端连接后 会继续监听
                Socket socket = ss.accept();

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();
                Message message = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                if (checkUser(user.getUserId(), user.getPassword())) {
                    message.setType(MessageType.LOGIN_SUCCESS.getMessage());
                    // 创建线程 和客户端保持通信
                    ServerConnectClientThread ssct = new ServerConnectClientThread(socket, user.getUserId());
                    ssct.start();
                    // 线程对象放到集合中管理
                    ManageClientThreads.addThread(user.getUserId(), ssct);
                    oos.writeObject(message);
                    // 私聊未读消息发送
                    if (ManageNotOnlineMessage.get(user.getUserId()) != null){
                        List<Message> messages = ManageNotOnlineMessage.get(user.getUserId());
                        for (Message m : messages){
                            // 这里遇到的bug  https://blog.csdn.net/weixin_44142151/article/details/124188691
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(m);
                        }
                        ManageNotOnlineMessage.remove(user.getUserId());
                    }
//                    socket.shutdownOutput();
                } else {
                    System.out.println("user : " + user.getUserId() + "验证失败");
                    message.setType(MessageType.LOGIN_FAIL.getMessage());
                    oos.writeObject(message);
//                    socket.shutdownOutput();
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
