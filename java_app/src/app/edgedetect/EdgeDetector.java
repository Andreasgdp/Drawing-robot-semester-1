package app.edgedetect;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ancla
 */
public class EdgeDetector {

    private String imagePath;
    private ArrayList<ArrayList<ArrayList<Integer>>> coordinates;
    private ArrayList<Point> sortedCoordinates;
    private ArrayList<ArrayList<Point>> greyLineCoordinates;

    /**
     * @param imagePath Path of the image to perform edgedetection on.
     */
    public EdgeDetector(String imagePath) {
        this.imagePath = imagePath;
        this.coordinates = null;
        this.sortedCoordinates = null;
        this.greyLineCoordinates = null;
    }

    public String getImagePath() {
        return imagePath;
    }

    private int truncate(int a) {
        if (a < 127) {
            return 0;
        } else return Math.min(a, 255);
    }

    /**
     * This method performs edge-detection of the image on the path provided to the
     * constructor, and returns a BufferedImage representation of the result.
     *
     * @return A BufferedImage-object representation of the image provided.
     */
    public BufferedImage getBufferedImage() {

        int[][] filter1 = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};

        int[][] filter2 = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        Picture picture0 = new Picture(imagePath);
        int width = picture0.width() - 2;
        int height = picture0.height() - 2;
        Picture picture1 = new Picture(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // get 3-by-3 array of colors in neighborhood
                int[][] gray = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gray[i][j] = (int) Luminance.intensity(picture0.get(x + i, y + j));
                    }
                }

                // apply filter
                int gray1 = 0, gray2 = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gray1 += gray[i][j] * filter1[i][j];
                        gray2 += gray[i][j] * filter2[i][j];
                    }
                }
                // int magnitude = 255 - truncate(Math.abs(gray1) + Math.abs(gray2));
                int magnitude = 255 - truncate((int) Math.sqrt(gray1 * gray1 + gray2 * gray2));
                Color grayscale = new Color(magnitude, magnitude, magnitude);
                picture1.set(x, y, grayscale);

            }
        }

        return picture1.getImage();
    }

    /**
     * This method performs edge-detection of the image on the path provided to the
     * constructor, and returns a two-dimensional Color-array representation of the
     * result.
     *
     * @return A two-dimensional array with Color objects, representing a greyscale
     * interpretation of the image.
     */
    public Color[][] getGreyscaleArray() {
        int[][] filter1 = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};

        int[][] filter2 = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};

        Picture picture0 = new Picture(imagePath);
        // Find width, removing outer border due to filter
        int width = picture0.width() - 2;
        int height = picture0.height() - 2;
        Color[][] arrayRepresentation = new Color[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // get 3-by-3 array of colors in neighborhood
                int[][] gray = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gray[i][j] = (int) Luminance.intensity(picture0.get(x + i, y + j));
                    }
                }

                // apply filter
                int gray1 = 0, gray2 = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gray1 += gray[i][j] * filter1[i][j];
                        gray2 += gray[i][j] * filter2[i][j];
                    }
                }
                // int magnitude = 255 - truncate(Math.abs(gray1) + Math.abs(gray2));
                int magnitude = 255 - truncate((int) Math.sqrt(gray1 * gray1 + gray2 * gray2));
                Color grayscale = new Color(magnitude, magnitude, magnitude);
                arrayRepresentation[y][x] = grayscale;
            }
        }

        return arrayRepresentation;
    }

    /**
     * This method runs through an image and creates a 2D array representation of
     * the colors in the image.
     *
     * @return A two-dimensional array with Color objects, representing a the image.
     */
    public Color[][] getColorArray() {
        Picture picture0 = new Picture(imagePath);
        // Find width, removing outer border due to filter
        int width = picture0.width();
        int height = picture0.height();
        Color[][] pixelColor = new Color[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // int value = 230;
                int value = 200;
                if (picture0.get(x, y).getRed() <= value && picture0.get(x, y).getGreen() <= value
                        && picture0.get(x, y).getBlue() <= value)
                    pixelColor[y][x] = new Color(0);
                else
                    pixelColor[y][x] = new Color(255, 255, 255);
            }
        }
        return pixelColor;
    }

    public Color[][] getRealColorArray() {
        Picture picture0 = new Picture(imagePath);
        // Find width, removing outer border due to filter
        int width = picture0.width();
        int height = picture0.height();
        Color[][] pixelColor = new Color[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelColor[y][x] = new Color(picture0.get(x, y).getRed(), picture0.get(x, y).getGreen(), picture0.get(x, y).getBlue());
            }
        }
        return pixelColor;
    }

    /**
     * This method runs through a 2D representation of an image and saves
     * coordinates in pairs: For each y-coordinate and each black line in said
     * y-coordinate, 2 points are saved - one for the beginning of the black line
     * and one for the end.
     *
     * @param array Type: Color[][] - A two-dimensional array with Color objects,
     *              representing a the image.
     */
    public void loadCoordinates(Color[][] array) {
        ArrayList<ArrayList<ArrayList<Integer>>> colorPairs = new ArrayList<>();
        ArrayList<ArrayList<Integer>> plist = new ArrayList<>();
        ArrayList<Integer> coords = new ArrayList<>();
        boolean drawBlackColor = true;

        for (int y = 0; y < array.length; y++) {

            boolean direction = y % 2 == 0;
            if (direction) {
                for (int x = 0; x < array[y].length; x++) {
                    // System.out.println(array[y][x]);
                    if (drawBlackColor) {
                        if (array[y][x].getRed() == 0) {
                            coords.add(y);
                            coords.add(x);
                            plist.add(coords);
                            coords = new ArrayList<>();
                            drawBlackColor = false;
                        }

                    } else {

                        if ((array[y][x].getRed() == 255) || ((x + 1) >= array[y].length)) {
                            coords.add(y);
                            if ((x + 1) >= array[y].length) {
                                coords.add(x);
                            } else {
                                coords.add(x - 1);
                            }
                            plist.add(coords);
                            coords = new ArrayList<>();
                            colorPairs.add(plist);
                            plist = new ArrayList<>();
                            drawBlackColor = true;
                        }
                    }
                }
            } else {
                for (int x = array[y].length - 1; x > -1; x--) {
                    if (drawBlackColor) {
                        if (array[y][x].getRed() == 0) {
                            coords.add(y);
                            coords.add(x);
                            plist.add(coords);
                            coords = new ArrayList<>();
                            drawBlackColor = false;
                        }

                    } else {

                        if ((array[y][x].getRed() == 255) || (x == 0)) {
                            coords.add(y);
                            if (x == 0) {
                                coords.add(x);
                            } else {
                                coords.add(x + 1);
                            }
                            plist.add(coords);
                            coords = new ArrayList<>();
                            colorPairs.add(plist);
                            plist = new ArrayList<>();
                            drawBlackColor = true;
                        }
                    }
                }
            }
        }
        // TODO: Add method to check if coords are loaded.
        this.coordinates = colorPairs;
    }


    public void loadGreyCoordinates(Color[][] array) {
        ArrayList<ArrayList<Point>> greyPairs = new ArrayList<>();
        ArrayList<Point> plist = new ArrayList<>();

        for (int y = 0; y < array.length; y++) {
            boolean direction = y % 2 == 0;
            if (direction) {
                for (int x = 0; x < array[y].length; x++) {
                    Point pixel = this.convertPixelToPoint(x, y, array[y][x]);
                    if (pixel.drawVal != 5) {
                        if (plist.isEmpty()) {
                            plist.add(pixel);
                        } else { // Not empty
                            if ((plist.get(0).drawVal != pixel.drawVal)) {
                                if (x == 0) {
                                    plist.add(this.convertPixelToPoint(x, y - 1, array[y - 1][x]));
                                } else {
                                    plist.add(this.convertPixelToPoint(x - 1, y, array[y][x - 1]));
                                }
                                greyPairs.add(plist);
                                plist = new ArrayList<>();
                                plist.add(pixel);
                            } else {
                                if (x == array[y].length - 1) {
                                    plist.add(pixel);
                                    greyPairs.add(plist);
                                    plist = new ArrayList<>();
                                } else if (plist.get(0).y != pixel.y) {
                                    plist.add(plist.get(0));
                                    greyPairs.add(plist);
                                    plist = new ArrayList<>();
                                    plist.add(pixel);
                                }
                            }
                        }
                    } else {
                        if (!(plist.isEmpty())) {
                            if (plist.get(0).y == y) {
                                plist.add(this.convertPixelToPoint(x - 1, y, array[y][x - 1]));
                            } else {
                                plist.add(plist.get(0));
                            }
                            greyPairs.add(plist);
                            plist = new ArrayList<>();
                        }
                    }
                }
            } else {
                for (int x = array[y].length - 1; x > -1; x--) {
                    Point pixel = this.convertPixelToPoint(x, y, array[y][x]);

                    if (pixel.drawVal != 5) {
                        if (plist.isEmpty()) {
                            plist.add(pixel);
                        } else { // Not empty
                            if ((plist.get(0).drawVal != pixel.drawVal)) {
                                if (x == array[y].length - 1) {
                                    plist.add(this.convertPixelToPoint(x - 1, y, array[y - 1][x]));
                                } else {
                                    plist.add(this.convertPixelToPoint(x + 1, y, array[y][x + 1]));
                                }
                                greyPairs.add(plist);
                                plist = new ArrayList<>();
                                plist.add(pixel);
                            } else {
                                if (x == 0) {
                                    plist.add(pixel);
                                    greyPairs.add(plist);
                                    plist = new ArrayList<>();
                                } else if (plist.get(0).y != pixel.y) {
                                    plist.add(plist.get(0));
                                    greyPairs.add(plist);
                                    plist = new ArrayList<>();
                                    plist.add(pixel);
                                }
                            }
                        }
                    } else {
                        if (!(plist.isEmpty())) {
                            if (plist.get(0).y == y) {
                                plist.add(this.convertPixelToPoint(x + 1, y, array[y][x + 1]));
                            } else {
                                plist.add(plist.get(0));
                            }
                            greyPairs.add(plist);
                            plist = new ArrayList<>();
                        }
                    }
                }
            }
        }

        ArrayList<Point> tempPoint = new ArrayList<>();
        for (int i = 0; i < greyPairs.size(); i++) {
            for (int j = i - 1; j > 0; j--) {
                int middelPointI = (greyPairs.get(i).get(0).x + greyPairs.get(i).get(1).x) / 2;
                int middelPointJ = (greyPairs.get(j).get(0).x + greyPairs.get(j).get(1).x) / 2;
                if ((greyPairs.get(j).get(0).y == (greyPairs.get(i).get(0).y - 1)) &&
                        (greyPairs.get(j).get(0).drawVal == greyPairs.get(i).get(0).drawVal) &&
                        (((middelPointJ >= greyPairs.get(i).get(0).x) && (middelPointJ <= greyPairs.get(i).get(1).x)) ||
                                ((middelPointI >= greyPairs.get(j).get(0).x) && (middelPointI <= greyPairs.get(j).get(1).x)))) {

                    tempPoint = greyPairs.get(i);
                    for (int k = i; k > (j + 1); k--) {
                        greyPairs.set(k, greyPairs.get(k - 1));
                    }
                    greyPairs.set((j + 1), tempPoint);
                }
            }
        }
        this.greyLineCoordinates = greyPairs;
    }

    /**
     * This method returns the already prepared coordinates by method:
     * loadCoordinates().
     *
     * @return An ArrayList of paired coordinates.
     */
    public ArrayList<ArrayList<ArrayList<Integer>>> getCoordinates() {
        Color[][] array = this.getColorArray();
        this.loadCoordinates(array);
        if (!(this.coordinates == null)) {
            return this.coordinates;
        } else {
            return null;
        }
    }

    /**
     * This method takes an image path and checks if that path is valid. If the path
     * is valid the image path is set to be the default image path for the class.
     *
     * @param imgPath String of the image path.
     */
    public void loadNewImagePath(String imgPath) {
        Scanner CMDscanner = new Scanner(System.in);
        File aFile = new File(imgPath);

        if (aFile.exists()) {
            System.out.println("file exists");
            this.imagePath = imgPath;
        } else {
            System.out.println("image dosnt exist in folder: app/images");
            System.out.println("do you wish to select an alternative path (Direct path)?");
            String diffPath = CMDscanner.nextLine();

            if (diffPath.startsWith("y")) {
                System.out.println("enter alternative image path:");
                String alPath = CMDscanner.nextLine();
                File alFile = new File(alPath);

                if (alFile.exists()) {
                    System.out.println("file exists");
                    this.imagePath = imgPath;
                } else {
                    System.out.println("file dosn't exist");
                }
            }
        }
    }

    public void loadNewImage(String imgName) {
        String imgPath = "../images/";
        this.imagePath = imgPath + imgName;
    }

    public ArrayList<ArrayList<ArrayList<Integer>>> getEdgeCords() {
        Color[][] array = this.getGreyscaleArray();
        this.loadCoordinates(array);
        if (!(this.coordinates == null)) {
            return this.coordinates;
        } else {
            return null;
        }
    }

    public ArrayList<Point> getSortedEdgeCords() {
        Color[][] array = this.getGreyscaleArray();
        ArrayList<Point> pointArray = this.convertCordsToPoints(array);

        ArrayList<Point> orderedList = new ArrayList<>();

        orderedList.add(pointArray.remove(0)); //Arbitrary starting point
        int liftCounter = 0;
        while (pointArray.size() > 0) {
            //Find the index of the closest point (using another method)
            IndexDist nearestIndexDist = findNearestIndex(orderedList.get(orderedList.size() - 1), pointArray);

            if (nearestIndexDist.dist > 5) {
                pointArray.get(nearestIndexDist.index).setDrawVal(5);
                liftCounter++;
            }
            //Remove from the unorderedList and add to the ordered one
            orderedList.add(pointArray.remove(nearestIndexDist.index));
        }
        System.out.println("Lifts needed to draw this image: " + liftCounter);

        return orderedList;
    }

    public ArrayList<Point> getSortedCords() {
        Color[][] array = this.getRealColorArray();
        this.loadSortedCoordinates(array);
        if (!(this.sortedCoordinates == null)) {
            return this.sortedCoordinates;
        } else {
            return null;
        }
    }

    public ArrayList<Point> getSortedCordsBH() {
        Color[][] array = this.getColorArray();
        this.loadSortedCoordinates(array);
        for (Point sortedCoordinate : this.sortedCoordinates) {
            if (sortedCoordinate.drawVal == 0) {
                sortedCoordinate.setDrawVal(3);
            }
        }
        if (!(this.sortedCoordinates == null)) {
            return this.sortedCoordinates;
        } else {
            return null;
        }
    }

    public ArrayList<Point> convertCordsToPoints(Color[][] array) {
        ArrayList<Point> pointList = new ArrayList<>();
        int scales = 6;
        int devider = 256 / scales;
        int[] counter = new int[scales + 1];

        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[y].length; x++) {
                int ggb = (((array[y][x].getRed() + array[y][x].getBlue() + array[y][x].getGreen() + 3) / 3) + devider) / devider - 1;

                counter[ggb]++;

                // Tager ikke ggb == 5 med fordi det er hvide koordinater, som ikke skal tegnes.
                if (ggb < scales - 1) {
                    Point point = new Point(x, y);
                    point.setDrawVal(ggb);
                    pointList.add(point);
                }
            }
        }
        int pixelCounter = 0;
        for (int i = 0; i < counter.length - 2; i++) {
            System.out.println(i + ": " + counter[i]);
            pixelCounter += counter[i];
        }
        System.out.println(pixelCounter);

        return pointList;
    }

    public Point convertPixelToPoint(int x, int y, Color pixel) {
        Point point = null;
        int scales = 6;
        int devider = 256 / scales;
        int ggb = (((pixel.getRed() + pixel.getBlue() + pixel.getGreen() + 3) / 3) + devider) / devider - 1;
        // Tager ikke ggb == 5 med fordi det er hvide koordinater, som ikke skal tegnes.
        if (ggb < scales - 1) {
            point = new Point(x, y);
            point.setDrawVal(ggb);
        } else {
            point = new Point(x, y);
            point.setDrawVal(5);
        }
        return point;
    }

    private void loadSortedCoordinates(Color[][] array) {
        ArrayList<Point> myList = this.convertCordsToPoints(array);
        ArrayList<Point> orderedList = new ArrayList<>();

        orderedList.add(myList.remove(0)); //Arbitrary starting point
        int liftCounter = 0;
        while (myList.size() > 0) {
            //Find the index of the closest point (using another method)
            IndexDist nearestIndexDist = findNearestIndex(orderedList.get(orderedList.size() - 1), myList);

            if (nearestIndexDist.dist > 5) {
                myList.get(nearestIndexDist.index).setDrawVal(5);
                liftCounter++;
            }
            //Remove from the unorderedList and add to the ordered one
            orderedList.add(myList.remove(nearestIndexDist.index));
        }
        System.out.println("Lifts needed to draw this image: " + liftCounter);
        this.sortedCoordinates = orderedList;
    }

    private IndexDist findNearestIndex(Point thisPoint, ArrayList<Point> listToSearch) {
        double nearestDistSquared = Double.POSITIVE_INFINITY;
        int nearestIndex = -1;
        for (int i = 0; i < listToSearch.size(); i++) {
            Point point2 = listToSearch.get(i);
            double distSQ = (thisPoint.x - point2.x) * (thisPoint.x - point2.x)
                    + (thisPoint.y - point2.y) * (thisPoint.y - point2.y);
            if (distSQ < nearestDistSquared) {
                nearestDistSquared = distSQ;
                nearestIndex = i;
            }
        }
        return new IndexDist(nearestIndex, nearestDistSquared);
    }

    public ArrayList<ArrayList<Point>> getGreyLineCoordinates() {
        Color[][] array = this.getRealColorArray();
        this.loadGreyCoordinates(array);
        if (!(this.greyLineCoordinates == null)) {
            return this.greyLineCoordinates;
        } else {
            return null;
        }
    }
}