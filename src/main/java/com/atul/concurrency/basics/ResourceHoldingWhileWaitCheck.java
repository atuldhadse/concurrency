package com.atul.concurrency.basics;

import java.util.Arrays;
import java.util.List;

class SharedResource2 {

	public synchronized void printWait() {

		List<Integer> ls = Arrays.asList(1, 2, 3, 4, 5, 6);

		for (int ele : ls) {
			if (ele == 4) {
				try {
					System.out.println(Thread.currentThread().getName() + " is going into waiting state...");
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " " + ele);
		}

	}

	public synchronized void notifyWaitingMethod() {
		System.out.println("notifying waiting method...");
		notify();
	}

}

public class ResourceHoldingWhileWaitCheck {

	public static void main(String[] args) {

		SharedResource2 shared = new SharedResource2();
		Thread t1 = new Thread(shared::printWait, "thread 1");
		Thread t2 = new Thread(shared::printWait, "thread 2");

		Thread notifier = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			shared.notifyWaitingMethod();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			shared.notifyWaitingMethod();
		});

		t1.start();
		t2.start();
		notifier.start();

	}

}
