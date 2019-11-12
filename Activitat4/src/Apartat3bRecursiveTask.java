
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;


public class Apartat3bRecursiveTask extends RecursiveTask<Long> {
		
	private long a;
	private long b;

	public Apartat3bRecursiveTask(long a, long b) {
		this.a = a;
		this.b = b;
	}

	@Override
	protected Long compute() {
		double calcul = java.lang.Math.cos(54879854);
	
		if(b==0) {
	           return a;
		} else  {
			Apartat3bRecursiveTask tasca = new Apartat3bRecursiveTask(b, a % b);
			tasca.fork();
	    	
	    	return tasca.join();
        }
	}

	public static void main(String[] args) throws InterruptedException {
		ForkJoinPool pool = new ForkJoinPool();
		long temps = System.currentTimeMillis();
		//System.out.println(temps);
		
		long a = 508000;
		long b = 460000;
		
		System.out.println("El mcd de " + a + " i " + b + "? Es: " + 
		pool.invoke(new Apartat3bRecursiveTask(a, b)));
		pool.shutdown();
				
		long temps2 = System.currentTimeMillis();
		
		System.out.println("temps en milisegons: " + (temps2 - temps));
	}
}
