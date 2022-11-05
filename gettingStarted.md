<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites - Guide

[![Drawing robot initial setup guide](https://img.youtube.com/vi/vAey5VbB7RE/0.jpg)](https://www.youtube.com/watch?v=vAey5VbB7RE)

This is an example of how to list things you need to use the software and how to install them.
1. Download and install VS Code. <a href="https://code.visualstudio.com/" target="_blank">Download here</a>
2. Download and install GitHub Desktop. <a href="https://desktop.github.com/" target="_blank">Download here</a>
3. Get Java up and running
   1. Create an empty folder on your desktop
   2. Open that folder with VS Code
   3. Create a file called `Test.java`
   4. VS Code should pop up with a notification to install `Java Extension Pack`. If not just search for it in the extension tab and install manually.
   5. Install JDK 14 when prompted by VS Code. If not prompted you can download and install it from <a href="https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html" target="_blank">here</a>
      1. Remember to tick on all the 4 settings when installing the JDK, else it will not run.
   6. Restart VS Code
   7. Try to run this code in the file `Test.java` by pressing the play button in the top right corner.
	```java
	public class Test {
		public static void main(String[] args) {
			System.out.println("Hello World!");
		}
	}
	```
4.  Now you can clone (download the files from the Repository) the repo down on your machine.
    1.  Open GitHub Desktop and go through the setup
    2.  Press clone repo
    3.  Select this repo: `Drawing-robot-semester-1`
    4.  Press open in `Visual Studio Code`
    5.  Create a new file in the first folder called `Test.java`
    6.  Commit this change by pressing the `Source Control tab` on the left side of VS Code 
    7.  Stage the change by pressing the plus sign
    8.  Write what you changed in the input field fx: `Added file Test.java`
    9.  Press `Ctrl + Enter` or the Check mark in the top of the panel to commit the change
        1.  You need to commit your changes every time you have added some functionality and tested that it works. This ensures that you always have a safe point to return to, if some of your code fails.
    10. Now you need to push your changes to the cloud.
        1.  Press the little circle of arrows in the bottom left corner of VS Code.
            1.  This will download possible changes in the cloud as well as pushing your changes to the cloud.
	11. You should be good to go from here.
