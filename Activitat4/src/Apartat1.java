/*
 * •	Crear un client cada 3 segons. En total hi haurà 50 clients.
•	Cada client porta un nombre aleatori (d’entre 1 i 30) articles i cada article en qüestió triga un temps a passar per caixa (d’entre 2 i 8 segons) ja que hi ja objectes que pesen poc i d’altres que pesen molt. Podeu utilitzar un vector, una llista o un array per representar aquestes dades (feu-ho senzill, dediqueu-hi temps per veure quina estructura és més senzilla!).
•	Proveu diferent nombre de fils (1, 2, 5 i 10) per passar els articles per caixa.
•	Proveu, amb 10 caixes, i diferent nombre de clients (100 i 500)
El resultat ha de ser similar al següent:

Creat el client 1 amb 5 articles (2, 8, 5, 3, 4)
Client 1 passa per caixa...
Client 1 article 1/5 (2 segons)...
Creat el client 2 amb 3 articles (5,7)
Client 2 passa per caixa...
Client 2 article 1/2 (5 segons)...
Client 1 article 2/5 (8 segons)...
Client 1 article 3/5 (5 segons)...
Client 2 article 2/2 (7 segons)...FINALITZAT
Client 1 article 4/ (3 segons)...
Client 1 article 5/5 (7 segons)...FINALITZAT

 */
import java.util.*;
import java.util.concurrent.*;

public class Apartat1 {
	// Fil Callable
    class Client implements  Callable<int[]>, Runnable {
    	//atributs
    	private int num;
    	
    	
    	//constructor
    	public Client (int num) {
    		this.num = num;
    		
    	}
        @Override
        public int[] call() throws InterruptedException {
        	int [] llistaArticles =  new int [num]; 
        	Thread.sleep(3000);        	       	
    		for (int i = 0; i < llistaArticles.length; i++) {        			
    			llistaArticles[i] = (int) (Math.random()*10);  
    			System.out.print("Creat el client " + i + " amb " + num + " articles(");    			
    		}
    		for (int j = 0; j < llistaArticles.length; j++) {
			System.out.print(llistaArticles[j] + ", ");	
			}
    		return llistaArticles;
        }
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				
			}
			
		}
    }

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//creacio d'un executor amb 5 fils
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		//creacio d'una llista on desar els objectes Suma
		List<Client> cistelles= new ArrayList<Client>();
		//es sumen números aleatoris 25 vegades i es desen en la llista
		for (int i = 0; i < 50; i++) {
			Client articles = new Client((int)(Math.random()*30));
    		cistelles.add(articles);			
		}
		//Es crea una llista que guardarà els resultats
		int[] llistaCistelles;
		//Omple la llista amb els resultats ja resolts (quan estan tots resolts)
		llistaCistelles = executor.invokeAny(cistelles);
		
		//Tancar tots els fils
		executor.shutdown();
		
//		//imprimeix resultat
//		for (int i = 0; i < llistaResultats.size(); i++) {
//			Future<Integer> resultat = llistaResultats.get(i);
//			try {
//				System.out.println("Resultat tasca "+i+ " és:" +
//				resultat.get());
//			} 
//			catch (InterruptedException | ExecutionException e)	{
//			}
//		}
	}
}
