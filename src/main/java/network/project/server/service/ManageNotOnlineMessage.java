package network.project.server.service;

import network.project.vo.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageNotOnlineMessage {

    private static Map<String, List<Message>> map = new HashMap<>();

    public static void add(String userId, Message message){
        List<Message> messages;
        if (!map.containsKey(userId)){
            messages = new ArrayList<>();
            messages.add(message);
        }else{
            messages = map.get(userId);
            messages.add(message);
        }
        map.put(userId, messages);
    }

    public static List<Message> get(String userId){
        return map.get(userId);
    }

    public static void remove(String userId){
        map.remove(userId);
    }

}
