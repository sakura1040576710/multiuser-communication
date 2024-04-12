package network.project.server.service;

import java.net.Socket;
import java.util.*;

/**
 * 管理和客户端进行通信的线程
 */
public class ManageClientThreads {

    private static Map<String, ServerConnectClientThread> map = new HashMap<>();

    public static void addThread(String userId, ServerConnectClientThread thread){
        map.put(userId,thread);
    }

    public static ServerConnectClientThread getThread(String userId){
        return map.get(userId);
    }

    public static String getUsers(){
        Set<String> users = map.keySet();
        return String.join(" ", users);
    }

    public static void removeThread(String userId){
        map.remove(userId);
    }

    public static List<Socket> getAllUsers(String userId){
        List<Socket> sockets = new ArrayList<>();
        for (String user : map.keySet()){
            if (!user.equals(userId)){
                sockets.add(map.get(user).getSocket());
            }
        }
        return sockets;
    }
}
