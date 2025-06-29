package com.atul.concurrency.threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {

	private final int i;

	public Task(int i) {
		this.i = i;
	}

	@Override
	public void run() {

		System.out.println("task started for -> " + i);
		try {
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + " entering the synchronized block, for -> " + i);
			synchronized (this) {
				System.out.println("task started in synchronized block for -> " + i);
				this.wait(1000);
				System.out.println("task over in synchronized block for -> " + i);
			}
			System.out.println("task completed for -> " + i);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}

public class ThreadPoolCheck {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(3);
		System.out.println("Thread pool created");

		for (int i = 1; i <= 5; i++) {
			final int j = i;
			executor.execute(new Task(j));
		}

		executor.shutdown();
		System.out.println("Thread pool shutdown intiated");

		try {
			if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
				executor.shutdownNow();
				System.out.println("Forcing shutdown");
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}

		System.out.println("All threads terminated");

	}

}
