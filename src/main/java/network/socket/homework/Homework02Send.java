package network.socket.homework;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Homework02Send {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(8889);

        byte[] send = "四大名著是哪些".getBytes();
        DatagramPacket packet = new DatagramPacket(
                send,
                send.length,
                InetAddress.getByName("paopaozideMacBook-Pro.local"),
                8888
        );
        socket.send(packet);

        byte[] receive = new byte[64 * 1024];
        DatagramPacket datagramPacket = new DatagramPacket(
                receive,
                receive.length
        );
        socket.receive(datagramPacket);
        byte[] data = datagramPacket.getData();
        int length = datagramPacket.getLength();
        System.out.println(new String(data, 0, length));

        socket.close();
    }
}
