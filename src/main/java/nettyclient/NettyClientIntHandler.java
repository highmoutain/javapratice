package nettyclient;

import gateway.inbound.HttpInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.List;

public class NettyClientIntHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(NettyClientIntHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
        logger.info("channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        result.release();
        ctx.close();
        System.out.println("111");


    }

    @Override
    public  void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive");
        String msg = "test";
        ByteBuf encoded = ctx.alloc().buffer(4*msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

}
