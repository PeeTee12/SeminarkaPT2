package SeminarkaPT;

//import javax.lang.model.element.NestingKind;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;


public class Mapa extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** promenna uchovavajici pocet central*/
	int factoriesCount;
	/** promenna uchovavajici pocet planet*/
	int planetsCount;
	int neighbourCountF;
	int neighbourCountP;
	/** ArrayList uchovavajici vsechny objekty typu Entita*/
	ArrayList<Entity> ar;
	/** matice, ktera uchovava id 5 nejblisich sousedu*/
	int [][] adjIdF, adjIdP;

	/**
	 * 
	 * @param planetsCount
	 * @param factoriesCount
	 * @param ar
	 * @param adjId
	 */
	public Mapa(int factoriesCount, int planetsCount, int neighbourCountF, int neighbourCountP, ArrayList<Entity>ar,int [][] adjIdF, int [][] adjIdP) {
		this.factoriesCount = factoriesCount;
		this.planetsCount = planetsCount;
		this.neighbourCountF = neighbourCountF;
		this.neighbourCountP = neighbourCountP;
		this.ar = ar;
		this.adjIdF = adjIdF;
		this.adjIdP = adjIdP;
		
		
		this.setTitle("Mapa");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	/**
	 * 
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		paint2D((Graphics2D)g);
	}
	
	/**
	 * 
	 * @param g2
	 */
	public void paint2D(Graphics2D g2) {
	
		System.out.println("Prave jsem vstoupil do kresleni mapy");
		g2.setColor(Color.BLUE);
		for(int i = factoriesCount; i < ar.size(); i++) {
			g2.fill(new Ellipse2D.Double(ar.get(i).getXAxis()+100, ar.get(i).getYAxis()+130 , 4, 4));
		}
		
		
		g2.setColor(Color.GREEN);
		for(int j = 0; j < factoriesCount; j++) {
			g2.fill(new Ellipse2D.Double(ar.get(j).getXAxis()+100, ar.get(j).getYAxis()+130, 7, 7));
		}
		
		
		g2.setColor(Color.RED);
		g2.fill(new Ellipse2D.Double(400 + 100, 400 + 130, 10, 10));
		
		
		g2.setColor(Color.BLACK);
		for (int i = 0; i < ar.size(); i++) {	
			if(i < factoriesCount) {
				for (int j = 0; j < neighbourCountF; j++) {
					g2.draw(new Line2D.Double(ar.get(i).getXAxis()+2+100, ar.get(i).getYAxis()+2+130, ar.get((adjIdF [i][j])).getXAxis()+2+100,  ar.get((adjIdF [i][j])).getYAxis()+2+130));
				}				
			}		
			else{
				for (int j = 0; j < neighbourCountP; j++) {
					g2.draw(new Line2D.Double(ar.get(i).getXAxis()+2+100, ar.get(i).getYAxis()+2+130, ar.get((adjIdP [i-factoriesCount][j])).getXAxis()+2+100,  ar.get((adjIdP [i-factoriesCount][j])).getYAxis()+2+130));
				}
			}
		} 
	}
}
