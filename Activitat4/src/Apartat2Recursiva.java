public class Apartat2Recursiva {
	//mètode bombolla
    static int[] ordenaBombolla(int [] arr) {
    	int aux;    	    
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i + 1] < arr[i]) {
				aux = arr[i];
				arr[i] = arr[i+1];
				arr[i+1] = aux;
			}
		}
		return ordenaBombolla(arr);	
		
    }
    
    //mostrar
    static void mostrar(int [] arr) {
        System.out.println("|-----------------------|");
        for (int i = 0; i < arr.length; i++) { 
        	System.out.print(" Element " + (i + 1) + " -----> " + arr[i] + "\n");
        }
        System.out.println("|-----------------------|");
    }
	
	public static void main(String[] args) {		
        //variables
        int num = 20;
        int [] arr = new int[num];        

        //omplir array
        for (int i = 0; i < arr.length; i++) {
        	arr[i] = (int) (Math.random()*100);
        }
        System.out.println("      -SENSE ORDENAR-");
        mostrar(arr);
        System.out.println();
        //ordenar
        System.out.println("       -ORDENAT-   ");   
        Apartat2Recursiva.ordenaBombolla(arr);
        mostrar(arr);
	}
}   


