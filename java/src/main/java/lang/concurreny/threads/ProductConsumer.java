package lang.concurreny.threads;

public class ProductConsumer {

	public static void main(String[] args) {
		SharedBuffer sharedBuffer = new SharedBuffer();
		
		Producer producer = new Producer(sharedBuffer);
		Consumer consumer = new Consumer(sharedBuffer);
		
		Thread t1 = new Thread(producer);
		Thread t2 = new Thread(consumer);
		
		t1.start();
		t2.start();
	}

}

class SharedBuffer {
	private boolean writable;
	private char ch;
	
	SharedBuffer() {
		writable = true;
	}
	
	synchronized void setChar(char newChar) {
		while(!writable) {
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		}
		ch = newChar;
		writable = false;
		notify();
	}
	
	synchronized char getChar() {
		while(writable) {
			try {
				wait();
			} catch (InterruptedException e) {
			}	
		}
		
		writable = true;
		notify();
		return ch;
	}
	
}

class Producer implements Runnable{
	private final SharedBuffer sharedBuffer;
	
	Producer(SharedBuffer sharedBuffer) {
		this.sharedBuffer = sharedBuffer;
	}

	@Override
	public void run() {
		for(char ch = 'A'; ch <= 'Z'; ch++) {
			synchronized (sharedBuffer) {
				sharedBuffer.setChar(ch);
				System.out.println("Produced " + ch);
			}		
		}
		
	}	
}

class Consumer implements Runnable {
	private final SharedBuffer sharedBuffer;
	
	public Consumer(SharedBuffer sharedBuffer) {
		super();
		this.sharedBuffer = sharedBuffer;
	}

	@Override
	public void run() {
		char ch;
		
		do {
			ch = sharedBuffer.getChar();
			System.out.println("Consumed " + ch);
		} while(ch != 'Z');
	}
	
}
