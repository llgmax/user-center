package com.llg.usercenter.exception;


/**
 * 线程
 *
 * @author llg
 */
public class MyThread extends Thread{
    public void run() {
		 System.out.println("MyThread.run()");
		 }

	public static void main(String[] args) {

		MyThread myThread1 = new MyThread();
		myThread1.start();
	}

}
