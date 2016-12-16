/**
 * 
 */
package com.hp.rpc.test.bean;

/**
 * @author ping.huang
 * 2016年12月13日
 */
public class Test {

	
	public static void main(String[] args) {
		Thread t1 = new Thread(new T("线程1"));
		Thread t2 = new Thread(new T("线程2"));
		t1.setDaemon(false);
		t2.setDaemon(true);
		t1.start();
		t2.start();
		System.out.println("00000");
	}
	
	public static class T implements Runnable {

		private String name;
		
		public T(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println(name + "开始");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(name + "结束");
		}
		
	}
}
