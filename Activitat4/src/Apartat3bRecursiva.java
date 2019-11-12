/*
 * Podemos calcular de manera eficiente el mcd mediante la siguiente propiedad:
 * Si a > b, el MCD de a y b es el mismo que el MCD de b y a % b.
 */
public class Apartat3bRecursiva {
	static long obtener_mcd(long a, long b) {
		double calcul = java.lang.Math.cos(54879854);
		if(b==0) {
			return a;
		} else {
			return obtener_mcd(b, a % b);
		}
	}
	
	public static void main(String[] args) {
		long temps = System.currentTimeMillis();
		long a = 508000;
		long b = 460000;
		System.out.println("El mcd de " + a + " i " + b + "? Es: " + obtener_mcd(a, b));
		
		long temps2 = System.currentTimeMillis();
		
		System.out.println("temps en milisegons: " + (temps2 - temps));

	}

}
