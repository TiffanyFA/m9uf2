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
		nauPropia = new Nau("NauNostra", colorNau, 200, 400, 10, 0, 100);
		
		//Crea els dispars
		

		// Creo fil per anar pintant cada 0,1 segons el joc per pantalla
		Thread n = new Thread(this);
		n.start();

		// Creo listeners per a que el fil principal del programa gestioni
		// esdeveniments del teclat
		addKeyListener(this);
		setFocusable(true);

	}

	public void run() {
		System.out.println("Inici fil repintar");
		while (true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			} // espero 0,1 segons
			// System.out.println("Repintant");
			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < nau.length; ++i)
			nau[i].pinta(g);
		nauPropia.pinta(g);
		
		for (int i = 0; i < dispars.size(); i++) {
			Dispar index = dispars.get(i);
			index.pinta(g);
			//dispars.remove(index);
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
		} // System.out.println("a l'esquerra"); }
		if (e.getKeyCode() == 39) {
			nauPropia.dreta();
		} // System.out.println("a la dreta"); }
		if (e.getKeyCode() == 32) {
			colorDispar = "disparBlau";
			dispar = new Dispar(colorDispar, nauPropia.getPosicioX(), nauPropia.getPosicioY(), 3, 100);
			dispars.add(dispar);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}

class Nau extends Thread {
	private String nomNau;
	private int x, y;
	private int dsx, dsy, v;
	private int tx = 10;
	private int ty = 10;

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
		Thread t = new Thread(this);
		t.start();
	}

	public int velocitat() {
		return v;
	}

	public void moure() {
		x = x + dsx;
		y = y + dsy;
		// si arriva als marges ...
		if (x >= 450 - tx || x <= tx)
			dsx = -dsx;
		if (y >= 500 - ty || y <= ty)
			dsy = -dsy;
	}

	public void pinta(Graphics g) {
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
		while (true) {
			// System.out.println("Movent nau numero " + this.nomNau);
			try {
				Thread.sleep(this.v);
			} catch (Exception e) {
			}
			moure();
		}
	}

	public void esquerra() {
		this.dsx = -2;
	}

	public void dreta() {
		this.dsx = 2;
	}
}

class Dispar extends Thread {
	private int x, y;
	private int dsx, dsy, v;
	private int tx = 10;
	private int ty = 10;

	private String img = "disparVermell.jpg";
	private Image image;
	private String colorDispar = "disparVermell";	
	
	public Dispar (String colorDispar, int x, int y, int dsx, int v) {
		this.x = x;
		this.y = y;
		this.dsx = dsx;
		this.v = v;
		image = new ImageIcon(Nau.class.getResource(colorDispar + ".png")).getImage();
		Thread t = new Thread(this);
		t.start();
	}

	public int velocitat() {
		return v;
	}

	public void moure() {
		x = x + dsx;
		// si arriva als marges ...
		if (y >= 500 - ty || y <= ty)
			dsy = -dsy;
	}

	public void pinta(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.image, x, y, null);
	}

	public void run() {
		while (true) {
			// System.out.println("Movent nau numero " + this.nomNau);
			try {
				Thread.sleep(this.v);
			} catch (Exception e) {
			}
			moure();
		}
	}

	public void esquerra() {
		this.dsx = -10;
	}

	public void dreta() {
		this.dsx = 10;
	}
}