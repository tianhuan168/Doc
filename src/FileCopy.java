/**
 * @author: tianhuan
 * @description:
 * @Date: 2019/4/1 20:42
 */


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author
 * @create 2019-04-01 20:42
 **/
public class FileCopy {



    public static void copyFile(String srcFileName, String dstFileName) {

        try (FileInputStream in = new FileInputStream(srcFileName); FileOutputStream out = new FileOutputStream(dstFileName)) {
            FileChannel readChannel = in.getChannel();
            FileChannel writeChannel = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                buffer.clear();
                if (readChannel.read(buffer) == 0) {
                    break;
                }
                buffer.flip();
                writeChannel.write(buffer);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
