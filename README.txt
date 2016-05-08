*****The file is titled Vertical Prototype, however, it is FAR beyond the original Prototype, which was only the mainActivity screen with the createfile button.  This is a fully-functional Alpha version of the project.*****

This android file assumes that you have music files on your device.  Currently, I do not know how it will react if your testing environment, (emulator or actual phone), lacks music files.

This android file assumes you have created at least one (1) playlist on your device.  If none are created, then when you click on 'GO TO GET PLAYLISTS', the spinner will be an empty set, and you will be unable to move to the confirmation screen ((Expected crash.))

This android file does NOT move songs to your computer.  It does not automatically transfer ANY data whatsoever.  Files must be manually moved.

To access the structure of the .m3u playlist, it is reccommended that you right click on the file, when transferred onto the computer, and open it in notepad, or its equivalent.  If you cannot, then do the following:
--Go to the 'confirmToWritePlaylist.java' file, and go to line 114
--Change "/CreatedPlaylist.m3u" to "/CreatedPlaylist.txt"

System.out.println() statements were used for debugging; they will be removed in the final implementation of the code.