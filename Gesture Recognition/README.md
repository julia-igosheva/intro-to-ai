 I have designed a gesture recognition project that can recognize at least 3 different hand gestures through a built-in camera using OpenCV and Jupyter. My algorithm responds to different hand displays from users and to their gestures accordingly through template matching. As long as the gestures displayed by the users are similar to the templates I fed to my algorithm, the accuracy and precision of identifying the hand shapes should be relatively high. That being said, since my camera and my code is not at its most advanced stage, certain factors may play a role in lowering the accuracy such as different lightning, background noise, or ambiguous hand shapes, which is why I have also implemented a threshold. 
  
  I used OpenCV as my main source. I have also implemented techniques such as background subtraction, frame-to frame differencing, template matching, motion energy template, and skin-colour detection within my algorithm. However, before all that, I firstly took pictures of my hands with the ideal gestures that I hoped my algorithm will recognize, in this case it is the “ok,” “thumbs-up” and “hi” symbols. I have also implemented the motion energy function to train our algorithm to follow our hands whilst waving as our dynamic gesture. I used OpenCV’s template matching function to recognize my hand gestures.

  I have got 4 video streaming windows to display FrameDifferencing, MotionHistory, SkinDetection and the live stream of my gestures with labels. I create the template variables by reading the images and thresholding them (as well as creating a reserved copy of each). Then I compare each frame to the 3 templates and display the corresponding label in the case of each match.