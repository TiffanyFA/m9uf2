import java.awt.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;

public class NauEspaial extends javax.swing.JFrame {

	public NauEspaial() {
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(255, 255, 255));
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300,
				Short.MAX_VALUE));
		pack();
	}

	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(NauEspaial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		NauEspaial f = new NauEspaial();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Naus Espaials");
		f.setContentPane(new PanelNau());
		f.setSize(480, 560);
		f.setVisible(true);
	}
}

class PanelNau extends JPanel implements Runnable, KeyListener {
	private int numNaus = 10;
	Nau[] nau;
	Nau nauPropia;
	String colorNau;
	Dispar dispar;
	String colorDispar;
	boolean continuar = true;
	Vector<Dispar> dispars = new Vector<Dispar>();
	
	public PanelNau() {
		nau = new Nau[numNaus];	
		for (int i = 0; i < nau.length; i++) {
			Random rand = new Random();
			int velocitat = (rand.nextInt(3) + 5) * 10;
			int posX = rand.nextInt(100) + 30;
			int posY = rand.nextInt(100) + 30;
			int dX = rand.nextInt(3) + 1;
			int dY = rand.nextInt(3) + 1;
			String nomNau = Integer.toString(i);
			colorNau = "nauVermella";
			nau[i] = new Nau(nomNau, colorNau, posX, posY, dX, dY, velocitat);
		}

		// Creo la nau propia
		colorNau = "nauBlava";
		nauPropia = new Nau("NauNostra", colorNau, 200, 400, 0, 0, 200);		

		// Creo fil per anar pintant cada 0,1 segons el joc per pantalla
		Thread n = new Thread(this);
		/*se li dona prioritat ja que és el fil que fa totes les comprovacions de interaccions entre
		 * naus i dispars i repinta		
		*/
		n.setPriority(7);
		n.start();

		// Creo listeners per a que el fil principal del programa gestioni
		// esdeveniments del teclat
		addKeyListener(this);
		setFocusable(true);

	}
	
	public void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}
	
	//mètode que fa explotar el dispar si arriba al final de la pantalla
	public void explota(){
		//En cas que el dispar es sorti del marge 
		for (int i = 0; i < dispars.size(); i++) {
			Dispar dispar = dispars.get(i);
			if (dispar.eliminar()) {
			dispars.remove(dispar);
			dispar.noSeguir();
			}
		}		
	}
	
	//mètode que comprova si queden enemics
	public void comprovarEnemics() {
		int count = 0;
		int tamany = nau.length;
		for (int i = 0; i < tamany; i++) {
			if (nau[i] == null) {
				count++;
			}
		}
		if (count == tamany) {
			System.out.println("HAS GUANYAT!!");
			setContinuar(false);
		}
	}
	
	//mètode que comprova si les naus xoquen amb nauPropia
	public void comprovarXoc() {
		for (int j = 0; j < nau.length; j++) {
			if (nau[j]!=null) {
				double calcul;						
				calcul = Math.sqrt(Math.pow(nau[j].getPosicioX() + 35 - nauPropia.getPosicioX() + 35, 2) +
							Math.pow(nau[j].getPosicioY() + 45 - nauPropia.getPosicioY() +45  , 2));
				if (calcul < 30){
					System.out.println("HAS PERDUT");
					setContinuar(false);					
				}
			}
		}
	}

	public void run() {
		while (continuar) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			} // espero 0,1 segons
			//fa explotar el dispar si arriba al final de la pantalla
			explota();
			//destrueix les naus que són disparades
			comprovaDispars();
			//comprova si queden naus enemigues
			comprovarEnemics();
			//comprovar si alguna nau xoca amb mi
			comprovarXoc();			
			// System.out.println("Repintant");
			repaint();
		}
		//tanca el joc
		System.exit(0);	
	}

	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < nau.length; ++i){
			if (nau[i]!=null) { 
				nau[i].pinta(g);
				nauPropia.pinta(g);
			}			
		}
		for (int i = 0; i < dispars.size(); i++) {
			Dispar index = dispars.get(i);
			index.pinta(g);
		}
	}

	// Metodes necesaris per gestionar esdeveniments del teclat
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" +
		// e.getKeyChar());
		if (e.getKeyCode() == 37) {
			nauPropia.esquerra();
		} 
		if (e.getKeyCode() == 39) {
			nauPropia.dreta();
		} 
		if (e.getKeyCode() == 32) {
			colorDispar = "disparBlau";
			dispar = new Dispar(colorDispar, nauPropia.getPosicioX()+35, nauPropia.getPosicioY(), -3, 100);
			dispars.add(dispar);			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	public void comprovaDispars(){
		//En cas que el dispar impacti amb una nau
		for (int i = 0; i < dispars.size(); i++) {
			Dispar index = dispars.get(i);
			for (int j = 0; j < nau.length; j++) {
				if (nau[j]!=null) {
					double calcul;						
					calcul = Math.sqrt(Math.pow(nau[j].getPosicioX() - index.getPosicioX(), 2) +
					Math.pow(nau[j].getPosicioY() - index.getPosicioY(), 2));
					if (calcul < 10){
						System.out.println("XOCA!!");
						nau[j].noSeguir();
						nau[j] = null;					
						dispars.remove(index);
						i--;
					}
				}
			}
		}	
	}
}

