import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class Apartat3aRecursiveTask extends RecursiveTask<Long> {
	long base;
	long exp;

	public Apartat3aRecursiveTask(long base, long exp) {
		this.base = base;
		this.exp = exp;
	}

	@Override
	protected Long compute() {
		//double calcul = java.lang.Math.cos(54879854);
		if (exp == 0) { 
        	return (long) 1;
        } else  {
        	Apartat3aRecursiveTask sub1 = new Apartat3aRecursiveTask(base, 1);
        	sub1.fork();
        	Apartat3aRecursiveTask sub2 = new Apartat3aRecursiveTask(base, exp - 1);
        	sub2.fork();
        	return sub1.join() * sub2.compute();
        }
	}

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		long temps = System.currentTimeMillis() / 1000;

		System.out.println("Calculat:  " + pool.invoke(new Apartat3aRecursiveTask(10, 6)));

		System.out.println(System.currentTimeMillis() / 1000 - temps);
	}
}
