package lang.concurreny.threads;

public class BasicThreadAndRunnables {

	public static void main(String[] args) {
		System.out.println("available processes:" + Runtime.getRuntime().availableProcessors());
		
		Runnable task1 = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("hello from anonymous runnable.");
			}
		};
		
		Runnable task2 = () -> System.out.println("hello from lambda");
		
		//A thread can only implement run itself.
		Thread myThread = new Thread() {
			@Override
			public void run() {
				System.out.println("hello from thread run.");
			}
		};
		
		Thread t1 = new Thread(task1);
		t1.start();
		
		Thread t2 = new Thread(task2);
		t2.start();
		
		myThread.start();
		
		
	}

}
