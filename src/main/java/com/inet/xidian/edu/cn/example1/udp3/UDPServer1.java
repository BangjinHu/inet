package com.inet.xidian.edu.cn.example1.udp3;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPServer1 {

    public final static int port = 1599;
    public final static int max_packet_size = 65502;

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();
            SocketAddress address = new InetSocketAddress(port);
            socket.bind(address);

            ByteBuffer buffer = ByteBuffer.allocateDirect(max_packet_size);
            while (true){
                SocketAddress client = channel.receive(buffer);
                buffer.flip();
                System.out.println(client + "say: ");
                while (buffer.hasRemaining()){
                    System.out.println(buffer.get());
                }
                System.out.println();
                buffer.clear();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
