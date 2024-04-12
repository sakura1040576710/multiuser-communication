package network.project.vo;

public enum MessageType {

    LOGIN_SUCCESS(1,"登陆成功"),
    LOGIN_FAIL(2,"登陆失败"),
    COMM_MES(3, "普通信息"),
    GET_ONLINE_USERS(4, "获取在线用户列表"),
    RETURN_ONLINE_USERS(5, "返回在线用户列表"),
    CLIENT_EXIT(6, "客户端请求退出"),
    ALL_MES(7, "群发消息"),
    FILE_MES(8, "文件消息"),
    ;


    private Integer index;
    private String message;

    MessageType(Integer index, String message) {
        this.index = index;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static MessageType getByMessage(String message){
        for (MessageType type : values()){
            if (type.getMessage().equals(message)){
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid code");
    }
}
