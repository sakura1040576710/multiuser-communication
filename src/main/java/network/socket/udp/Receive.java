package network.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 接收端
 */
public class Receive {

    public static void main(String[] args) throws IOException {
        // 创建datagram socket对象  监听9999端口
        DatagramSocket socket = new DatagramSocket(9999);
        // 构建datagram packet对象  用于接收数据
        byte[] buf = new byte[64 * 1024];   // UDP数据包最大64k
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // 接收数据
        socket.receive(packet);  // 如果没有在9999端口收到数据 则会阻塞

        // 将packet进行拆包 并取出数据
        int length = packet.getLength();  // 获取实际数据的长度
        byte[] data = packet.getData();

        String s = new String(data, 0, length);
        System.out.println(s);

        // 回送数据
        byte[] dataSend = "hello, send".getBytes();
        DatagramPacket send = new DatagramPacket(
                dataSend,
                dataSend.length,
                packet.getAddress(),
                packet.getPort()
        );
        socket.send(send);

        // 关闭
        socket.close();
    }
}
