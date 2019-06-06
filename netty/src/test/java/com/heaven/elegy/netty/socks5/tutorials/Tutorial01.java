package com.heaven.elegy.netty.socks5.tutorials;

import com.heaven.elegy.netty.tutorials.DiscardServer;
import org.junit.Test;

/**
 * @author li.xiaoxi
 * @date 2019/06/06 17:30
 */
public class Tutorial01 {


	@Test
	public void test01() {
		DiscardServer discardServer = new DiscardServer(8080);
		discardServer.run();
	}
}
