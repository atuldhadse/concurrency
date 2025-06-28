package com.atul.concurrency.basics;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class MyClass implements Callable<String> {

	private final String name;

	public MyClass(String name) {
		this.name = name;
	}

	@Override
	public String call() {
		return this.name;
	}

}

public class CallableCheck {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService executor = Executors.newFixedThreadPool(2);
		Callable<String> c1 = new MyClass("class 1");
		Callable<String> c2 = new MyClass("class 2");

		Future<String> future1 = executor.submit(c1);
		Future<String> future2 = executor.submit(c2);

		System.out.println(future1.get());
		System.out.println(future2.get());

//		int count = 0;
//		while (executor.awaitTermination(1, TimeUnit.SECONDS)) {
//			System.out.println(++count);
//		}

		executor.shutdown();
		
	}

}
