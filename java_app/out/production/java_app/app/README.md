# Fixed errors in java files - What we did

## Deleted all the crap
We went in the folders for the java files and deleted all but the java files themselves.

## App folder
We created a folder called `app/` that will contain all of our java code.

### Packages
In the app folder we created two folders - one for each package.

- `edgedetect`
- `robclient`

These packages will contain your specified code for your assignment. For edgedetect the main file you will work on is `app/edgedetect/EdgeDetector.java`.

### Main file - `App.java`
We created a main file called `App.java` witch is where all of the Java code will be merged and used together.

## Programming workflow
When coding you will focus on creating your core methods in the packages and using/running them in the `App.java` file. An example is shown below:


#### `App.java`
```java
import robclient.RobotClient;
import edgedetect.EdgeDetector;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class App {
	public static void main(String[] args) {
		RobotClient client = new RobotClient("hostname", 5000);
		client.connect();
		if (client.isConnected())
			client.write("this is a message");

		EdgeDetector eDetector = new EdgeDetector("app/images/black.jpg");
		Color[][] array = eDetector.getGreyscaleArray();
		int white = 0;
		int black = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j].getRGB() == -1)
					white++;
				else
					black++;
			}
		}
		System.out.println("white: " + white);
		System.out.println("black: " + black);
	}
}

```