package network.project.client.service;

import network.project.vo.Message;
import network.project.vo.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 文件传输
 */
public class FileService {

    public void sendFileToOne(String src, String dest, String sender, String getter){
        Message message = new Message();
        message.setSender(sender);
        message.setGetter(getter);
        message.setSrc(src);
        message.setDest(dest);
        message.setType(MessageType.FILE_MES.getMessage());

        // 读取源文件
        byte[] bytes = new byte[(int)new File(src).length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(src);
            fis.read(bytes);
            message.setFile(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 发送
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
