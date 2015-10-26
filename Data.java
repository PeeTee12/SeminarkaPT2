package SeminarkaPT;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Data {
	/** pole se vzdalenostmi planet */
	private static double[][] distance;		
	/** pole se 6(5) nejblizsimi vzdalenostmi */
	private static double[][] adjDistance;		
	/** pole se 6(5) Id nejblizsich vzdalenosti */
	private static int[][] adjId;
	/** x-ove a y-ove souradnice objektu */
	private static double xAxis, yAxis;
	 
	/**
	 * Tato metoda vytvori centraly na pozadovanych(vyhodnych) souradnicich
	 * @param factoriesCount
	 * @param planetsCount
	 * @param entity
	 * @param adjFactoriesCount
	 * @return
	 */
	 public static ArrayList<Entity> factoriesDistribution(int factoriesCount, int planetsCount, ArrayList<Entity> entities, int adjFactoriesCount) {
		 distance = new double[planetsCount+factoriesCount][planetsCount+factoriesCount];     //deklaruje velikost matice uchvavajici vzdalenost objektu
		 
		 // vytvori centraly rozlozene pravidelne na kruznici se stredem ve stredu souradneho systemu
		 for (int i = 0; i < factoriesCount; i++) {			  
			 xAxis = 200*Math.cos(Math.toRadians(72*i))+400;                                  // vypocte x-ovou souradnici
			 yAxis = (-1*200*Math.sin(Math.toRadians(72*i)))+400;                             // vypocte y-ovou souradnici
			 Factory centrala = new Factory(i, xAxis, yAxis, adjFactoriesCount);              // vytvori objekt centrala s pozadovanymi parametry
			 entities.add(centrala);                                                          // prida objekt centraly do AL entity
		  }
		  
		 // nastavi vzdalenost central 10000(inf), aby nebyly voleny za sousedy
		 for (int i = 0; i < factoriesCount; i++) {
			 for (int j = 0; j < factoriesCount; j++) {
				 distance[i][j] = 10000;
				 distance[j][i] = 10000;
			 }
		 }		  
		  return entities;
	  }
	 
	 /**
	  * Generuje random rozlozeni planet na zadanem uzemi.
	  * @param factoriesCount
	  * @param planetsCount
	  * @param entity
	  * @param adjPlnaetsCount
	  * @return
	  */
	 public static ArrayList<Entity> planetsDistribution(int factoriesCount,int planetsCount, ArrayList<Entity> entities, int adjPlanetsCount) {
		 adjDistance = new double[factoriesCount+factoriesCount][adjPlanetsCount];                 //deklaruje velikost matice uchvavajici vzdalenost sousednich objektu
		 adjId = new int[factoriesCount+factoriesCount][adjPlanetsCount];                          //deklaruje velikost matice uchvavajici Id objektu

		 int boundX = 800+2,  boundY = 800+2;
		 double adj, adjAct = 0;
		 /** pomocny ArrayList, ktery slouzi k ukladani vzdalenosti planet a nasledne kontroly splneni podminky min. vzdalenosti2 */
		 ArrayList<Double> vzdPom = new ArrayList<Double>();		   
		 Random rX = new Random(), rY = new Random();
	

		 int i = 0;
		 /** cyklus, ktery bezi, tak dlouho, dokud nejsou vytvoreny vsechny pozadovane planety */ 
		 while(i < factoriesCount) {
			 vzdPom.clear();																// vycisti AL s pomocnymi vzdalenostmi, aby se nepletli s novymi(AL by se nafoukl do nekonecna)
			 xAxis = rX.nextInt(boundX);													    // vygeneruje nahodne X, Y souradnice v zadanem rozmezi
	 		 yAxis = rY.nextInt(boundY); 	
	 		 	for(int j = 0; j < entities.size(); j++) {									// spocte zda  nove vytvarena planeta je dostatecne daleko od vdech vytvorenych entit
	 																// pro centraly, tj. j=0;1;2;3;4
	 		 			adjAct = Math.sqrt( Math.pow( (entities.get(j).getXAxis() - xAxis), 2) + Math.pow((entities.get(j).getYAxis() - yAxis), 2) );
		 		 		vzdPom.add(adjAct);	
		 		 	  	distance[j][i+factoriesCount] = adjAct;
		 		 	  	distance[i+factoriesCount][j] = adjAct;
	 		 		
	 		 	}	
	 		 adj = Collections.min(vzdPom); 												// vybere nejmensi vzdaenost prave vytvarene planety a nejblizsi entity
	 		 if(adj > 2) { 																	// overi, zda dana planeta je dostatecne daleko, pokud ano, tak ji vytvori a prida do AL entit
	 		 		Planet pl = new Planet(i, xAxis, yAxis, adjPlanetsCount);
	 		 		entities.add(pl);
	 		 		i++;
	 		 }
		  }		
	 	return entities;
	  }
	  
	 /**
	  * metoda na zjisteni Id sousedu
	  * @param planetsCount
	  * @param factoriesCount
	  * @param adjPlanetsCount
	  * @return
	  * @throws IOException
	  */
	 public static int [][] idSousedu(int planetsCount, int factoriesCount, int adjPlanetsCount) throws IOException {	
		 double[] seraz = new double[planetsCount+factoriesCount];									// pomocne pole pro serazeni vzdalenosti
		 BufferedWriter bw1 = new BufferedWriter(new FileWriter("seznamVzdal.txt"));		// vypise do textaku vzdalenost entit
		 BufferedWriter bw2 = new BufferedWriter(new FileWriter("seznamVzdalSeraz.txt"));	// vypise do textaku serazene vzdalenosti entit
		 BufferedWriter bw3 = new BufferedWriter(new FileWriter("seznamId.txt"));			// vypise do textaku Id sousedu
		  
		 for (int k = 0; k < planetsCount+factoriesCount; k++) {
			 for (int l = 0; l < planetsCount+factoriesCount; l++) {
				 seraz [l] = distance[k][l];													//ulozi prislusny radek do pole, ktere se pak seradi		
				  if(l == 0){
					  bw1.write("Entita c."+(k+1)+":  ");
				  }
				  bw1.write(Math.floor(distance [k][l])+"  ");								// vypise vzdalenost entity k od entity l
				  if(l == planetsCount+factoriesCount-1){
					  bw1.newLine();
					  bw1.newLine();
				  }
			 }	
			 Arrays.sort(seraz);															//seradi prislusny radek z mVzdal
			 for (int m = 0; m < adjPlanetsCount; m++) {
				 adjDistance [k][m] = seraz [m] ;												//ulozi 6(5) nejblizsich sousedu pro nasledne vytvoreni cest			
				 if(m == 0){
					 bw2.write("Planeta c."+(k+1)+":  ");
				 }
				 bw2.write(Math.floor(adjDistance [k][m])+"  ");								// vypise vzdalenosti  6(5) nejblizsich entit 
				 if(m == adjPlanetsCount-1){
					 bw2.newLine();
					 bw2.newLine();
				 }
			 }
			  
			 for (int n = 1; n < adjPlanetsCount; n++) {
				 for (int o = 0; o < planetsCount+factoriesCount; o++) {
					 if( adjDistance [k][n] == distance [k][o]) {
						 adjId [k][n] = o;											//zjisti id nejblizsich 6(5) sousedu
					 }
				 }
				 if(n == 1){
					 bw3.write("Planeta c."+(k+1)+":  ");
				 }
				 bw3.write(Math.floor(adjId [k][n])+"  ");
				 if(n == adjPlanetsCount-1){
					 bw3.newLine();
					 bw3.newLine();
				 }
			}
		}
		bw1.close();																	// uzavre vypis do souboru		
		bw2.close();
		bw3.close();
		return adjId; 
	  }	  
}
