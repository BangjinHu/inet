package com.inet.xidian.edu.cn.example1.udp2;

import com.inet.xidian.edu.cn.example1.udp1.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf byteBuf = datagramPacket.content();
        int idx = byteBuf.indexOf(0, byteBuf.readableBytes(), LogEvent.SEPARATOR);
        String fileName = byteBuf.slice(0, idx).toString(CharsetUtil.UTF_8);
        String logMsg = byteBuf.slice(idx + 1, byteBuf.readableBytes()).toString(CharsetUtil.UTF_8);

        LogEvent logEvent = new LogEvent(datagramPacket.sender(), logMsg, fileName, System.currentTimeMillis());
        out.add(logEvent);
    }
}
