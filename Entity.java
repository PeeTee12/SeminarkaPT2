package SeminarkaPT;

import java.util.Random;

public class Entity {
																		/* deklarace promennych */
	Random r = new Random();
	/** promenna uchovavajici idetifikacni cislo objektu */
	protected int id;												 
	/** promenna uchovajici x souradnici objektu */
	protected double xAxis;											
	/** promenna uchovavajici y souradnici obejektu */
	protected double yAxis;											
	/** promenna uchovavajici pocet sousedu objektu, tj.objekty spojene cestou */
	protected int neighbourCount;									
	
	
				
	 																	  /* konstruktor */
	 public Entity(int id, double xAxis, double yAxis, int neighbourCount) {
		 this.id = id;
		 this.xAxis = xAxis;
		 this.yAxis = yAxis;
		 this.neighbourCount = neighbourCount;
	}
	 
																		 /* gettery */
	 public int getId() {
		 return id;
	 }
	  
	 public double getXAxis() {
		 return xAxis;
	 }
	  
	 public double getYAxis() {
		 return yAxis;
	 }

	 public int getNeighbourCount() {
		 return neighbourCount;
	 }
}