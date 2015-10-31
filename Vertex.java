package SeminarkaPT;

import java.util.ArrayList;

/**
 * Trida pro reprezentaci vrcholu
 * @author Scien_000
 */

class Vertex {
	
	/** deklaruje promenou pro identifikaci vrcholu, obdoba Id u objektu */
	protected int key;		
	/** deklaruje promenou pro algoritmy hledani nejkratsich cest */
	protected char color;		
	/** deklaruje promennou uchovavajici pocet predchucu vrcholu */ 
	protected int predecessor;		
	/** deklaruje promennou uchovavajici vzdalenost vrcholu */
	protected double distance;
	/** prommena uchovavajici pocet sousedu*/
	protected int neighbourCount;
	protected Vertex[] neighbour;
	
																				//konstruktory
	/**
	 * Konstruktor vrcholu
	 * @param key
	 * @param color
	 */
	 Vertex(int key, char color) {
			this.key = key;
			this.color = color;
		}
	/**
	 * Konstruktor vrcholu 
	 * @param key
	 * @param color
	 * @param neighbourCount
	 * @param adjId
	 * @param planets
	 */
	Vertex(int key, char color, int neighbourCount, int[][] adjId, ArrayList<Vertex> entitiesV) {
		this.key = key;
		this.color = color;
		this.neighbourCount = neighbourCount;
	
		/*
		for(int i = 0; i < neighbourCount; i++) {
			neighbour[i] = adjId[key][i];
		}*/
	}
	
																			//metody
	/**
	* Metoda pro tisk vrcholu
	*/
	public void printVertex() {
		System.out.print(key+" ");
	}
}
