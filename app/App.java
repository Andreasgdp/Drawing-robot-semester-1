import robclient.RobotClient;
import edgedetect.EdgeDetector;
import edgedetect.Picture;

import java.awt.image. BufferedImage;
import java.awt.Color;

public class App {
	public static void main(String[] args) {

		BufferedImage bi = ImageIO.read(new File("app/images/small_sandwitch.jpg"));
		int[] data = ( (DataBufferInt) bi.getRaster().getDataBuffer() ).getData();
		for ( int i = 0 ; i < data.length ; i++) {
			Color c = new Color(data[i]);

			c.getRed();
			c.getGreen();
			c.getBlue();

		}


		// EdgeDetector eDetector = new EdgeDetector("app/images/small_sandwitch.jpg");
		// BufferedImage test = eDetector.getBufferedImage();
		// System.out.println(test);

		// int[][] testMag = eDetector.getMagnitudeArray();
		// for (int i = 0; i < testMag.length; i++) {
		// 	for (int j = 0; j < testMag[i].length; j++) {
				
		// 		System.out.println(testMag[i][j]);
		// 	}
		// }

		// Color[][] testGrey =  eDetector.getGreyscaleArray();
		// for (int i = 0; i < testGrey.length; i++) {
		// 	for (int j = 0; j < testGrey[i].length; j++){

		// 		System.out.println(testGrey[i][j]);
		// 	}
		// }


	}
}
