package csp1150.assignment1.model;

/**
 * This class defines the shape model.
 * The shape's attributes are saved here,
 * and all calculation is handled by the methods
 * in this class.
 * 
 * @author Martin Ponce ID# 10371381
 * @version 5.1.0
 * @since 20141011
 */
public class ShapeModel {

	// declare constant grid size & samples
	protected final int GRIDSIZE = 444;
	protected final int SAMPLES = 100;
	
	// declare 2d array
	protected double[][] hits = new double[GRIDSIZE][GRIDSIZE];
	
	// these fields hold user input values
	protected double outRadius;
	protected double inRadius;
	
	// these fields hold the max-min values aka zoom values
	protected double minX;
	protected double minY;
	protected double maxX;
	protected double maxY;
	
	// these fields hold the calculated values
	private double areaApprox;
	protected double areaMonte;
	
	/**
	 * Default constructor for inheritance.
	 */
	public ShapeModel() {
		
	}
	
	/**
	 * This method sets the radius.
	 * 
	 * @param double outRadius - The outer radius.
	 * @param double inRadius - The inner radius.
	 */
	public final void setRadius(double outRadius, double inRadius) {
		
		// set incoming radius values to their respective variables
		this.outRadius = outRadius;
		this.inRadius = inRadius;
		
		// set incoming radius values to their respective max-min variables
		this.minX = -outRadius;
		this.minY = -outRadius;
		this.maxX = outRadius;
		this.maxY = outRadius;
		
		// do Area calculation
		calcApprox(this.minX, this.minY, this.maxX, this.maxY);
		
		// do Monte calculation
		calcMonte(this.minX, this.minY, this.maxX, this.maxY);
	}
	
	/**
	 * This method calculates the area using approximate estimation.
	 * Max-min values control how zoomed in the shape is.
	 * 
	 * @param double minX - The min X coordinate.
	 * @param double minY - The min Y coordinate.
	 * @param double maxX - The max X coordinate.
	 * @param double maxY - The max Y coordinate.
	 */
	protected final void calcApprox(double minX, double minY, double maxX, double maxY) {
		
		// declare counter
		int counter = 0;
		
		// iterate through columns
		for(int col = 0; col < GRIDSIZE - 1; col++) {
			
			// calculate column center
			double x = minX + (col + 0.5) * ((maxX - minX) / GRIDSIZE);
			
			// iterate through rows
			for(int row = 0; row < GRIDSIZE - 1; row++) {
				
				// calculate row center
				double y = minY + (row + 0.5) * ((maxY - minY) / GRIDSIZE);
				
				 // if test pass, set counter + 1
				if(isInside(x, y)) {
					counter++;
				}
			}
		}
		
		// set calculated area to areaApprox
		areaApprox = (maxX - minX) * (maxY - minY) * counter / Math.pow(GRIDSIZE, 2);
	}
	
	/**
	 * This method calculates the area using monte carlo estimation.
	 * Max-min values control how zoomed in the shape is.
	 * 
	 * @param double minX - The min X coordinate.
	 * @param double minY - The min Y coordinate.
	 * @param double maxX - The max X coordinate.
	 * @param double maxY - The max Y coordinate.
	 */
	public final void calcMonte(double minX, double minY, double maxX, double maxY) {
		
		// declare counter
		double counter = 0.0;
		
		// declare arraySum
		double arraySum = 0.0;
		
		// iterate through columns
		for(int col = 0; col < GRIDSIZE - 1; col++) {
			
			// iterate through rows
			for(int row = 0; row < GRIDSIZE - 1; row++) {
				
				// reset current cell value to 0
				hits[col][row] = 0;
				
				// iterate through samples
				for(int i = 0; i < SAMPLES; i++) {
					
					// generate random scatter points per cell
					double x = minX + (col + Math.random()) * ((maxX - minX) / GRIDSIZE);
					double y = minY + (row + Math.random()) * ((maxY - minY) / GRIDSIZE);
					
					// if test pass, set counter + 1
					if(isInside(x, y)) {
						hits[col][row] = 1;
						arraySum++;
					}
				}
			}
		}
		
		// divide sum of array to samples
		arraySum = arraySum / SAMPLES;
		
		// add sum of array to counter
		counter = counter + arraySum;
		
		// calculate area
		areaMonte = (maxX - minX) * (maxY - minY) * counter / Math.pow(GRIDSIZE, 2);
	}
	
	/**
	 * This method checks if center of cell, or hitpoints are within shape perimeter.
	 * 
	 * @param double x - The x-axis value.
	 * @param double y - The y-axis value.
	 * @return boolean inside - Is the hitpoint inside the shape perimeter.
	 */
	protected boolean isInside(double x, double y) {
		
		// calculate test value
		double test = x * x + y * y;
		
		// declare boolean variable to be returned
		boolean inside;
		
		// if test value less than outer radius squared and greater than inner radius squared
		if(test < outRadius * outRadius && test > inRadius * inRadius) {
			
			// set boolean to true
			inside = true;
			
		// else set boolean value to false
		} else {
			inside = false;
		}
		
		// return boolean value
		return inside;
	}
	
	/**
	 * This method returns the area calculated using approximate estimation.
	 * 
	 * @param args unused
	 * @return double areaApprox
	 */
	public final double getAreaCalc() {
		return areaApprox;
	}
	
	/**
	 * This method returns the area calculated using monte carlo method.
	 * 
	 * @param args unused
	 * @return double areaMonte
	 */
	public final double getMonteCalc() {
		return areaMonte;
	}
	
	/**
	 * This method returns the hits array to be used for the hit view generator.
	 * 
	 * @param args unused
	 * @return double[][] hits.
	 */
	public final double[][] getHitsArray() {
		return hits;
	}
	
	/**
	 * This method returns the GRIDSIZE constant.
	 * 
	 * @param args unused
	 * @return int GRIDSIZE.
	 */
	public final int getGridSize() {
		return GRIDSIZE;
	}
	
	/**
	 * This method resets the zoom values.
	 * 
	 * @param args unused
	 */
	public void resetZoom() {
		
		this.minX = -this.outRadius;
		this.minY = -this.outRadius;
		this.maxX = this.outRadius;
		this.maxY = this.outRadius;
	}
	
	/**
	 * This method converts pixelzoom values into max-min values,
	 * and sets them to the minX - maxY fields.
	 * 
	 * @param double[] pixelZoom.
	 */
	public void setZoom(double[] pixelZoom) {
		
		// converted value = (pixelzoom / gridsize) * maxrange - halfrange
		this.minX = (pixelZoom[0] / this.GRIDSIZE) * (this.outRadius * 2) - this.outRadius;
		this.minY = (pixelZoom[1] / this.GRIDSIZE) * (this.outRadius * 2) - this.outRadius;
		this.maxX = (pixelZoom[2] / this.GRIDSIZE) * (this.outRadius * 2) - this.outRadius;
		this.maxY = (pixelZoom[3] / this.GRIDSIZE) * (this.outRadius * 2) - this.outRadius;
	}
}
