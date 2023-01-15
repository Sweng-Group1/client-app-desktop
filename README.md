# client-app-desktop
The desktop client-side application for SwEng Group 1

# General Plan
Within the home folder of the repository, there is a UML Class diagram outlining the structure of the application.
This has been broken down into separate packages and views, allowing the development of each to be mostly indendent, allowing easier allocation of separate jobs

# UML Class Diagram
![Diagram](./docs/MMPE-UML-Diagram.svg)

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

# Presentation Environment
Each element within a slide will extend JComponent. We have agreed that the slide component will run on a point coordinate system (e.g no matter the resolution of a screen/size of the window, the elements will stay in the same place and same size relative to each other). This means that within the paint method of each element to be developed the point information will have to be scaled to pixels (Component.setSize & Component.setLocation() will be usefull for this).

# Outsourcing
The module specification requires that we outsource 2 of the modules; we are going to outsource Graphics and Text. This allows us to focus on the media (images, video and audio). This decision has been made as these components will eventually downloading media from our server.
