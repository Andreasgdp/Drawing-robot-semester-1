package edgedetect;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author ancla
 */
public class EdgeDetector {

    private String imagePath;
    private ArrayList<ArrayList<ArrayList<Integer>>> coordinates;

    /**
     * 
     * @param imagePath Path of the image to perform edgedetection on.
     */
    public EdgeDetector(String imagePath) {
        this.imagePath = imagePath;
        this.coordinates = null;
    }

    private int truncate(int a) {
        if (a < 127) {
            return 0;
        } else if (a >= 255) {
            return 255;
        } else {
            return a;
        }
    }

    private int binaryTruncate(int a) {
        if (a < 127) {
            return 0;
        } else {
            return 255;
        }
    }

    /**
     * This method performs edge-detection of the image on the path provided to the
     * constructor, and returns a BufferedImage representation of the result.
     * 
     * @return A BufferedImage-object representation of the image provided.
     */
    public BufferedImage getBufferedImage() {

        int[][] filter1 = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };

        int[][] filter2 = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

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
     *         pixel of the picture provided.
     */
    public int[][] getMagnitudeArray() {
        int[][] filter1 = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };

        int[][] filter2 = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

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
     *         interpretation of the image.
     */
    public Color[][] getGreyscaleArray() {
        int[][] filter1 = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };

        int[][] filter2 = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

        Picture picture0 = new Picture(imagePath);
        // Find width, removing outer border due to filter
        int width = picture0.width() - 2;
        int height = picture0.height() - 2;
        Color[][] arrayRepresentation = new Color[width][height];

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
                arrayRepresentation[x][y] = grayscale;
            }
        }

        return arrayRepresentation;
    }

    public Color[][] getColorArray() {
        Picture picture0 = new Picture(imagePath);
        // Find width, removing outer border due to filter
        int width = picture0.width();
        int height = picture0.height();
        Color[][] pixelColor = new Color[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = 230;
                if (picture0.get(x, y).getRed() <= value && picture0.get(x, y).getGreen() <= value
                        && picture0.get(x, y).getBlue() <= value)
                    pixelColor[x][y] = new Color(0);
                else
                    pixelColor[x][y] = new Color(255, 255, 255);
            }
        }
        return pixelColor;
    }

    public boolean loadCoordinates(Color[][] array) {
        ArrayList<ArrayList<ArrayList<Integer>>> colorPairs = new ArrayList<ArrayList<ArrayList<Integer>>>();
        ArrayList<ArrayList<Integer>> plist = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> coords = new ArrayList<Integer>();
        boolean drawBlackColor = true;

        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                if (drawBlackColor) {
                    if (array[x][y].getRed() == 0 && array[x][y].getBlue() == 0 && array[x][y].getGreen() == 0) {
                        coords.add(x);
                        coords.add(y);
                        coords = new ArrayList<Integer>();
                        plist.add(coords);
                        drawBlackColor = false;
                    }

                } else {
                    if (array[x][y].getRed() == 255 && array[x][y].getBlue() == 255 && array[x][y].getGreen() == 255) {
                        coords.add(x - 1);
                        coords.add(y);
                        coords = new ArrayList<Integer>();
                        plist.add(coords);
                        colorPairs.add(plist);
                        plist = new ArrayList<ArrayList<Integer>>();
                        drawBlackColor = true;
                    }
                }
            }
        }
        this.coordinates = colorPairs;
        // TODO: Add method to check if coords are loaded.
        return true;
    }

    public ArrayList<ArrayList<ArrayList<Integer>>> getCoordinates() {
        return this.coordinates;
    }

    public boolean loadNewImage(String imgPath) {
        this.imagePath = imgPath;
        // TODO: Add method to check if path exists.
        return true;
    }

    public ArrayList<ArrayList<ArrayList<Integer>>> getPairCoords(Color[][] array) {
        ArrayList<ArrayList<ArrayList<Integer>>> colorPairs = new ArrayList<ArrayList<ArrayList<Integer>>>();
        ArrayList<ArrayList<Integer>> plist = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> coords = new ArrayList<Integer>();
        boolean colorSwitch = true;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (colorSwitch) {
                    if (array[i][j].getRed() == 0 && array[i][j].getBlue() == 0 && array[i][j].getGreen() == 0) {
                        coords.add(i);
                        coords.add(j);
                        coords = new ArrayList<Integer>();
                        plist.add(coords);
                        colorSwitch = false;
                    }

                } else {
                    if (array[i][j].getRed() == 255 && array[i][j].getBlue() == 255 && array[i][j].getGreen() == 255) {
                        coords.add(i - 1);
                        coords.add(j - 1);
                        coords = new ArrayList<Integer>();
                        plist.add(coords);
                        colorPairs.add(plist);
                        plist = new ArrayList<ArrayList<Integer>>();
                        colorSwitch = true;
                    }
                }
            }
        }
        return colorPairs;
    }
}