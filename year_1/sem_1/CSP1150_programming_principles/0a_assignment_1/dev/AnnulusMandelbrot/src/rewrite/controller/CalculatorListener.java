package rewrite.controller;

import java.awt.*;
import java.awt.event.*;

import rewrite.model.*;
import rewrite.view.*;

public class CalculatorListener implements ActionListener {

	private CalculatorView theView;
	private ShapeModel theModel;
	private HitViewer theImage;
	
	public CalculatorListener(CalculatorView theView, ShapeModel theModel) {
		
		this.theView = theView;
		this.theModel = theModel;
		this.theImage = theView.getPanelAnnulusImage();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// init default values for annulus input fields
		double outRadius = 0.0;
		double inRadius = 0.0;
		
		// if values are entered, do the following
		try {
			
			// get the annulus radius values from the view
			outRadius = this.theView.getAnnulusOutRadius();
			inRadius = this.theView.getAnnulusInRadius();
			
			// if outer radius < inner radius,
			if(outRadius < inRadius) {
				
				// display error message
				this.theView.displayErrorMessage("Outer radius must be greater than inner radius!");
				
			// else,
			} else {
				
				// set input radius to the model
				this.theModel.setRadius(outRadius, inRadius);
				
				// get calculated approx area from the model, show it in the view
				this.theView.setAnnulusAreaCalc(theModel.getAreaCalc());
				
				// get calculated monte carlo area from the model, show it in the view
				this.theView.setAnnulusMonteCalc(theModel.getMonteCalc());
				
				// call viewHits to get data from the array
				this.theImage.viewHits(theModel.getHits());
				
				// refresh the image
				this.theView.refreshAnnulusImage();
			}
		}
		
		catch(NumberFormatException ex) {
			theView.displayErrorMessage("Enter two values.");
		}
	}
}
