package week3netty.nettyclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;

public class NettyClientIntHandler extends ChannelInboundHandlerAdapter {

//    private static Logger logger = LoggerFactory.getLogger(NettyClientIntHandler.class);
//    @Override
//    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
//        logger.info("channelRead");
//        ByteBuf result = (ByteBuf) msg;
//        byte[] result1 = new byte[result.readableBytes()];
//        result.readBytes(result1);
//        result.release();
//        ctx.close();
//        System.out.println("111");
//
//
//    }
//
//    @Override
//    public  void channelActive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("channelActive");
//        String msg = "test";
//        ByteBuf encoded = ctx.alloc().buffer(4*msg.length());
//        encoded.writeBytes(msg.getBytes());
//        ctx.write(encoded);
//        ctx.flush();
//    }
@Override
public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof HttpResponse) {
        HttpResponse response = (HttpResponse) msg;
        System.out.println(response.toString());
    }
    if (msg instanceof HttpContent) {
        HttpContent content = (HttpContent) msg;
        ByteBuf buf = content.content();
        System.out.println(buf.toString(CharsetUtil.UTF_8));
        buf.release();
    }
}

}
