package whu.zion;

import whu.zion.filechannel.FileIo;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String src = "C:\\Users\\zhaoxin\\Desktop\\article.txt";
        String desc = "C:\\Users\\zhaoxin\\Desktop\\write.txt";
//        FileIo.read(src);
//        FileIo.write(desc);
//        FileIo.nioFileCopy(src, desc);
        FileIo.nioCopyFileByTransferTo(src, desc);
    }
}
