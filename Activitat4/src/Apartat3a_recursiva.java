
public class Apartat3a_recursiva {
	//Constructor
    public Apartat3a_recursiva () {
       //Nada que declarar
    }

    //Método 2 para calcular la potencia
    public static int potenciaConRecursion (int m, int n) {
    	double calcul = java.lang.Math.cos(54879854);
        if (n == 0) { 
        	return 1;
        } else  {
        	return m * potenciaConRecursion (m, n-1); 
        }
    }
    
    public static void main(String[] args){
		long temps =System.currentTimeMillis();
		
		long resultat = potenciaConRecursion(54870,30);
		System.out.println(resultat);
		
		System.out.println(System.currentTimeMillis() - temps);
        
    }
}
