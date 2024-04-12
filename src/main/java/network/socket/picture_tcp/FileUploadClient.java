package network.socket.picture_tcp;

import network.socket.StreamUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class FileUploadClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        // 从本地读取图片
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("src/main/resources/test.JPG"));
        byte[] bytes = StreamUtils.streamToByteArray(bis);  // bytes/bis都是图片数据

        // socket获取到输出流 将bytes数组数据发送
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(bytes);
        // 设置结束标志
        socket.shutdownOutput();

        InputStream is = socket.getInputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) {
            System.out.println(new String(buf, 0, len));
        }

        // 关闭
        bis.close();
        bos.close();
        is.close();
        socket.close();
    }
}
