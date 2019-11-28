package com.inet.xidian.edu.cn.example2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {

    public void selector() throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        java.nio.channels.Selector selector = java.nio.channels.Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//����Ϊ��������ʽ
        ssc.socket().bind(new InetSocketAddress(8080));

        ssc.register(selector, SelectionKey.OP_ACCEPT);//ע��������¼�
        while (true){
            Set selectedKeys = selector.selectedKeys();//ȡ������key����
            Iterator it = selectedKeys.iterator();
            while (it.hasNext()){
                SelectionKey key = (SelectionKey) it.next();
                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssChannel.accept();//���ܵ�����˵�����
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    it.remove();
                }else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
                    SocketChannel sc = (SocketChannel) key.channel();
                    while (true){
                        buffer.clear();
                        int n = sc.read(buffer);
                        if (n < 0){
                            break;
                        }
                        buffer.flip();
                    }
                    it.remove();
                }
            }
        }
    }
}
