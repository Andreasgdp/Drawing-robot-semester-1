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

    /**
     * @param imagePath Path of the image to perform edgedetection on.
     */
    public EdgeDetector(String imagePath) {
        this.imagePath = imagePath;
        this.coordinates = null;
        this.sortedCoordinates = null;
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
     * constructor, and returns a two-dimensional int-array representation of the
     * result.
     *
     * @return A two dimensional array showing the magnitude (intensity) in each
     * pixel of the picture provided.
     */
    public int[][] getMagnitudeArray() {
        int[][] filter1 = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};

        int[][] filter2 = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};

        Picture picture0 = new Picture(imagePath);
        int width = picture0.width() - 2;
        int height = picture0.height() - 2;
        int[][] arrayRepresentation = new int[width][height];

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
                arrayRepresentation[x][y] = magnitude;
            }
        }

        return arrayRepresentation;
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
                int value = 100;
                if (picture0.get(x, y).getRed() <= value && picture0.get(x, y).getGreen() <= value
                        && picture0.get(x, y).getBlue() <= value)
                    pixelColor[y][x] = new Color(0);
                else
                    pixelColor[y][x] = new Color(255, 255, 255);
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
    public boolean loadCoordinates(Color[][] array) {
        ArrayList<ArrayList<ArrayList<Integer>>> colorPairs = new ArrayList<>();
        ArrayList<ArrayList<Integer>> plist = new ArrayList<>();
        ArrayList<Integer> coords = new ArrayList<>();
        boolean drawBlackColor = true;

        try {
            for (int y = 0; y < array.length; y++) {
                for (int x = 0; x < array[y].length; x++) {

                    if (drawBlackColor) {
                        if (array[y][x].getRed() == 0 && array[y][x].getBlue() == 0 && array[y][x].getGreen() == 0) {
                            coords.add(y);
                            coords.add(x);
                            plist.add(coords);
                            coords = new ArrayList<>();
                            drawBlackColor = false;
                        }

                    } else {
                        if (array[y][x].getRed() == 255 && array[y][x].getBlue() == 255
                                && array[y][x].getGreen() == 255) {
                            coords.add(y);
                            coords.add(x - 1);
                            plist.add(coords);
                            coords = new ArrayList<>();
                            colorPairs.add(plist);
                            plist = new ArrayList<>();
                            drawBlackColor = true;
                        } else if (x + 1 >= array[y].length) {
                            coords.add(y);
                            coords.add(x - 1);
                            plist.add(coords);
                            coords = new ArrayList<>();
                            colorPairs.add(plist);
                            plist = new ArrayList<>();
                            drawBlackColor = true;
                        }
                    }
                }
            }
            this.coordinates = colorPairs;
            return true;
        } catch (Exception e) {
            System.out.println("Cannot load coordinates of image. ERR: " + e);
            return false;
        }

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
    public void loadNewImage(String imgPath) {
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

    public ArrayList<ArrayList<ArrayList<Integer>>> getEdgeCords() {
        Color[][] array = this.getGreyscaleArray();
        this.loadCoordinates(array);
        if (!(this.coordinates == null)) {
            return this.coordinates;
        } else {
            return null;
        }
    }

    public ArrayList<Point> getSortedCords() {
        Color[][] array = this.getColorArray();
        this.loadSortedCoordinates(array);
        if (!(this.sortedCoordinates == null)) {
            return this.sortedCoordinates;
        } else {
            return null;
        }
    }

    public ArrayList<Point> convertCordsToPoints(Color[][] array) {
        ArrayList<Point> pointList = new ArrayList<>();
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[y].length; x++) {
                if (array[y][x].getRed() == 0 && array[y][x].getBlue() == 0 && array[y][x].getGreen() == 0) {
                    pointList.add(new Point(x, y));
                }
            }
        }
        return pointList;
    }

    private void loadSortedCoordinates(Color[][] array) {
        ArrayList<Point> myList = this.convertCordsToPoints(array);
        System.out.println(myList.size());

        ArrayList<Point> orderedList = new ArrayList<Point>();

        orderedList.add(myList.remove(0)); //Arbitrary starting point

        while (myList.size() > 0) {
            //Find the index of the closest point (using another method)
            int nearestIndex = findNearestIndex(orderedList.get(orderedList.size() - 1), myList);

            //Remove from the unorderedList and add to the ordered one
            orderedList.add(myList.remove(nearestIndex));
        }

        System.out.println(orderedList.size());
        this.sortedCoordinates = orderedList;
    }

    private int findNearestIndex(Point thisPoint, ArrayList<Point> listToSearch) {
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
        return nearestIndex;
    }
}