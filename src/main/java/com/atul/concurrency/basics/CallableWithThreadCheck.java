package com.atul.concurrency.basics;

import java.util.concurrent.Callable;

class MyClass2 implements Runnable {

	private Callable<Integer> callable;

	public MyClass2(Callable<Integer> callable) {
		this.callable = callable;
	}

	@Override
	public void run() {
		try {
			callable.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

public class CallableWithThreadCheck {

}
