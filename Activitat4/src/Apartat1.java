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
import java.util.concurrent.*;

public class Apartat1 {
	// classe client
    public static class Client {
    	//atributs
    	private int num;
    	private int identificador;    	
    	
    	//constructor
    	public Client (int num, int id) {
    		this.num = num;
    		identificador = id;    		
    	}
    	
        public int getIdentificador() {
			return identificador;
		}

		public int getNum() {
			return num;
		}	

		public int[] getCistella() {
			return omplirCistella();
		}
		
		public int[] omplirCistella() {
			int [] llistaArticles =  new int [num];        	       	       	
    		for (int i = 0; i < llistaArticles.length; i++) {        			
    			llistaArticles[i] = (int) (Math.random()*10);      			
    		}
    		
    		return llistaArticles;
		}
		
		public void mostrarCistella(int [] llistaArticles) {
			for (int j = 0; j < llistaArticles.length; j++) {
				System.out.print(llistaArticles[j] + ", ");	
				}
		}
    }
    
    //classe caixa
    public static class Caixa implements Runnable{
    	//atributs
    	private Client client;    	
    	
    	//constructor
		public Caixa(Client client ) {
			super();
			this.client = client;			
		}

		@Override
		public void run() {	
			try {	        	       	       	
				long tempsEntreArticles = (long) Math.floor(Math.random()*(1000-8000+1000)+8000);
				long tempsInicial = System.currentTimeMillis();
				long tempsFinal;
				System.out.print("Creat el client " + this.client.getIdentificador() + " amb " + this.client.getCistella().length + " articles("); 
				this.client.mostrarCistella(this.client.getCistella());
				System.out.print(")");
				System.out.println();
				System.out.println("Client " + this.client.getIdentificador() + " passa per caixa...");
				for (int i = 1; i < this.client.getCistella().length + 1; i++) {
					Thread.sleep(tempsEntreArticles);
					System.out.println("Client " + this.client.getIdentificador() + " article " + i + "/" + this.client.getCistella().length +
							" (" + ((System.currentTimeMillis() - tempsInicial) / 1000) + " segons)...");
				}	
				System.out.println("Client " + this.client.getIdentificador() + " FINALITZAT");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}    	
    }

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//creacio d'un executor amb 10 fils
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);		
		//es creen 50 clients
		for (int i = 1; i < 51; i++) {			
			Client client = new Client((int)(Math.floor(Math.random()*(1-30+1)+30)), i);
			Runnable run = new Caixa (client);
			executor.execute(run);
			Thread.sleep(3000); 			
		}		
		//Tancar tots els fils
		executor.shutdown();
	}
}
