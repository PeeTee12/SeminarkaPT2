package SeminarkaPT;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Data {
	/** pole se vzdalenostmi entit */
	private static double[][] distance;		
	/** pole se n nejmensimi vzdalenostmi mezi centralou a jejimi sousedy */
	private static double[][] adjDist;				
	/** pole s n Id nejblizsich sousedu centraly */
	private static int[][] adjId;
	/** x-ove a y-ove souradnice objektu */
	private static double xAxis, yAxis;
	/** pomocna promenna uchovavajici aktualni vzdalenost*/
	private static double actDist = 0;
	 
	
	/**
	 * Tato metoda vytvori centraly na pozadovanych(vyhodnych) souradnicich
	 * @param factoriesCount
	 * @param planetsCount
	 * @param entity
	 * @param adjFactoriesCount
	 * @return
	 */
	 public static ArrayList<Entity> factoriesDistribution(int factoriesCount, int planetsCount, int neighbourCountF, ArrayList<Entity> entities) {
		 distance = new double[planetsCount+factoriesCount][planetsCount+factoriesCount];     		//deklaruje velikost matice uchvavajici vzdalenost objektu
		 
		 for (int i = 0; i < factoriesCount; i++) {													// vytvori centraly rozlozene pravidelne na kruznici se stredem ve stredu souradneho systemu	  
			 xAxis = 200*Math.cos(Math.toRadians(72*i))+400;                                  		// vypocte x-ovou souradnici
			 yAxis = (-1*200*Math.sin(Math.toRadians(72*i)))+400;                             		// vypocte y-ovou souradnici
			 Factory centrala = new Factory(i, xAxis, yAxis, neighbourCountF);              		// vytvori objekt centrala s pozadovanymi parametry
			 entities.add(centrala);                                                         		// prida objekt centraly do AL entity
		  }
		
		 /* novejsi zpusob by melbyt presnejsi
		 for (int i = 0; i < factoriesCount; i++) {													// nastavi vzdalenost mezi centralami 100000(inf), aby se nevolily za sousedy
			 for (int j = 0; j < factoriesCount; j++) {
				 distance[i][j] = 100000;
				 distance[j][i] = 100000;
			 }
		 }  */
		 for (int i = 0; i < factoriesCount; i++) {	
			 for(int j = 0; j < factoriesCount; j++) {												// spocte zda  nove vytvarena planeta je dostatecne daleko od vsech vytvorenych entit
				actDist = Math.sqrt( Math.pow( (entities.get(j).getXAxis() - entities.get(i).getXAxis()), 2) + Math.pow((entities.get(j).getYAxis() - entities.get(i).getYAxis()), 2) );
	 			distance[i+factoriesCount][j] = actDist;											// ulozi vzdalenost prave vytvarene planety od entity j do pomocneho AL
	 			distance[j][i+factoriesCount] = actDist;											// matice distance je symetricka
			 }	
		 }
		 return entities;
	  }
	 
	 /**
	 * Generuje nahodne rozlozeni planet na zadanem uzemi.
	 * @param factoriesCount
	 * @param planetsCount
	 * @param entity
	 * @param neighbourCountP
	 * @return
	 */
	 public static ArrayList<Entity> planetsDistribution(int factoriesCount, int planetsCount,int neighbourCountP, ArrayList<Entity> entities) {
		 int boundX = 800+2,  boundY = 800+2;													   // nasteaveni mezi
		 ArrayList<Double> auxDist = new ArrayList<Double>();		   							   // pomocny ArrayList, ktery slouzi k ukladani vzdalenosti planet a nasledne kontroly splneni podminky min. vzdalenosti 2	
		 Random rX = new Random(), rY = new Random();
	
		 int i = 0;
		 while(i < planetsCount) {																	// cyklus, ktery bezi, tak dlouho, dokud nejsou vytvoreny vsechny pozadovane planety
			 auxDist.clear();																		// vycisti AL s pomocnymi vzdalenostmi, aby se nepletli s novymi(AL by se zvetoval do nekonecna)
			 xAxis = rX.nextInt(boundX);													    	// vygeneruje nahodne X, Y souradnice v zadanem rozmezi
	 		 yAxis = rY.nextInt(boundY); 	
	 		 for(int j = 0; j < entities.size(); j++) {												// spocte zda  nove vytvarena planeta je dostatecne daleko od vsech vytvorenych entit
	 			 actDist = Math.sqrt( Math.pow( (entities.get(j).getXAxis() - xAxis), 2) + Math.pow((entities.get(j).getYAxis() - yAxis), 2) );
		 		 auxDist.add(actDist);																// ulozi vzdalenost prave vytvarene planety od entity j do pomocneho AL
		 		 distance[i+factoriesCount][j] = actDist;											// matice distance je symetricka
		 		 distance[j][i+factoriesCount] = actDist;
		 	 }	
	 		 actDist = Collections.min(auxDist); 													// vybere nejmensi vzdalenost prave vytvarene planety a nejblizsi entity
	 		 if(actDist > 2) { 																		// overi, zda dana planeta je dostatecne daleko, pokud ano, tak ji vytvori a prida do AL entit
	 		 		Planet pl = new Planet(i, xAxis, yAxis, neighbourCountP);
	 		 		entities.add(pl);
	 		 		i++;
	 		 }
		  }		
	 	  return entities;
	  }
	  
	 /**
	  * Tato metoda zjisti Id sousedu
	  * @param planetsCount
	  * @param factoriesCount
	  * @param neighbourCountP
	  * @return
	  * @throws IOException
	  */
	 public static int [][] idSousedu(int entitiesCount, int auxEntCount, int neighbourCount) throws IOException {	
		 adjDist = new double[entitiesCount][neighbourCount];       								// deklaruje velikost matice uchvavajici vzdalenost central a jejich sousedu
		 adjId = new int[entitiesCount][neighbourCount];                           					// deklaruje velikost matice uchvavajici Id sousedu centraly
		 double[] auxAr = new double[entitiesCount+auxEntCount];									// pomocne pole pro serazeni vzdalenosti
		 BufferedWriter bw1 = new BufferedWriter(new FileWriter("seznamVzdal.txt", true));				// BW na vypis do textaku vzdalenosti entit
		 BufferedWriter bw2 = new BufferedWriter(new FileWriter("seznamVzdalSeraz.txt", true));			// BW na vypis do textaku serazenych vzdalenosti entit
		 BufferedWriter bw3 = new BufferedWriter(new FileWriter("seznamId.txt", true));					// BW na vypis do textaku Id sousedu
		  
		 for (int i = 0; i < entitiesCount+auxEntCount; i++) {
			 for (int j = 0; j < entitiesCount+auxEntCount; j++) {
				 auxAr [j] = distance[i][j];														// prepise j-ty radek do pomocneho pole		
				 if(j == 0){
					 bw1.write("Entita c."+(i+1)+":  ");											// pro kazdy prvek udela novy popisek, pouze pro prehlednost textu	
				 }
				 bw1.write(Math.floor(distance [i][j])+"  ");										// vypise vzdalenost entity i od entity j, zakrouhleni je z duvodu setreni mista
				 if(j == entitiesCount+auxEntCount-1){												// nez se zacne pracovat s dalsim objektem odradkuje, opet pouze pro prehlednost textu	
					 bw1.newLine();
					 bw1.newLine();
				 }
			 }	
			 Arrays.sort(auxAr);																	//seradi vzdalensoti objektu i od vsech ostatnich
			
			 for (int j = 0; j < neighbourCount; j++) {												// v tomto foru se najde N nejblizsich sousedu objektu i
				if(i < entitiesCount){	
					adjDist [i][j] = auxAr[j] ;														//ulozi N nejblizsich sousedu pro nasledne vytvoreni cest		
					if(j == 0){
						bw2.write("Entita c."+(i+1)+":  ");												// pro kazdy prvek udela novy popisek, pouze pro prehlednost textu	
					}
					bw2.write(Math.floor(adjDist [i][j])+"  ");										// vypise vzdalenosti 5 nejblizsich entit 
					if(j == neighbourCount-1) {														// nez se zacne pracovat s dalsim objektem odradkuje, opet pouze pro prehlednost textu
						bw2.newLine();
						bw2.newLine();
					}
				}
			}
			
			 
			for (int j = 1; j < neighbourCount; j++) {											// v tomto foru se zjisti Id 5 nejblizsich sousedu objektu i
				 for (int k = 0; k < entitiesCount+auxEntCount; k++) {
					 if(i < entitiesCount){	
						 if( adjDist [i][j] == distance [i][k]) {									// zjistujeme, kdy se vzdalenosti rovnaji, predpokladame, ze nemuze nastat nejednoznacnost
							 adjId [i][j] = k;															//zjisti id nejblizsich 5 sousedu
						 }
						 if(j == 1){
							bw3.write("Entita c."+(i+1)+":  ");											// pro kazdy prvek udela novy popisek, pouze pro prehlednost textu	
						 }
						 bw3.write(Math.floor(adjId [i][j])+"  ");
						 if(j == neighbourCount-1){														// nez se zacne pracovat s dalsim objektem odradkuje, opet pouze pro prehlednost textu
							bw3.newLine();
							bw3.newLine();
						 }
					}
				 }
			}
		}
		bw1.close();																				// uzavre vypis do souboru		
		bw2.close();																				// uzavre vypis do souboru
		bw3.close();																				// uzavre vypis do souboru
		return adjId; 
	  }	  
	 
	 public static double[][] getDistance() {	
		 return distance;
	 }
}


