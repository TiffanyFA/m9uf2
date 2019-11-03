import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

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
        } else  {
        	long exp1 = 1;
        	Apartat3aRecursiveTask sub1 = new Apartat3aRecursiveTask(this.base, exp1);
        	//sub1.fork();
        	Apartat3aRecursiveTask sub2 = new Apartat3aRecursiveTask(this.base, this.exp - (long)1);
        	sub2.fork();
        	
        	return this.base * sub2.join();
        }
	}

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		long temps = System.currentTimeMillis() / 1000;
		long base = 125006000;
		long exp = 9;

		System.out.println("Calculat:  " + pool.invoke(new Apartat3aRecursiveTask(base, exp)));

		System.out.println(System.currentTimeMillis() / 1000 - temps);
	}
}

