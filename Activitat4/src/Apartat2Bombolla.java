
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
 
public class Apartat2Bombolla extends RecursiveTask<int[]> {
	private int[] data;
	private int inici, fi; 
	
	public Apartat2Bombolla (int[] data, int inici, int fi) {
		this.data = data;
		this.inici = inici;
		this.fi = fi;
	}	
	
	//Utility function to swap values at two indices in the array
	public static void swap(int arr[], int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}	
	
	//Recursive function to perform bubble sort on subarray arr[i..n]
	public void bubbleSort(int arr[], int n) {
		for (int i = 0; i < n - 1; i++) {
			if (arr[i] > arr[i + 1]) {
				swap(arr, i, i + 1);
			}
		}
		if (n - 1 > 1) {
			bubbleSort(arr, n - 1);
		}
	}	
	
	@Override
	protected int[] compute() {		
		int quart1 = data.length / 4;
		int quart2 = (data.length / 4) * 2;
		int quart3 = (data.length / 4) * 3;
		int[] data1;
		int[] data2;
		int[] data3;
		int[] data4;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		int min;
	
		Apartat2Bombolla subtaskA = new Apartat2Bombolla(data, inici, quart1);
		subtaskA.fork();
		try {
			bubbleSort(subtaskA.get(), subtaskA.get().length);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Apartat2Bombolla subtaskB = new Apartat2Bombolla(data, quart1 + 1, quart2);
		subtaskB.fork();
		try {
			bubbleSort(subtaskB.get(), subtaskB.get().length);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Apartat2Bombolla subtaskC = new Apartat2Bombolla(data, quart2 + 1, quart3);
		subtaskC.fork();
		try {
			bubbleSort(subtaskC.get(), subtaskC.get().length);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Apartat2Bombolla subtaskD = new Apartat2Bombolla(data, quart3 + 1, fi);
		subtaskD.fork();
		try {
			bubbleSort(subtaskD.get(), subtaskD.get().length);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data1 = subtaskA.join();
		data2 = subtaskB.join();
		data3 = subtaskC.join();
		data4 = subtaskD.join();

		for (int i = 0; i < data.length - 1; i++) {
			min = data1[i1];
			if (data2[i2] < min) {
				min = data2[i2];
			} else if (data3[i3] < min) {
				min = data3[i3];
			} else {
				min = data4[i4];					
			}
			//omplir data
			data[i] = min;
			
			//control index
			if (min == data1[i1]) {
				i1++;
			} else if (min == data2[i2]) {
				i2++;
			} else if (min == data3[i3]) {
				i3++;
			} else {
				i4++;
			}
			
		}
		System.out.println("ORDENADA");
		System.out.println(Arrays.toString(data));
		return data;       
	}
	
	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		int[] data = new int[100];
		
		// omplir array
		for (int i = 0; i < data.length; i++) {
			data[i] = (int) (Math.random() * 100);
		}
		
		//mostrar
		System.out.println("DESORDENADA");
		System.out.println(Arrays.toString(data));
		
		//dividir tasca		
		Apartat2Bombolla task = new Apartat2Bombolla(data, 0, data.length);
		data = pool.invoke(task);
				
		System.out.println("ORDENADA");
		System.out.println(Arrays.toString(data));
		
	}	
}