package network.project.vo;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sender;  // 发送者
    private String getter;  // 接收者
    private String content;  // 内容
    private String time;  // 发送时间
    private String type;  // 消息类型
    private byte[] file;  // tcp底层传输的数据是字节
    private int length;  // 文件大小
    private String dest; // 文件传输目的地址
    private String src;  // 文件源文件的地址

    public Message(String sender, String getter, String content, String time, String type) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.time = time;
        this.type = type;
    }

    public Message(String sender, String getter, String content, String time, String type, byte[] file, int length, String dest, String src) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.time = time;
        this.type = type;
        this.file = file;
        this.length = length;
        this.dest = dest;
        this.src = src;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Message() {
    }
}
