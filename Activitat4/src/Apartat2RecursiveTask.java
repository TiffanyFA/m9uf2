import java.util.concurrent.ForkJoinPool; 
import java.util.concurrent.RecursiveTask; 

public class Apartat2RecursiveTask {
	static class Ordena extends RecursiveTask<int[]> {
		private int[] data;
		private int inici;
		private int fi;

		Ordena(int[] data, int inici, int fi) {
			this.data = data;
			this.inici = inici;
			this.fi = fi;
		}

		public static int[] ordenaBombolla(int[] arr) {
			int aux;
			for (int i = 0; i < arr.length - 1; i++) {
				if (arr[i + 1] < arr[i]) {
					aux = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = aux;
				}
			}
			return (arr);

		}

		@Override
		protected int[] compute() {
			int mig = (inici + fi) / 2;
			int aux;
			int[] data1;
			int[] data2;

			Ordena subtaskA = new Ordena(data, inici, mig);
			Ordena subtaskB = new Ordena(data, mig, fi);

			subtaskA.fork();
			subtaskB.fork();

			data1 = ordenaBombolla(subtaskA.join());
			data2 = ordenaBombolla(subtaskB.join());

			for (int i = 0; i < data.length - 1; i++) {
				if (data1[i + 1] < data2[i]) {
					aux = data2[i];
					data[i] = data1[i + 1];
					data[i + 1] = aux;
				}
			}
			return data;
		}
	}

	public static void main(String[] args) {
		ForkJoinPool fjp = new ForkJoinPool();
		int[] data = new int[100];

		// omplir array
		for (int i = 0; i < data.length; i++) {
			data[i] = (int) (Math.random() * 100);
		}
		Ordena task = new Ordena(data, 0, data.length);
		data = fjp.invoke(task);

		for (int i = 0; i < data.length; i++) {
			
		}

	}
} 
