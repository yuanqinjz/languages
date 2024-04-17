package lang.concurreny.threads;

public class ThreadInterrupt {

	public static void main(String[] args) {
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				String name = Thread.currentThread().getName();
				int count = 0;
				while(!Thread.currentThread().isInterrupted()) {
					System.out.println(name + ":" + count++);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						System.out.println(name + " is interrupted, let's return");
						return;
					}
				}
			}
		};
		
		Thread threadA = new Thread(task);
		Thread threadB = new Thread(task);
		threadA.start();
		threadB.start();
		
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException ie){
			//Never reach here as we don't call interrupt() on main thread.
	    }
		threadA.interrupt();
		threadB.interrupt();
		

	}

}
