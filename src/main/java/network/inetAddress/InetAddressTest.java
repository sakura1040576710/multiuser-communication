package network.inetAddress;

import java.net.InetAddress;

/**
 * InetAddress类的使用
 */
public class InetAddressTest {

    public static void main(String[] args) throws Exception{
        // 获取本机的InetAddress对象
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);  // 计算机的名称和ip地址

        // 获取指定主机名的InetAddress对象
        InetAddress host1 = InetAddress.getByName("paopaozideMacBook-Pro.local");
        System.out.println(host1);  // 计算机的名称和ip地址

        // 根据域名返回InetAddress对象
        InetAddress host2 = InetAddress.getByName("www.baidu.com");
        System.out.println(host2);

        // 通过InetAddress对象，获取对应的地址
        String ip = host2.getHostAddress();  // ip地址
        System.out.println(ip);

        // 通过InetAddress对象，获取对应的主机名/域名
        String name = host2.getHostName();
        System.out.println(name);
    }
}
