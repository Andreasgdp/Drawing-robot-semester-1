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
