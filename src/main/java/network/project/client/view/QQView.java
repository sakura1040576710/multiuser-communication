package network.project.client.view;

import network.project.client.service.ChatService;
import network.project.client.service.FileService;
import network.project.client.service.UserClientService;
import network.project.util.Utility;

/**
 * menu
 */
public class QQView {

    private boolean loop = true;
    private String key;  // 键盘输入
    private UserClientService service = new UserClientService();
    private ChatService chatService = new ChatService();
    private FileService fileService = new FileService();

    // 显示主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("===========欢迎登陆网络通信系统===========");
            System.out.println("\t\t 1. 登陆系统");
            System.out.println("\t\t 9. 退出系统");

            System.out.print("请输入你的选择：");
            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密  码：");
                    String password = Utility.readString(50);

                    // 去服务端验证用户是否合法
                    if (service.checkUser(userId, password)){
                        System.out.println("===========欢迎 ( 用户" + userId + " ) ===========");
                        secondMenu(userId);
                    }else{
                        System.out.println("===========登陆失败===========");
                    }
                    break;
                case "9":
                    System.out.println("退出系统");
                    loop = false;
                    break;
            }

        }
    }

    private void secondMenu(String userId){
        String content;
        String getterId;
        while (loop){
            System.out.println("===========网络通信系统二级菜单 ( 用户" + userId + " ) ===========");
            System.out.println("\t\t 1. 显示在线用户列表");
            System.out.println("\t\t 2. 群发消息");
            System.out.println("\t\t 3. 私聊消息");
            System.out.println("\t\t 4. 发送文件");
            System.out.println("\t\t 9. 退出系统");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);

            switch (key){
                case "1":
                    System.out.println("显示在线用户列表");
                    service.onlineUser(userId);
                    break;
                case "2":
                    System.out.print("请输入想对大家说的话：");
                    content = Utility.readString(100);
                    chatService.chatToAll(userId, content);
//                    System.out.println("群发消息");
                    break;
                case "3":
                    System.out.print("请输入想聊天的用户号：");
                    getterId = Utility.readString(3);
                    System.out.print("请输入想说的话：");
                    content = Utility.readString(100);
                    chatService.chatToOne(userId, getterId, content);
//                    System.out.println("私聊消息");
                    break;
                case "4":
                    System.out.print("请输入要发送文件的用户号（仅支持在线用户）：");
                    getterId = Utility.readString(3);
                    System.out.print("请输入源文件路径：");
                    String src = Utility.readString(50);
                    System.out.print("请输入目标文件路径：");
                    String dest = Utility.readString(50);
                    fileService.sendFileToOne(src, dest, userId, getterId);
                    System.out.println("\n" + userId + " 给 " + getterId + " 发送文件: " + src
                                        + " 到对方的: " + dest + " 目录");
//                    System.out.println("发送文件");
                    break;
                case "9":
                    System.out.println("退出系统");
                    // 给服务器发送退出系统的message 让线程断开 否则主线程结束但子线程未结束要报错
                    service.logout(userId);
                    loop = false;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统.......");
    }
}
