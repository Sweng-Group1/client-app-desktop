# client-app-desktop
The desktop client-side application for SwEng Group 1

# General Plan
Within the home folder of the repository, there is a UML Class diagram outlining the structure of the application.
This has been broken down into separate packages and views, allowing the development of each to be mostly indendent, allowing easier allocation of separate jobs

# Libraries to Use
All of the external libraries will be installed using maven, which is integrated within eclipse 
## General Graphics
### java.swing 
- We have all used this library
- it is native
- easily reskinned to look how we want it to.

## Audio/Video
### [jvlc](https://github.com/caprica/vlcj)
- Stuart recommended it during a lecture 
- Works within swing 
- There is minimal code needed to playback video and audio - see [here](https://www.tutorialspoint.com/vlcj/)

## Mapping
### [Mapsforge](https://github.com/mapsforge/mapsforge)
- works within swing
- documentation is kinda ass but is easy enough to work with
- it's internal POI system doesn't have to be installed, allowing us to easily implement our own
