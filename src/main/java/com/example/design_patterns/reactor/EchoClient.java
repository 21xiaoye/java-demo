package com.example.design_patterns.reactor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class EchoClient {
    private static final Logger logger = LoggerFactory.getLogger(EchoClient.class);

    public void start() throws IOException {

        InetSocketAddress address =
                new InetSocketAddress(6771);

        // 1、获取通道（channel）
        SocketChannel socketChannel = SocketChannel.open(address);
        logger.info("客户端连接成功");
        // 2、切换成非阻塞模式
        socketChannel.configureBlocking(false);
        socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        //不断的自旋、等待连接完成，或者做一些其他的事情
        while (!socketChannel.finishConnect()) {

        }
        logger.info("客户端启动成功！");

        //启动接受线程
        Processor processor = new Processor(socketChannel);
        Commander commander = new Commander(processor);
        new Thread(commander).start();
        new Thread(processor).start();

    }

    static class Commander implements Runnable {
        Processor processor;

        Commander(Processor processor) throws IOException {
            //Reactor初始化
            this.processor = processor;
        }

        public void run() {
            while (!Thread.interrupted()) {

                ByteBuffer buffer = processor.getSendBuffer();

                Scanner scanner = new Scanner(System.in);
                while (processor.hasData.get()) {
                    logger.info("还有消息没有发送完，请稍等");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                logger.info("请输入发送内容:");
                if (scanner.hasNext()) {

                    String next = scanner.next();
                    buffer.put((" >>" + next).getBytes());

                    processor.hasData.set(true);
                }

            }
        }
    }


    static class Processor implements Runnable {
        ByteBuffer sendBuffer = ByteBuffer.allocate(1024);

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        public ByteBuffer getSendBuffer() {
            return sendBuffer;
        }

        public void setSendBuffer(ByteBuffer sendBuffer) {
            this.sendBuffer = sendBuffer;
        }

        public ByteBuffer getReadBuffer() {
            return readBuffer;
        }

        public void setReadBuffer(ByteBuffer readBuffer) {
            this.readBuffer = readBuffer;
        }

        protected AtomicBoolean hasData = new AtomicBoolean(false);

        final Selector selector;
        final SocketChannel channel;

        Processor(SocketChannel channel) throws IOException {
            //Reactor初始化
            selector = Selector.open();

            this.channel = channel;
            channel.register(selector,
                    SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }

        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        SelectionKey sk = it.next();
                        if (sk.isWritable()) {

                            if (hasData.get()) {
                                SocketChannel socketChannel = (SocketChannel) sk.channel();
                                sendBuffer.flip();
                                // 操作三：发送数据
                                socketChannel.write(sendBuffer);
                                sendBuffer.clear();
                                hasData.set(false);
                            }

                        }
                        if (sk.isReadable()) {
                            // 若选择键的IO事件是“可读”事件,读取数据
                            SocketChannel socketChannel = (SocketChannel) sk.channel();

                            int length = 0;
                            while ((length = socketChannel.read(readBuffer)) > 0) {
                                readBuffer.flip();
                                logger.info("server echo:" + new String(readBuffer.array(), 0, length));
                                readBuffer.clear();
                            }

                        }
                        //处理结束了, 这里不能关闭select key，需要重复使用
                        //selectionKey.cancel();
                    }
                    selected.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoClient().start();
    }

    /**
     * 读取Http请求的请求行，不能使用这个方法来读取Http请求的正文
     *
     * @return true表示有数据被读取， false没有数据被读取，需要释放线程
     * @throws IOException 在读取 HTTP 请求行过程当中发生I/O错误
     */
//    public boolean parseRequestLine() throws IOException {
//        if (!parsingRequestLine) {
//            return true;
//        }
//        if (parsingRequestLinePhase < 2) {
//            if (!f()) return false;
//            parsingRequestLinePhase = 4;
//        }
//        // 读取此次请求的请求类型GET、POST...
//        if (parsingRequestLinePhase == 2) {
//            boolean space = false;
//            while (!space) {
//                int pos = byteBuffer.position();
//                chr = byteBuffer.get();
//                if (chr == Constants.SP || chr == Constants.HT) {
//                    space = true;
//                    request.setStrVal(Request.Type.METHOD_MB, byteBuffer.array(), parsingRequestLineStart,
//                            pos - parsingRequestLineStart);
//                    parsingRequestLineStart = byteBuffer.position();
//                    parsingRequestLinePhase = 3;
//                }
//            }
//        }
//        // 读取请求路径和路径参数
//        if(parsingRequestLinePhase == 3){
//            boolean space = false;
//            int pos = -1;
//            while (!space) {
//                pos = byteBuffer.position();
//                chr = byteBuffer.get();
//                // "?" 后面为路径参数，这里标记参数开始位置
//                if(chr == Constants.QUESTION){
//                    parsingRequestLineQPos = byteBuffer.position();
//                }
//                if (chr == Constants.SP || chr == Constants.HT) {
//                    space = true;
//                    parsingRequestLinePhase = 4;
//                }
//            }
//            // 有参数需要处理， 这里-1 是为了忽略 "?" 符号，"?"前为请求路径，后为参数
//            if(parsingRequestLineQPos >= 0){
//                request.setStrVal(Request.Type.URI_MB,  byteBuffer.array(), parsingRequestLineStart,
//                        parsingRequestLineQPos - 1);
//                request.setStrVal(Request.Type.QUERY_MB, byteBuffer.array(), parsingRequestLineQPos,
//                        pos);
//            }else{
//                request.setStrVal(Request.Type.URI_MB,  byteBuffer.array(), parsingRequestLineStart,
//                        pos - parsingRequestLineStart);
//            }
//            parsingRequestLineStart = byteBuffer.position();
//        }
//        // 读取请求协议
//        if(parsingRequestLinePhase == 4){
//            boolean space = false;
//            while (!space) {
//                int pos = byteBuffer.position();
//                chr = byteBuffer.get();
//                if (chr == Constants.CR || chr == Constants.LF) {
//                    space = true;
//                    request.setStrVal(Request.Type.PROTO_MB, byteBuffer.array(), parsingRequestLineStart,
//                            pos);
//                    parsingRequestLineStart = byteBuffer.position();
//                    parsingRequestLinePhase = 5;
//                }
//            }
//        }
//        if (parsingRequestLinePhase == 5) {
//            parsingRequestLine = false;
//            parsingRequestLinePhase = 0;
//            parsingRequestLineStart = 0;
//            parsingRequestLineQPos = 0;
//            return true;
//        }
//        return false;
//    }
}
