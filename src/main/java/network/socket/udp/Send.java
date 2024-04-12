package network.socket.udp;

import java.io.IOException;
import java.net.*;

/**
 * 发送端（也是可以接收端）
 */
public class Send {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(9998);
        // 将数据装包
        byte[] data = "hello, receiver".getBytes();
        DatagramPacket packet = new DatagramPacket(
                data,
                data.length,
                InetAddress.getByName("paopaozideMacBook-Pro.local"),
                9999
        );
        // 发送
        socket.send(packet);

        // 接收回信
        byte[] dataReceive = new byte[64 * 1024];
        packet = new DatagramPacket(dataReceive, dataReceive.length);
        socket.receive(packet);
        // 拆包
        byte[] receive = packet.getData();
        int length = packet.getLength();
        System.out.println(new String(receive, 0, length));

        socket.close();
    }
}
