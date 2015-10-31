package SeminarkaPT;

import java.util.Random;

public class Path {
	
	/**
	 * Promenna nebezpeci. True nebo false.
	 */
	//private boolean dangerous;
	Random r = new Random();

	/**
	 * Urci nebezpeznost cesty. Sance 20 %.
	 * @param dangerous
	 */
	public Path(boolean dangerous) {
		int danger = r.nextInt(5);
		if (danger == 4){
			dangerous = true;
		}
		else
			dangerous = false;
		//System.out.println(danger);
	}
}
