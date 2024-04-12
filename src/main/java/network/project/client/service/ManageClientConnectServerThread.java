package network.project.client.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理线程
 */
public class ManageClientConnectServerThread {

    private static Map<String, ClientConnectServerThread> map = new HashMap<>();

    public static void addThread(String userId, ClientConnectServerThread thread){
        map.put(userId,thread);
    }

    public static ClientConnectServerThread getThread(String userId){
        return map.get(userId);
    }

}
