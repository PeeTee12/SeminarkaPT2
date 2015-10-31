package SeminarkaPT;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Trida pro reprezentaci grafu
 * @author Scien_000
 */
public class Graph {
	
	/** tato promenna uchovava vrcholy nasi grafove striktury*/
	private static Vertex[] vertexes;					
	/** tato promenna uchovava informaci o tom zda spoluvrcholy sousedi*/
	private int[][] adjMatrix;
	/** tato promenna uchovava konecny pocet vrcholu*/
	private static int vertexCount;	
	/** tato promenna uchovava pocet vytvorenych vrcholu*/
	private int vertexCreated = 0;	
	/** promenna uchovavajici informaci o to zda je graf orientovany */
	private boolean connected;			
	private static int[][] shortestPath;
	
	                                                              /* konstruktor */	   
	/**
	 * Konstruktor grafu
	 * @param pocVr
	 */
	Graph(int vertexCount, boolean connected) {
    this.vertexCount = vertexCount;												//inicializuje pocet vrcholu			
    this.adjMatrix = new int[vertexCount][vertexCount];							//inicializuje velikost matice sousednosti
	this.vertexes = new Vertex[vertexCount];									//inicializuje velikost pole vrcholu
	this.connected = connected;													//inicializuje typ grafu
	
	for (int i = 0; i < vertexes.length; i++) {									//pro jistotu pole vunulluje
    	vertexes[i] = null;
    }
  }
	
  	/**
  	 * Metoda pro ziskani Id vrcholu
  	 * @param key
  	 * @return
  	 */
  	public static int getId(int key) {
  		int pom = -1;
  		for (int i = 0; i < vertexCount; i++) {
  			if (vertexes[i].key == key) {
  				pom = i;
  			}
  		}
  		return pom;
  	}
		
  	/**
  	 * Metoda pro vlozeni vrcholu
  	 * @param vertex
  	 */
  	public void addVertex(int key) {
  		Vertex pom = new Vertex(key,'B');
  		vertexes[vertexCreated++] = pom;
  	}
	
  	/**
  	 * Metoda pro vlozeni hrany grafu
  	 * @param start
  	 * @param end
  	 */
  	public	void addEdge(int start, int end) {
  		for (int m = 0; m < vertexCount; m++) {
  			if (vertexes[m].key == start) {
  				for (int n = 0; n < vertexCount; n++) {
  					if (vertexes[n].key == end) {
  						adjMatrix[m][n] = 1;
  						if(connected == false){
  							adjMatrix[n][m] = 1;
  						}
  					}
  				}
  			}
  		}
  	}
	
  	/**
  	 * Metoda pro vypsani matice sousednosti na obrazovku 
  	 */
  	public void printAdjMatrix() {
  		System.out.println("\nMatice sousednosti\n");
  			for (int m = 0; m < vertexCount; m++) {
  				for (int n = 0; n < vertexCount; n++) {
  					System.out.print(adjMatrix[m][n]+" ");
  				}
  				System.out.println();
  			}
  			System.out.println();
  	}
	

  	/**
  	 * Metoda pro prohledavani grafu do sirky       	TATO METODA SE MODIFIKUJE NEBO NAHRADI JINOU PRO HLEDANI NEJKRATSICH CEST
  	 * @param start
  	 */
  	public void BFS(int start) {
  		ArrayList<Integer> queue = new ArrayList<Integer>(vertexCount);			//vytvori AL a vrcholy(frontu)
  		int s = getId(start);													//promenna uchovavajici vrchol z ktereho se vychazi
  		int counter = 0;
  		int i;
	
  		vertexes[s].color = 'S';
  		vertexes[s].distance = 0;
  		queue.add(s);
		
  		System.out.println("  0 1 2 3");
  		System.out.print(counter);
  		for (int j = 0; j < vertexCount; j++) {
  			System.out.print(" "+vertexes[j].color);
		}
  		System.out.println("   V dalsim kroce budu pracovat s vrcholem "+queue.get(0));

  		while (!queue.isEmpty()) {
  			
  			i = queue.get(0);
  			for (int j = 0; j < vertexCount; j++) {
  				if (adjMatrix[i][j] == 1) {										//zjisti zda jsou vrcholy i a j spojeny hranou
  					if (vertexes[j].color == 'B') {								//zjisti zda byl vrchol uz nalezen
  						vertexes[j].color = 'S';								//oznaci vrchol za nalezeny
  						vertexes[j].distance = vertexes[i].distance + 1;
  						vertexes[j].predecessor = i;
  						queue.add(getId(j));
  					}
  				}
  			}
  			vertexes[i].color = 'C';
  			//vertexes[i].printVertex();
  		
  	  		System.out.print(counter+1);
  	  		for (int k = 0; k < vertexCount; k++) {
  	  			System.out.print(" "+vertexes[k].color);
  			}
  	  		queue.remove(0);
  	  		if(!queue.isEmpty()) {
  	  		System.out.println("   V dalsim kroce budu pracovat s vrcholem"+queue.get(0));
  	  		}
  			
  			counter++;
  		}
  	}
  	
  	public static int[][] shortestPath (ArrayList<Vertex> entitiesV) throws IOException {
  		shortestPath = new int[entitiesV.size()][entitiesV.size()];
  		ArrayList<Vertex> queue = new ArrayList<Vertex>(vertexCount);			//vytvori AL a vrcholy(frontu)
  		
  		for(int start = 0; start < entitiesV.size(); start++) {                       // pro kazdy vrchol s najde nejkratsi cestu do vsech ostatnich vrcholu
  		   //int s = getId(start);													//promenna uchovavajici vrchol z ktereho se vychazi
	
  		// zacatek relaxacniho algoritmu
  		   entitiesV.get(start).distance = 0;     // vrchol je od sebe vzdaleny 0 jednotek                         // 1
  		   for(int i = 1; i < entitiesV.size(); i ++) {                                                        // 1
  			  entitiesV.get(i).distance = 100000;																 // nastavi vzdalenost vsech ostatnich vrcholu 100000(inf)
		   }
  		
  		   entitiesV.get(0).color = 'S';      //nastavi vrchol jako videny                                     // 2
  		   queue.add(entitiesV.get(0));       // prida planetu do fronty a zacne hledat nejkratsi cestu k ostatnim planetam
  		
  		   while (queue.size() != 0) {    //dokud existuji otevrene vrcholy, opakujeme                       // 3
  		       queue.get(0).color = 'C';  //vyber lib. vrchol a oznac ho jako uzavreny                       // 4 a 5
  		       for(int i = 0; i < queue.get(0).neighbourCount; i ++) {//relaxujeme lib.vrcchol                 // 6
    		//pridat sousedy do fronty
  			      queue.get(0).neighbour[i].color = 'S';                                                        // 7
  			      if( queue.get(i).distance > ( queue.get(0).distance + Data.getDistance()[queue.get(0).key][queue.get(i).key]) ) {  // 8
  				     queue.get(i).distance = queue.get(0).distance + Data.getDistance()[queue.get(0).key][queue.get(i).key];        // 9
  				     // tady je jeste treba dodelat ukladani vrcholu na ceste
  				      shortestPath[start][i]= (int) (entitiesV.get(i).distance);
  			      }  
  		      }
  		   }  	  	
  		}
  		return shortestPath;                                                                              // 10
  	}
  	
}