class Nau extends Thread {
	private String nomNau;
	private int x, y;
	private int dsx, dsy, v;
	private int tx = 10;
	private int ty = 10;
	private boolean seguir = true;
	ThreadGroup grupNaus = new ThreadGroup ("naus");
	private String img = "/images/nauBlava.jpg";
	private Image image;

	public Nau(String nomNau, String colorNau, int x, int y, int dsx, int dsy, int v) {
		this.nomNau = nomNau;
		this.x = x;
		this.y = y;
		this.dsx = dsx;
		this.dsy = dsy;
		this.v = v;
		image = new ImageIcon(Nau.class.getResource(colorNau + ".png")).getImage();
		Thread t = new Thread(grupNaus, this, "nau");
		t.start();
	}

	public void noSeguir() {
		seguir = false;	
	}

	public int velocitat() {
		return v;
	}

	public synchronized void moure() {
		x = x + dsx;
		y = y + dsy;
		// si arriva als marges ...
		if (x >= 450 - tx || x <= tx)
			dsx = -dsx;
		if (y >= 500 - ty || y <= ty)
			dsy = -dsy;
	}

	public synchronized void pinta(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.image, x, y, null);
	}
	
	public int getPosicioX(){
		return this.x; 
	}

	public int getPosicioY(){
		return this.y; 
	}
	
	public void run() {
		while (seguir) {
			// System.out.println("Movent nau numero " + this.nomNau);
			try {
				Thread.sleep(this.v);
			} catch (Exception e) {
			}
			moure();
		}
	}

	public void esquerra() {
		this.dsx = -20;
	}

	public void dreta() {
		this.dsx = 20;
	}
}

class Dispar extends Thread {
	private int x, y;
	private int dsy, v;	
	private int ty = 10;
	private boolean seguir;
	private String img = "disparVermell.jpg";
	private Image image;
	private String colorDispar = "disparVermell";
	ThreadGroup grupDispars = new ThreadGroup ("dispars");
	
	public Dispar (String colorDispar, int x, int y, int dsy, int v) {
		this.x = x;
		this.y = y;
		this.dsy = dsy;
		this.v = v;
		seguir = true;
		image = new ImageIcon(Nau.class.getResource(colorDispar + ".png")).getImage();
		Thread t = new Thread(grupDispars, this, "blaus");
		t.start();
	}

	public int getPosicioX() {
		// TODO Auto-generated method stub
		return x;
	}
	
	public int getPosicioY() {
		// TODO Auto-generated method stub
		return y;
	}

	public int velocitat() {
		return v;
	}

	public synchronized void moure() {		
		y = y + dsy;
	}
	
	public synchronized void pinta(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.image, x, y, null);
	}
	
	public boolean eliminar() {
		boolean fora = false;
		if (y >= 500 - ty || y <= ty) {
			fora = true;
		}
		return fora;
	}
	
	public void noSeguir(){
		seguir = false;
	}

	public void run() {
		while (seguir) {
			// System.out.println("Movent nau numero " + this.nomNau);
			try {
				Thread.sleep(this.v);
			} catch (Exception e) {
			}
			moure();
		}
	}
	
}