import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Apartat3aRecursiveTask extends RecursiveTask<Long> {
	private static final long serialVersionUID = 1L;
	long base;
	long exp;

	public Apartat3aRecursiveTask(long base, long exp) {
		this.base = base;
		this.exp = exp;
	}

	@Override
	protected Long compute() {
		double calcul = java.lang.Math.cos(54879854);
		if (this.exp == 0) { 
        	return (long) 1;
        }
		if (this.exp == 1) { 
        	return base;
        }
		
		else  {
        	long exp1 = 1;
        	Apartat3aRecursiveTask sub1 = new Apartat3aRecursiveTask(this.base, exp1);
        	sub1.fork();
        	
        	Apartat3aRecursiveTask sub2 = new Apartat3aRecursiveTask(this.base, (this.exp - (long)1));
        	sub2.fork();
        	//System.out.println("fede");
        	long u = sub1.join();
        	long d = sub2.join();
        	return u * d ;
        }
	}

	public static void main(String[] args) throws InterruptedException {
		ForkJoinPool pool = new ForkJoinPool();
		long temps = System.currentTimeMillis();
		System.out.println(temps);
		
		long base = 400;
		long exp = 15;
		
		//long Future l = pool.invoke(new Apartat3aRecursiveTask(base, exp));
		
		System.out.println("Calculat:  " + pool.invoke(new Apartat3aRecursiveTask(base, exp)));
		pool.shutdown();
		//if (pool.awaitTermination(4, TimeUnit.SECONDS)) {}
		
		
		long temps2 = System.currentTimeMillis();
		
		System.out.println(temps2);
	}
}

