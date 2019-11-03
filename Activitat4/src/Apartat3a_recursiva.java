
public class Apartat3a_recursiva {
	//Constructor
    public Apartat3a_recursiva () {
       //Nada que declarar
    }

    //Método 2 para calcular la potencia
    public int potenciaConRecursion (int m, int n) {
        if (n == 0) { 
        	return 1;
        } else  {
        	return m * potenciaConRecursion (m, n-1); 
        }
    } 
}
