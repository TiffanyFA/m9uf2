
public class CalculaFibonacci {
	public static long calculaFibonacci(long numero) {
        double calcul = java.lang.Math.cos(54879854);
        if (numero==0) { return 0; }
        else if (numero==1) { return 1; }
        else {
            return (calculaFibonacci(numero-2) + calculaFibonacci(numero-1));
        }
    }
	
	public static void main(String[] args){
		long temps =System.currentTimeMillis()/1000;
		
		long resultat = calculaFibonacci(45);
		System.out.println(resultat);
		
		System.out.println(System.currentTimeMillis()/1000 - temps);
        
    }

}
