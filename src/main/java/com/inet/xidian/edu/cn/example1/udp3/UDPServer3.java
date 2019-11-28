package com.inet.xidian.edu.cn.example1.udp3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class UDPServer3 {
    public final static int port = 1599;
    public final static int limit = 400;

    public static void main(String[] args) {
        SocketAddress address;
        try {
            address = new InetSocketAddress(port);
        }catch (RuntimeException e){
            return;
        }

        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.connect(address);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            ByteBuffer buffer= ByteBuffer.allocate(4);
            int n = 0;
            int numbersRead = 0;
            while (true){
                if (numbersRead == limit){
                    selector.select(60000);
                    Set<SelectionKey> readKeys = selector.selectedKeys();
                    if (readKeys.isEmpty() && n == limit){
                        break;
                    }else {
                        Iterator<SelectionKey> iterator = readKeys.iterator();
                        while (iterator.hasNext()){
                            SelectionKey key = iterator.next();
                            iterator.remove();
                            if (key.isReadable()){
                                buffer.clear();
                                channel.read(buffer);
                                buffer.flip();
                                int echo = buffer.getInt();
                                System.out.println("read:" + echo);
                                numbersRead++;
                            }

                            if (key.isWritable()){
                                buffer.clear();
                                buffer.putInt(n);
                                buffer.flip();
                                channel.write(buffer);
                                System.out.println("write:" + n);
                                n++;
                                if (n == limit){
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
                System.out.println("echoed:" + numbersRead + " out of" + limit + " sent");

                System.out.println("success rate:" + 1000.0 * numbersRead / limit + "%");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
