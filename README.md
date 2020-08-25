# pr-tech-test

Known issues :
*  If the image is too big, in size, the server returns a 500 or timeout, for now it’s handled by a Toast asking for a smaller image. 

Some explanations : 
* The app is just one Activity, no fragment. 
* I made this choice because it’s a really small app, no need to add complexity on it with managing Fragments 
* All the app is in Kotlin, using Retrofit for network request and Rx for asynchronous calls
* I haven’t changed the app icon, and the icon used in the app is a default icon on Android    

Some improvements? 
* Get the image from the gallery and process it to Base64 in a thread to not lock the UI Thread 
* To use ViewBinding instead of findViewById 
* To use coroutines instead of Rx (I could have used it for that project, but I’m more used to Rx, with the time I had, I preferred to use Rx) 
* To add some tests
* To have the possibility to download the final image
* To save the previous images, to don’t have the images gone every time you close the app (We can use Room for that) 
* To click on one of the previous images to reprocess it again
* To use another way to build RecyclerView, the way I did is a bit old school but still the recommended way to do it. But now we can do it better, just take more time to implement. 
* To use a ViewModel class for the logic
* Change the background with the average color of the rendered image (can look cool no?) 
