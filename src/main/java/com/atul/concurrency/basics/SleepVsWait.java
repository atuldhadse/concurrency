package com.atul.concurrency.basics;

class SharedResource {

	public synchronized void waitingCall() {

		System.out.println(Thread.currentThread().getName() + " entered the waiting method...");

		try {
			System.out.println(Thread.currentThread().getName() + " asking to wait...");
			wait();
			System.out.println(Thread.currentThread().getName() + " done with waiting...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " taking an exit from the waiting method");

	}

	public synchronized void notifyWaitingMethod() {
		System.out.println("notifying the waiting method...");
		notify();
	}

}

public class SleepVsWait {

	public static void main(String[] args) {

		SharedResource shared = new SharedResource();
		Thread t1 = new Thread(shared::waitingCall, "thread 1");
		Thread t2 = new Thread(shared::waitingCall, "thread 2");

		Thread notifier = new Thread(() -> {
			try {
				Thread.sleep(1000); // this will give t1 and t2 to take wait() first
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			shared.notifyWaitingMethod();
			try {
				Thread.sleep(1000); // this will give t1 and t2 to take wait() first
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			shared.notifyWaitingMethod();
		}, "notifier");

		t1.start();
		t2.start();
		notifier.start();

	}

}
