package network.socket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtils {

    /**
     * 将输入流转换成byte[]
     * 把文件的内容读入到byte[]
     * @param is
     * @return
     */
    public static byte[] streamToByteArray(InputStream is) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) {
            bos.write(buf, 0, len);  // 读取到的数据读取到字节数组流
        }
        byte[] array = bos.toByteArray();
        bos.close();
        return array;
    }
}
