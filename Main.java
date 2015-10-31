package SeminarkaPT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main {
	
	private static int centralaID;
	private static int starshipID;
	
	static Scanner sc = new Scanner(System.in);
	static Random r = new Random();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		/** vytvori ArrayList do ktereho se budou ukladat objekty Entita */
		ArrayList<Entity> entities = new ArrayList<Entity>();
		/** vytvori ArrayList do ktereho se budou ukladat objekty Entita jako vrcholy grafu */
		ArrayList<Vertex> entitiesV = new ArrayList<Vertex>();		
		/** vytvori promenou ktera urcuje pocet central v galaxii */
		int factoriesCount = 5;
		/** vytvori promenou ktera urcuje pocet sousedu kazde centraly */
		int neighbourCountF = 20; 
		System.out.println("Zadej pocet planet");
		/** vytvori promenou ktera urcuje pocet planet v galaxii */
		int planetsCount = sc.nextInt();
		/** vytvori promenou ktera urcuje pocet sousedu kazde planety(v zadani 5) */
		int neighbourCountP = 5;
		/** vytvori pole do ktereho se bude ukladat pst vrcholu na ceste z vrcholu i do vrcholu j*/
		int[][] shortestPath = new int[planetsCount][planetsCount]; 		
		/** vytvori matici do ktere se budou ukladat id nejblizsich 6(5) sousedu */
		int[][] adjIdF = new int[factoriesCount][neighbourCountF];
		int[][] adjIdP = new int[planetsCount][neighbourCountP];
		
		/** zavola metodu, ktera vytvori centraly */
		entities = Data.factoriesDistribution(factoriesCount, planetsCount, neighbourCountF, entities);
		/** zavola metodu, ktera vytvori planety */
		entities = Data.planetsDistribution(factoriesCount, planetsCount, neighbourCountP, entities);		
		/** zavola metodu, ktera vyplni matici Id sousedu */
		adjIdF = Data.idSousedu(factoriesCount, planetsCount, neighbourCountF);
		/** zavola metodu, ktera vyplni matici Id sousedu */
		adjIdP = Data.idSousedu(planetsCount, factoriesCount, neighbourCountP); 
		/** zavola metodu, ktera nazorne vykresli galaxii, tj. plnety,  centraly a cesty mezi nimi */
		new Mapa(factoriesCount, planetsCount, neighbourCountF, neighbourCountP, entities, adjIdF, adjIdP);
		
	
		/** V tomto foru se  na planety "nasadi na grafovou strukturu" */
		
	
		for(int i = 0; i < planetsCount+factoriesCount; i ++){
			if (i<factoriesCount) {
				Vertex veretexesF = new Vertex(i, 'B', neighbourCountF, adjIdF, entitiesV);
				entitiesV.add(veretexesF);
			}else {
				Vertex veretexesP = new Vertex(i, 'B', neighbourCountP, adjIdP, entitiesV);	
				entitiesV.add(veretexesP);
			}   
		}
		
	for (int d = 0; d < 1; d++){ 			//poèet dní, kdy simulace pobìží (pozdìji pùjde nastavit více dní uživatelem)
		for (int i = 4; i < planetsCount; i++){			//cyklus pobìží pro všechny planety
			Planet p = new Planet(entities.get(i).getId(), entities.get(i).getXAxis(), entities.get(i).getYAxis(), entities.get(i).getNeighbourCount());			//volani planety
			centralaID = entities.get(r.nextInt(4)).getId();
			Starship s = new Starship(i, 25, 5000000, centralaID);			//volani lode, musi se doresit ID
			//starshipID = centralaID;
			//najde se planeta nejbližší k centrále
			int oneDayPath = s.getVel();			//lod za jeden den urazi 25 LY
			Scanner sc2 = new Scanner(new File("seznamVzdalSeraz.txt"));
			sc2.skip("Planeta c.1: ");
			//while(sc2.hasNextDouble()){
			double nextPlanet = sc2.nextDouble(); 			//zatím to neète to, co má
			System.out.println(nextPlanet);
			if (nextPlanet > oneDayPath){
				nextPlanet = nextPlanet - oneDayPath;
				System.out.println("Lod je stale na ceste");
			}
			else{
			//podle ID planety se zmìní starshipID
			//starshipID = entities.get(i).getId();
			int cargo = s.getCap();
			if(cargo > p.drugProduction(p.populCount)){				//funkcni pouze pokud je populCount public ve tride Planet
			cargo = cargo - p.drugProduction(p.populCount);			//vylozi se naklad podle potreby
			}
			else{
				//navrat do centraly				//pozdeji proste poleti na dalsi planetu
				//starshipID = centralaID			//dokud simulace trva jeden den, lod na dalsi planety pokracovat nemuze, program zatim nepokracuje
			}
			//System.out.println(naklad);
			//System.out.println(p.populCount);
			}
			
		}
	}
		
		//while (Starship.getCap() > 0){
		if (new Path(false) != null){
			int pirates = r.nextInt(9);
				if (pirates == 8){
					//Lod se vraci do materske centraly
					//capacity = 0;
				}
		}
		
		//lod poleti na nejblizsi planetu
		//if Planet.planetStatus == true
		//na planete vylozi naklad podle Planet.drugProduction
		//else planeta se preskoci a nasleduje na dalsi nejblizsi planetu
		//}
		//}
		
		
		/*
		// zde bude volani metody, ktera naplni
		shortestPath = Graph.shortestPath(entitiesV);
		
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("vrcholVzdal.txt", true));				// BW na vypis do textaku vzdalenosti entit
		
		for (int i = 0; i < shortestPath.length; i++) {
			for (int j = 0; j < shortestPath.length; j++) {												
				
				if(j == 0){
					bw1.write("Entita c."+(i+1)+":  ");												// pro kazdy prvek udela novy popisek, pouze pro prehlednost textu	
				}
				bw1.write(shortestPath[i][j]+"  ");										// vypise vzdalenosti 5 nejblizsich entit 
				if(j == shortestPath.length-1) {														// nez se zacne pracovat s dalsim objektem odradkuje, opet pouze pro prehlednost textu
					bw1.newLine();
					bw1.newLine();
				}
			}			
		}
		bw1.close();
		*/
	}
}