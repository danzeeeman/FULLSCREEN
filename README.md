#FULLSCREEN
###Curated by Aram Bartholl
###March 13 – April 4, 2014
###XPO gallery, Paris


* Participating artists:
* Vincent Broquaire,
* Jennifer Chan,
* Petra Cortright,
* Constant Dullaart,
* Oliver Laric,
* Sara Ludy,
* Raquel Meyers,
* Evan Roth,
* Rafaël Rozendaal,
* Paul Souviron,
* Addie Wagenknecht,
* Ai Weiwei


##The _How to Build_ Guide


OK so you need the [Android Development Tools Bundle for your chosen OS.](http://developer.android.com/sdk/index.html#download)



###Import The FULLSCREEN Project 

![][1]

![][2]

![][3]

![][4]


### Change Strings.xml and Copy Your Media
Change the values in Strings.xml to match your file names.  
Note you should only use a-z0-9 or lowercase alphanumeric file names without spaces or special chars.
For Video please DO NOT list the file extension in the Strings.xml file. 
Strings.xml is located in ```res/values```
![][5]

You should also update Strings.xml to reflect your new activity you wish to Auto Boot. 

Add your media to either ```assets/gifs``` for .Gif, ```assets/www``` for HTML,  or ```res/raw``` for Video. 

![][6]

![][7]



### Change Your AndroidManifest.xml
Edit the Manifest file to refelect the media you added.
![][8]

### For VIDEO.java
![][9]

### For GIF.java
![][3]


### Hit Play
![][9]
 

 
  [1]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/Step-One-Import.png
  [2]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/Step-One.a-import.png
  [3]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/Step-One.b-import.png
  [4]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/Step-One.c-import.png
  [5]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/StepTwoChangeStrings.png
  [6]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/ImportGifWWW.png
  [7]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/ImportVideo.png
  [8]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/Step-Four-ChangeManifest.png
  [9]: https://raw.github.com/danthemellowman/FULLSCREEN/master/notes/%20Step-Five-HIT-PLAY.png
