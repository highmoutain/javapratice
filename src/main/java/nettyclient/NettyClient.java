package nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.EventListener;


public class NettyClient {
    public void connect(String host,int port) {
        EventLoopGroup wg = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(wg);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE,true);
            b.handler(new ChannelInitializer<SocketChannel>() {
              public void initChannel(SocketChannel ch) throws  Exception {
                  ch.pipeline().addLast(new NettyClientIntHandler());
              }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            wg.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        client.connect("127.0.0.1",8888);
    }
}
