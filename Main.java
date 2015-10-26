package SeminarkaPT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		/** vytvori ArrayList do ktereho se budou ukladat objekty Entita */
		ArrayList<Entity> entities = new ArrayList<Entity>();
		ArrayList<Vertex> planets = new ArrayList<Vertex>();
		
		/** vytvori promenou ktera urcuje pocet central v galaxii */
		int factoriesCount = 5;
		/** vytvori promenou ktera urcuje pocet sousedu kazde centraly */
		int neighbourCountF = 20; 
		System.out.println("Zadej pocet planet");
		/** vytvori promenou ktera urcuje pocet planet v galaxii */
		int planetsCount = sc.nextInt();
		/** vytvori promenou ktera urcuje pocet sousedu kazde planety(v zadani 5) */
		int neighbourCountP = 6;
		/** vytvori matici do ktere se budou ukladat id nejblizsich 6(5) sousedu */
		int[][] adjId = new int[planetsCount+factoriesCount][neighbourCountP];
		
		/** zavola metodu, ktera vytvori centraly */
		entities = Data.factoriesDistribution(factoriesCount, planetsCount, entities, neighbourCountF);
		/** zavola metodu, ktera vytvori planety */
		entities = Data.planetsDistribution(factoriesCount, planetsCount, entities, neighbourCountP);		
		/** zavola metodu, ktera vyplni matici Id sousedu */
		adjId = Data.idSousedu(planetsCount, factoriesCount, neighbourCountP); 
		/** zavola metodu, ktera nazorne vykresli galaxii, tj. plnety,  centraly a cesty mezi nimi */
		new Mapa(planetsCount,factoriesCount, entities, adjId);
		
		/** V tomto foru se  na planety "nasadi na grafovou strukturu" */
		for(int i = 0; i < planetsCount; i ++){
			Vertex veretexesP = new Vertex(i, 'B');            
			planets.add(veretexesP);
		}
		//potrebuju neco pushnout--
	}
}