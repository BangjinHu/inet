package com.inet.xidian.edu.cn.example1.udp2;


import com.inet.xidian.edu.cn.example1.udp1.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent logEvent) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(logEvent.getReceived());
        stringBuilder.append(" [");
        stringBuilder.append(logEvent.getSource().toString());
        stringBuilder.append("] [");
        stringBuilder.append(logEvent.getLogfile());
        stringBuilder.append("] : ");
        stringBuilder.append(logEvent.getMsg());
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
