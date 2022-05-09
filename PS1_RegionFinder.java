import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 *
 * @author Chris Bailey-Kellogg, Winter 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 * @author CBK, Spring 2015, updated for CamPaint
 */
public class PS1_RegionFinder {
    private static final int maxColorDiff = 20;                // how similar a pixel color must be to the target color, to belong to a region
    private static final int minRegion = 50;                // how many points in a region to be worth considering

    private BufferedImage image;                            // the image in which to find regions
    private BufferedImage recoloredImage;                   // the image with identified regions recolored

    private ArrayList<ArrayList<Point>> regions;            // a region is a list of points
    // so the identified regions are in a list of lists of points

    public PS1_RegionFinder() {
        this.image = null;
    }

    public PS1_RegionFinder(BufferedImage image) {
        this.image = image;
    }


    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getRecoloredImage() {
        return recoloredImage;
    }

    /**
     * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
     */
    public void findRegions(Color targetColor) {
        // TODO: YOUR CODE HERE

        BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);  // Creates a new blank image to store the color of each visited pixel on the given image

        regions = new ArrayList<ArrayList<Point>>();        // Initializes an empty arraylist of points called regions

        // This for loop ensures that each pixel on the image is analyzed and that each qualified pixel is stored in the appropriate region of points.

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                ArrayList<Point> toVisit = new ArrayList<Point>();    // Initializes the toVisit arraylist to store all the relevant neighboring points to be visited
                ArrayList<Point> region = new ArrayList<Point>();   // Instantiates a new region to store visited points with a color similar to the target color


                if (visited.getRGB(x, y) == 0 && colorMatch(new Color(image.getRGB(x, y)), targetColor)) {// Checks if a pixel has never been visited before and has the target color
                    Point newPoint = new Point(x, y);   // Creates a new point object to store the pixel coordinate
                    toVisit.add(newPoint);      // Adds the pixel point to the toVisit arraylist

                    while (!toVisit.isEmpty()) {    // Checks if the toVisit arraylist is not empty
                        Point currPoint = toVisit.remove(toVisit.size() - 1);  // Removes the last pixel point from the toVisit arraylist and stores it as the point, currPoint

                        if (visited.getRGB(currPoint.x, currPoint.y) == 0) { // Checks if the pixel, currPoint, has never been visited
                            visited.setRGB(currPoint.x, currPoint.y, 1);    // Changes the color of currPoint
                            region.add(currPoint);      // Adds the currPoint to a region

                            // Checks the neighboring points of currPoint
                            for (int i = Math.max(0, currPoint.x - 1); i <= Math.min(image.getWidth()-1, currPoint.x + 1); i++) {
                                for (int j = Math.max(0, currPoint.y - 1); j <= Math.min(image.getHeight()-1, currPoint.y + 1); j++) {
                                    if (visited.getRGB(i, j) == 0 && colorMatch(new Color(image.getRGB(i, j)), targetColor)) {// Checks if this neighboring point has ever been visited before and has the target color

                                        Point neighPoint = new Point(i, j);
                                        toVisit.add(neighPoint);        // Adds the neighPoint to the toVisit arraylist so that it will be visited
                                    }

                                }
                            }
                        }
                    }
                }
                if (region.size() >= minRegion) {       // Checks if a region is big enough
                    regions.add(region);        // Adds the region to the regions arraylist
                }

            }

        }
    }

    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
     */
    private static boolean colorMatch(Color c1, Color c2) {
        // TODO: YOUR CODE HERE

        return Math.abs(c1.getRed() - c2.getRed()) <= maxColorDiff && Math.abs(c1.getGreen() - c2.getGreen()) <= maxColorDiff && Math.abs(c1.getBlue() - c2.getBlue()) <= maxColorDiff;

    }

    /**
     * Returns the largest region detected (if any region has been detected)
     */
    public ArrayList<Point> largestRegion() {
        // TODO: YOUR CODE HERE


        int biggerSize = 0;     // Initializes biggerSize to zero
        ArrayList<Point> bigRegion = new ArrayList<>();         // Instantiates arraylist, bigRegion, to store the biggest region in the regions arrayList.

        // Loops through all the regions in the regions arraylist and assigns the largest region of all to bigRegion arraylist.
        for(ArrayList<Point> region : regions){
            if(region.size()>biggerSize){
                biggerSize = region.size();         // Updates biggerSize to store the size a larger region
                bigRegion = region;                 // Updates bigRegion to store the region with size equal to biggerSize
            }
        }
        return bigRegion;
    }

    /**
     * Sets recoloredImage to be a copy of image,
     * but with each region a uniform random color,
     * so we can see where they are
     */
    public void recolorImage() {

        // First copy the original
        recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
        // Now recolor the regions in it
        // TODO: YOUR CODE HERE


        // Loops through the regions arrayList
        for (int i = 0; i < regions.size(); i++) {
            int v = (int) (16777216 * Math.random());    // Creates random integer v
            Color myColor = new Color(v); // Creates random color

            for (int k = 0; k < regions.get(i).size(); k++) {
                recoloredImage.setRGB(regions.get(i).get(k).x, regions.get(i).get(k).y, myColor.getRGB());      // Gives a random color to each pixel in each region in the regions arraylist in order to recolor each region with a specific color
            }
        }
    }

}


