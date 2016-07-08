package com.chulung.forwarder.server;

import com.chulung.forwarder.codec.KryoDecoder;
import com.chulung.forwarder.codec.KryoEncoder;
import com.chulung.forwarder.codec.KryoPool;
import com.chulung.forwarder.common.Config;
import com.chulung.forwarder.server.handler.ForwarderServerHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * 转发服务端
 * 
 * @author ChuKai
 *
 */
public class ForwarderServer extends AbstractServer {
	private ForwarderServerHandler forwarderServerHandler = new ForwarderServerHandler();

	@Override
	public ChannelInitializer<Channel> getChildHandler() {
		return new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast(new KryoEncoder(KryoPool.getInstance()));
				ch.pipeline().addLast(new KryoDecoder(KryoPool.getInstance()));
				ch.pipeline().addLast(forwarderServerHandler);
			}
		};
	}

	@Override
	public void start() {
		this.run();
	}

	public static void main(String[] args) throws Exception {
		new ForwarderServer().start();
	}

	@Override
	protected int getPort() {
		return Config.getConfig().getForwaderPort();
	}
}