# MyDaliChallenges


# Challenge: Building a website

To execute this challenge, I used javascript, css, and html to build a website from scratch. The website I build has a home page that contains two pictures and a couple welcoming sentences and then it also contains embedded links to an AboutMe page and to my Dartmouth journeys portfolio. To make this website, I used two code files: one html file to to create the website and add all necessary contents to it and one css file to help in editing and beautifying the website appearance. In my html file (named, index.html), I used various elements and selectors like <body>, <head>, <header>, etc to add different parts of the website to their respective pages. Then I used the main.css file to make all the editings needed to improve the appearance of my website content and ensure that each part is in a position it's supposed to be in.

  Due to limited time and skills at the moment, I opted to build a basic website like this because I didn't have enough time to create a more advanced and better website.
  
  On that note, I also wanted to share that I started pursuing my major, computer sciences modified with engineering sciences, last term (W22). Consequently, I haven't had enough time and most importantly enough skills to work on extra curricular projects that I can submit as part of my application to DALI. That is why I decided to submit two of the projects that I have worked on as part of my CS10 assignments this term. 
  
# Other 2 Projects Submitted
  
  The first project is the image processing project and the second project is the Huffman Encoding project.
  
  # A. Image Processing Project
  
  In this project, there are two main classes, the RegionFinder Class and the Campaint (coloring the selected part in the webcam) part. Here is the description of my code:
  
  *RegionFrinder Class
  
Starting with the findRegions method, we know that its purpose is to locate the regions in the image that have colors that are similar enough to the trackColor. With that in mind, I created a new blank image, called visited, that has the same dimensions as the image being edited (processed). The image, named "visited", helps in recording each visited pixel of the image being processed by coloring the similar position of that pixel on the visited image with a different color of rgb=1. The reason why I chose to make "visited" an image, instead of an arraylist or anything else is because an image presents an easier way of checking whether a certain pixel has been visited as opposed to an arraylist or any other data structure. On that note, to check whether the image has been visited, I used a for loop that went over each pixel on the image being processed and marked its corresponding coordinate on the visited image with color  of rgb=1. That way if that same coordinate is checked again, its corresponding coordinate on the visited image will give away the hint that it has been visited already because its pertinent pixel on the visited image will not be blank, but colored. Now, since the main mission is to find which parts of the image being processed has a similar color to the trackColor, for each visited pixel, we need to evaluate all of its neighbors to see if they have a similar color to trackColor and if they have been visited or not. In case a  neighboring pixel is found to be closely similar to the trackColor and has not been visited before, it is added to the toVisit arraylist, which contains all the pixels that have to be visited yet. In this toVisit arraylist, the first pixel to be stored, is also the first pixel to be removed to be processed.

That said, for each visited pixel that has a similar color to trackColor, it gets added to an arraylist of points called "region", which contains all the neighboring pixel points of color similar to the trackColor. To check the color similarity, a method called colorMatch is called to compare the color of a pixel to that of the trackColor, and it returns true or false depending on whether the color difference is less than or equal to the acceptable maximum color difference or not. Once a certain pixel is done being analyzed and all of its neighboring points have been checked as well, a new empty region, which will store the relevant information about a different pixel, is created, and this goes on until each pixel on the image being processed has been analyzed. Also, for each region, its size is checked to see if it's big enough and this is done by comparing its size with the size of the minRegion. If it is, it gets added to another arraylist called, "regions", which stores all the individual regions that fits the description of being big enough. That's pretty much what the findRegions method does.

As for the largestRegion method, it checks whether the regions arraylist is empty or not. When regions arraylist is not empty, it uses a for loop to go through each region in the regions arraylist and check which one is the biggest and returns it, as the arraylist, bigRegion. In case the regions arraylist is found to be empty, this method returns null.

CamPaint Class

In my CamPaint class, starting with the processImage method, I used the  largest arraylist, which stored the largest region from the regionFinder method, and looped over each pixel of that region and recolored it. As for the handleMousePress, it checks if the image is not null and then sets the targetColor to be the color of the image. When it comes to the draw method, it draws a different appropriate image for each display mode. If it's displaymode, w, it draws the unedited webcam image, if it's the displaymode, r, it draws the recolored image, while if it is displaymode, p, it draws the painting of the selected region movement. 
