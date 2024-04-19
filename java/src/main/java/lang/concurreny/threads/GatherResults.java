package lang.concurreny.threads;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class GatherResults {

	public static void main(String[] args) {
		GatherResults gatherResults = new GatherResults();
		
		gatherResults.doWork();

	}
	
	public void doWork() {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		
		List<Double> bases = Arrays.asList(1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d);
		List<WorkTask> tasks = bases.stream().map(
				(Double base) -> new WorkTask(base)).toList();
		//Create task and submit them.
		try {
			List<Future<Double>> results = executorService.invokeAll(tasks, 1, TimeUnit.HOURS);
			double sum = 0.0;
			for(Future<Double> future : results) {
				Double singleResult = future.get();
				System.out.println("Received:" + singleResult);
				sum += singleResult;
			}
			System.out.println("Total is:" + sum);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
		
	}
	
	class WorkTask implements Callable<Double> {
		private double base;
		
		WorkTask(double base){
			this.base = base;
		}
		
		@Override
		public Double call() throws Exception {
			Random random = new Random();
			int sleepTime = (int)random.nextInt(10) * 1000;
			
			//Do the heavy duty work...
			Thread.currentThread().sleep(sleepTime);
			Boolean shouldThrow = random.nextBoolean();
			if(shouldThrow) {
				System.out.println("Thread will throw exception now..");
				throw new RuntimeException("Throw on purpose");
			}
			
			return base + random.nextDouble(5);
		}
		
	}

}
