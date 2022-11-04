<!--
*** Thanks for checking out this README Template. If you have a suggestion that would
*** make this better, please fork the repo and create a pull request or simply open
*** an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
***
***
***
*** To avoid retyping too much info. Do a search and replace for the following:
*** andreasgdp, Drawing-robot-semester-1, AndreasGuldberg, andreasgdp@gmail.com
-->





<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]





<!-- ABOUT THE PROJECT -->
## About The Project
<p align="center"> 
<img src="https://user-images.githubusercontent.com/39928082/200000349-7032717f-b081-4ab3-b343-85bd94cfe196.gif" alt="SDU" title="SDU" width="80%" height="80%"/> 
</p>

### Abstract
In this project, we worked with processing and drawing images. We have for the treatment
of images used Java to obtain coordinates that our drawing robot has received. We
has then set up a number of requirements that our drawing robot has had to comply with. To confirm that
these have been complied with, we have carried out a series of tests and benchmarks. Based on these have
we made statistics and have come to the conclusion that it is generally fastest to draw with lines, if it
is a black and white image, but if it's greyscale, it's faster to draw it sorted by
our own sorting algorithm. We also found that complexity increases the print time for our sorted
coordinates. We have achieved to draw pictures in 4 ways, draw greyscale with 6 shades, automatically
pencil sharpening and simulating the drawings in Java. The processed images are sent through
a TCP server to a PLC that controls the robot and can be controlled through a GUI. Our goal with
the project was to draw a nice drawing as quickly as possible and this has been achieved.

<p align="center"> 
<img src="https://user-images.githubusercontent.com/39928082/199998276-67d2aa06-8f68-43ae-b4f2-bddfe8cf1543.png" alt="SDU" title="SDU" width="80%" height="80%"/> 
</p>

### Built With

* [Java]()
* [PLC script]()
* [Pure manpower]()



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


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/andreasgdp/Drawing-robot-semester-1.svg?style=flat-square
[contributors-url]: https://github.com/andreasgdp/Drawing-robot-semester-1/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/andreasgdp/Drawing-robot-semester-1.svg?style=flat-square
[forks-url]: https://github.com/andreasgdp/Drawing-robot-semester-1/network/members
[stars-shield]: https://img.shields.io/github/stars/andreasgdp/Drawing-robot-semester-1.svg?style=flat-square
[stars-url]: https://github.com/andreasgdp/Drawing-robot-semester-1/stargazers
[issues-shield]: https://img.shields.io/github/issues/andreasgdp/Drawing-robot-semester-1.svg?style=flat-square
[issues-url]: https://github.com/andreasgdp/Drawing-robot-semester-1/issues
[license-shield]: https://img.shields.io/github/license/andreasgdp/Drawing-robot-semester-1.svg?style=flat-square
[license-url]: https://github.com/andreasgdp/Drawing-robot-semester-1/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/andreas-g-d-petersen-11707518b/
[product-screenshot]: images_readme/robot_arm.jpg
