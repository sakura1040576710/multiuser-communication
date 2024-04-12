package network.socket.homework;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Homework02Receive {

    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket(8888);
        byte[] buf = new byte[1024 * 64];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        int length = packet.getLength();
        byte[] data = packet.getData();
        String s = new String(data,0,length);
        System.out.println(s);
        DatagramPacket datagramPacket;
        byte[] send;
        if ("四大名著是哪些".equals(s)){
            send = "红楼梦/三国演义/水浒传/西游记".getBytes();
        }else{
            send = "what?".getBytes();
        }
//        System.out.println("send : " + new String(send,0,send.length));
        datagramPacket = new DatagramPacket(
                send,
                send.length,
                packet.getAddress(),
                packet.getPort()
        );
//        System.out.println(packet.getPort());
        socket.send(datagramPacket);

        socket.close();
    }
}
