package whu.zion.filechannel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhaoxin
 */
public class FileIo {

    /**
     * 读取 - FileChannel 管道
     *
     * @param src 文件路径
     */
    public static void read(String src) {
        // 判断文件是否存在，不存在则中止
        boolean exists = Files.exists(Paths.get(src));
        if (!exists) {
            System.out.println("源文件不存在!");
            return;
        }

        // 获取FileChannel
        try (FileInputStream inputStream = new FileInputStream(src); FileChannel channel = inputStream.getChannel();) {

            // 初始化一个字节的缓存区 - 默认写入模式
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 已读长度
            int length = -1;

            while ((length = channel.read(buffer)) != -1) {
                // 切换为读取模式
                buffer.flip();
                byte[] bytes = buffer.array();
                String str = new String(bytes, 0, length);
                System.out.println(str);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 写入-FileChannel管道
     *
     * @param desc 目标文件路径
     */
    public static void write(String desc) {

        // 这里使用 RandomAccessFile 文件随机访问类，获得 FileChannel 文件通道
        // “r"	打开文件仅仅是为了读取数据，如果尝试调用任何写入数据的操作都会造成返回IOException错误信息的问题。
        // "rw"	打开文件用于读写两种操作，如果文件本身并不存在，则会创建一个全新的文件。
        // "rws" 打开文件用于读写两种操作，这点和”rw“的操作完全一致，同步模式,每write修改一个byte,立马写到磁盘,间性能差点儿,适合小的文件
        // "rwd" 模式跟个rws基础的一样..不过,只对“文件的内容”同步更新到磁盘...不对metadata同步更新..

        try (RandomAccessFile raf = new RandomAccessFile(desc, "rwd"); FileChannel outChannel = raf.getChannel();) {

            String text = "你好,FileChannel!";
            byte[] bytes = text.getBytes(Charset.defaultCharset());
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            // 切换到读取模式
            byteBuffer.flip();
            int outLength = 0;
            while ((outLength = outChannel.write(byteBuffer)) != 0) {
                System.out.println("已写入字节数：" + outLength);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 复制文件
     * @param src 源文件
     * @param desc 目标文件
     */
    public static void nioFileCopy(String src, String desc) {
        // 判断文件是否存在，不存在则中止
        boolean exists = Files.exists(Paths.get(src));
        if (!exists) {
            System.out.println("源文件不存在!");
            return;
        }

        // 获取FileChannel
        try (FileInputStream inputStream = new FileInputStream(src); FileChannel inChannel = inputStream.getChannel();
             FileOutputStream outputStream = new FileOutputStream(desc); FileChannel outChannel = outputStream.getChannel();) {

            // 初始化一个字节的缓存区 - 默认写入模式
            ByteBuffer buffer = ByteBuffer.allocate(1024);
           int inLength = -1;
            while ((inLength = inChannel.read(buffer)) != -1) {
                // 切换为读取模式
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 TransferTo 复制文件, 一次最大复制2G
     * @param src 源文件
     * @param desc 目标文件
     */
    public static void nioCopyFileByTransferTo(String src, String desc) {
// 判断文件是否存在，不存在则中止
        boolean exists = Files.exists(Paths.get(src));
        if (!exists) {
            System.out.println("源文件不存在!");
            return;
        }

        // 获取FileChannel
        try (FileInputStream inputStream = new FileInputStream(src); FileChannel inChannel = inputStream.getChannel();
             FileOutputStream outputStream = new FileOutputStream(desc); FileChannel outChannel = outputStream.getChannel();) {
            // 开始位置，最大长度，目标channel
            inChannel.transferTo(0, inChannel.size(), outChannel);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
