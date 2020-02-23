package br.com.ufabc.GossipProtocol.util;

import java.util.concurrent.ThreadFactory;

public class ExecutaThreadFactory implements ThreadFactory {
	private int counter = 0;
	private String prefix = "";

	public ExecutaThreadFactory(String prefix) {
		this.prefix = prefix;
	}

	public Thread newThread(Runnable r) {
		return new Thread(r, prefix + "-" + counter++);
	}
}
