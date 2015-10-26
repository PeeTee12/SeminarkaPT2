package SeminarkaPT;

public class Factory extends Entity{	
	  																		/* konstruktor */	     
	  /**
	   * Vytvori centralu se zadanym id a souradnicemi, poctem sousedu
	   * @param id 
	   * @param xSour, ySour
	   * @param pocetSousC
	   */ 
	   public Factory(int id, double xAxis, double yAxis, int neighbourCount) {
		   super(id, xAxis, yAxis, neighbourCount);
	  }
}
