import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Webcam-based drawing
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 *
 * @author Chris Bailey-Kellogg, Spring 2015 (based on a different webcam app from previous terms)
 */
public class PS1_CamPaint extends Webcam {
	private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
	private PS1_RegionFinder finder;			// handles the finding
	private Color targetColor;          	// color of regions of interest (set by mouse press)
	private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
	private BufferedImage painting;			// the resulting masterpiece

	/**
	 * Initializes the region finder and the drawing
	 */
	public PS1_CamPaint() {
		finder = new PS1_RegionFinder();
		clearPainting();
	}

	/**
	 * Resets the painting to a blank image
	 */
	protected void clearPainting() {
		painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * DrawingGUI method, here drawing one of live webcam, recolored image, or painting,
	 * depending on display variable ('w', 'r', or 'p')
	 */

	@Override
	public void draw(Graphics g) {
		// TODO: YOUR CODE HERE

		// draws a different, but appropriate image for each display mode

		if (displayMode == 'w') {
			g.drawImage(image, 0, 0, null);			// draws the webcame image

		} else if (displayMode == 'r') {
			g.drawImage(finder.getRecoloredImage(), 0, 0, null);		// draws the recolored image from the recolorImage method in RegionFinder class

		} else if (displayMode == 'p') {
			g.drawImage(painting, 0, 0, null);		// draws the painting of the movement of the selected region on the webcam image
		}
	}

			/**
             * Webcam method, here finding regions and updating the painting.
             */
	@Override
	public void processImage() {

		// TODO: YOUR CODE HERE
		if (targetColor != null) {// Checks if the targetColor is not empty

			finder.setImage(image);                            // Calls the setImage method in RegionFinder class
			finder.findRegions(targetColor);                // Calls the findRegions method in RegionFinder class
			finder.recolorImage();                            // Calls the recolorImage method in RegionFinder class

			ArrayList<Point> largestRegion = finder.largestRegion();        // Finds the largest region in the image shown by the webcam

			// Loops through each point in the largest region and resets its color
			for (int i = 0; i < largestRegion.size(); i++) {
				painting.setRGB(largestRegion.get(i).x, largestRegion.get(i).y, paintColor.getRGB());
			}
		}
	}


	/**
	 * Overrides the DrawingGUI method to set the track color.
	 */
	@Override
	public void handleMousePress(int x, int y) {
		// TODO: YOUR CODE HERE

		// Checks if the image is not null and then sets the targetColor to be the color of the image
		if (image != null) {
			targetColor = new Color(image.getRGB(x, y));
			System.out.println("tracking " + targetColor);
		}
	}

	/**
	 * DrawingGUI method, here doing various drawing commands
	 */
	@Override
	public void handleKeyPress(char k) {
		if (k == 'p' || k == 'r' || k == 'w') { // display: painting, recolored image, or webcam
			displayMode = k;
		}
		else if (k == 'c') { // clear
			clearPainting();
		}
		else if (k == 'o') { // save the recolored image
			saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
		}
		else if (k == 's') { // save the painting
			saveImage(painting, "pictures/painting.png", "png");
		}
		else {
			System.out.println("unexpected key "+k);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PS1_CamPaint();
			}
		});
	}
}
