package SeminarkaPT;

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
	/** deklaruje promennou uchovavajici vzdalenost vrcholu*/
	protected int distance;
	
	
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
	* Metoda pro tisk vrcholu
	*/
	public void printVertex() {
		System.out.print(key+" ");
	}
}
