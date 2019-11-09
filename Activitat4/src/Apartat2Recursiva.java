import java.io.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
 
public class A2Bombolla extends RecursiveTask<int[]>
{
	private int[] arr;
	private int inici, fi; 
	
	public A2Bombolla (int[] arr, int inici, int fi) {
		this.arr = arr;
		this.inici = inici;
		this.fi = fi;
	}
	
    public static void main(String arg[]) throws IOException
    {
        /*creacion del objeto para leer por teclado*/
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        /*ingreso del tamaño de arreglos*/
        System.out.print("\n Ingrese Numero de Datos a Ingresar : ");
        int tam = Integer.parseInt(in.readLine());
        /*creacion del arreglo*/
        int arr[] = new int[tam];
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println();
        /*lectura del arreglo*/
        int j = 0;
        for (int i = 0 ; i < arr.length;i++)
        {
            j+=1;
            System.out.print("Elemento " + j + " : ");
            arr[i] = (int)(Math.random()*100+1);
        }
        pool.invoke(new A2Bombolla(arr, 0, 1000));
        
        
        
    }
 
    static void burbuja(int arreglo[])
    {
        for(int i = 0; i < arreglo.length - 1; i++)
        {
            for(int j = 0; j < arreglo.length - 1; j++)
            {
                if (arreglo[j] > arreglo[j + 1])
                {
                	for(int h = 0;h < arreglo.length; h++)
                    {
                        System.out.print(arreglo[h]+"\n");
                    }
                    int tmp = arreglo[j];
                    arreglo[j] = arreglo[j + 1];
                    arreglo[j+1] = tmp;
                    for(int k = 0;k < arreglo.length; k++)
                    {
                        System.out.print(arreglo[k]+"\n");
                    }
                }
            }
        }
        for(int i = 0;i < arreglo.length; i++)
        {
            System.out.print(arreglo[i]+"\n");
        }
    }

	@Override
	protected int[] compute() {
		A2Bombolla tasca = new A2Bombolla(arr, 0, 1000);
        A2Bombolla tasca1 = new A2Bombolla(arr, 0, 250);
        A2Bombolla tasca2 = new A2Bombolla(arr, 251, 500);
        A2Bombolla tasca3 = new A2Bombolla(arr, 501, 750);
        A2Bombolla tasca4 = new A2Bombolla(arr, 751, 1000);
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        tasca1.fork();
        tasca2.fork();
        tasca3.fork();
        tasca4.fork();
        tasca1.join();
        tasca2.join();
        tasca3.join();
        tasca4.join();
        
        
        for (int i = 0; i < arr.length; i++) {
			int min = tasca1[i1];
			if (tasca2[i2] < min) {
				min = tasca2[i2];
			}else if (tasca3[i3] < min) {
				min = tasca3[i3];
			}else {
				min = tasca4[i4];
			}
			tasca [i] = min;
			
			if (min == tasca1[i1]) {
				if (i1 < tasca1.length) {
					i1++;
				}
			}else if (min == tasca2[i2]) {
				if (i2 < tasca2.length) {
					i2++;
				}
			}else if (min == tasca3[i3]) {
				if (i3 < tasca3.length) {
					i3++;
				}
			}else {
				if (i4 < tasca4.length) {
					i4++;
				}
			}
		}
		
		return null;
	}
}
