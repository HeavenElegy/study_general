package com.heaven.elegy.netty.socks5;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author li.xiaoxi
 * @date 2019/06/06 15:53
 */
public class Sock5 {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void startServer() throws InterruptedException {
		Demo demo = new Demo();
		demo.start();
	}

	@Test
	public void visit() throws IOException {
		OkHttpClient httpClient = new OkHttpClient.Builder()
				// 设置代理，使其指向代理socks服务
				.proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 8801)))
				// 设置连接超时
				.connectTimeout(10, TimeUnit.SECONDS)
				// 读取超时
				.readTimeout(10, TimeUnit.SECONDS)
				// 写入超时
				.writeTimeout(10, TimeUnit.SECONDS)
				.build();

		Request request = new Request.Builder()
				.url("http://www.baidu.com")
				.build();

		Response response = httpClient.newCall(request)
				.execute();

		log.info("请求百度。body:{}", response.body().string());


	}

}
