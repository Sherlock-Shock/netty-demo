package netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.demo.ServerHandler;

/**
 * <p>
 * TODO 类的描述
 * </p>
 *
 * @author derui <752750447@qq.com>
 * @date 2021-03-22 13:01
 * @since 1.0.0
 */
public class Server {

    private int port;

    public Server(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new Server(9999).start();
    }

    void start() throws Exception{
        final ServerHandler serverHandler = new ServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    })
                    .localAddress(port)
                    .channel(NioServerSocketChannel.class);

            ChannelFuture future = serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }




    }





}
