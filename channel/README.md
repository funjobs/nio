# Channel(通道)的主要类型 #
&#160; &#160; &#160; &#160;Channel重要的实现有四种：FileChannel、SocketChannel、ServerSocketChannel、DatagramChannel。

&#160; &#160; &#160; &#160;说明：

&#160; &#160; &#160; &#160;
1. FileChannel文件通道，用于文件数据的读写。

&#160; &#160; &#160; &#160;
2. SocketChannel套接字通道，用于Socket套接字TCP连接数据的读写。

&#160; &#160; &#160; &#160;
3. ServerSocketChannel服务器嵌套字通道(或服务器监听通道)，允许我们监听TCP连接的请求，为每个监听到的请求，创建一个SocketChannel套接字通道。

&#160; &#160; &#160; &#160;
4. DatagramChannel数据报通道，用于UDP协议的数据读写。

&#160; &#160; &#160; &#160;以上四种通道，包含了文件IO、TCP、UD。