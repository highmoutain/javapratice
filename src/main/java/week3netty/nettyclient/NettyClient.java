package week3netty.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;


public class NettyClient {
//    public void connect(String host,int port) {
//        EventLoopGroup wg = new NioEventLoopGroup();
//        try {
//            Bootstrap b = new Bootstrap();
//            b.group(wg);
//            b.channel(NioSocketChannel.class);
//            b.option(ChannelOption.SO_KEEPALIVE,true);
//            b.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//              public void initChannel(SocketChannel ch) throws  Exception {
//                  ch.pipeline().addLast(new HttpResponseDecoder());
////                     客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
//                  ch.pipeline().addLast(new HttpRequestEncoder());
//                  ch.pipeline().addLast(new NettyClientIntHandler());
//              }
//            });
//            ChannelFuture f = b.connect(host, port).sync();
//            //f.channel().write(request);
//            f.channel().flush();
//            f.channel().closeFuture().sync();
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            wg.shutdownGracefully();
//        }
//    }
//
//    public static void main(String[] args) {
//        NettyClient client = new NettyClient();
//        client.connect("127.0.0.1",8888);
//    }
public static void main(String[] args) {
    connect("127.0.0.1", 8888, "test");
}

    public static void connect(String host, int port, String url_str) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            // HttpClientCodec 相当于同时添加了HttpResponseDecoder和HttpRequestEncoder
                            sc.pipeline().addLast(new HttpClientCodec());
                            sc.pipeline().addLast(new NettyClientIntHandler());
                        }
                    });
            Channel channel = bootstrap.connect(host, port).sync().channel();

            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, new URI(url_str).toASCIIString());
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());

            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
