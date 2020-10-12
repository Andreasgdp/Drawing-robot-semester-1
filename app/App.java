import robclient.RobotClient;
import edgedetect.EdgeDetector;
import edgedetect.Picture;
import java.util.*;

import java.awt.image. BufferedImage;
import java.awt.Color;

public class App {
	public static void main(String[] args) {

		EdgeDetector eDetector = new EdgeDetector("app/images/black.jpg");
		int black = 0;
		int white = 0;

		Color[][] testColor =  eDetector.getColorArray();
		for (int x = 0; x < testColor.length; x++) {
			for (int y = 0; y < testColor[x].length; y++){
				if (testColor[x][y].getRed() == 0 && testColor[x][y].getGreen() == 0 && testColor[x][y].getBlue() == 0){
					black++;

				}
				else
				{
					white++;

				}

				System.out.println(testColor[x][y]);
				

			}
		}
		System.out.println("Black " + black);
		System.out.println("White " + white);

		ArrayList<ArrayList<ArrayList<Integer> > > coordinates = eDetector.colorPair(testColor);
			for ( int i = 0; i < coordinates.size; i++) {
				for (int j = 0; j < coordinates.size; j++) {
					for (int j2 = 0; j2 < coordinates.size; j2++) {
						
						System.out.println(coordinates.get(i).get(j).get(j2));
					}
				}
			}

		
		// EdgeDetector eDetector = new EdgeDetector("app/images/small_sandwitch.jpg");
		// BufferedImage test = eDetector.getBufferedImage();
		// System.out.println(test);
		//
		// int[][] testMag = eDetector.getMagnitudeArray();
		// for (int i = 0; i < testMag.length; i++) {
		// 	for (int j = 0; j < testMag[i].length; j++) {
		//				
		// 		System.out.println(testMag[i][j]);
		// 	}
		// }
		//
		// Color[][] testGrey =  eDetector.getGreyscaleArray();
		// for (int i = 0; i < testGrey.length; i++) {
		// 	for (int j = 0; j < testGrey[i].length; j++){
		//
		// 		System.out.println(testGrey[i][j]);
		// 	}
		// }


	}
}
