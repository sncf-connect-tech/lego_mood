# lego_mood
Calculate team mood with MindStorm/RPI BrickPi in Python

Prepare BrickPi :
We have two BrickPi : One with a RaspBerry Pi 2 and an other one with a RaspBerry Pi 3.
The RaspBerry Pi 3 has also a Raspberry Pi 3.5” Touch Screen like this one : http://osoyoo.com/2016/05/26/osoyoo-lcd-touch-screen-for-raspberry-pi-installation-guide/
This LCD is provided without sources so you have to take their linux distribution.
http://osoyoo.com/2016/09/13/install-raspberry-pi-3-5-touch-screen-driver-for-raspbian-jessie/
We are using the Rasbian Jessy Version.

You can then update and upgrade, but don't dist-upgrade.

Install BrickPi as follow : 
http://www.instructables.com/id/BrickPi-Setup/

Test the system with the color sensor and a motor.

Install the python version of Open-cv :
https://github.com/jabelone/OpenCV-for-Pi

Test with a webcan with the video.py that is included in the source distribution :
https://github.com/opencv/opencv/tree/master/samples/python

Projets :
Grille

This project detect lego colors in a picture.
The step3 picture is a picture that can be processed.

In order to detect a color, use the Image screen and modify cursor to detect a color :
To detect the light grey week separator :
hl=19
sl=29
vl=129
hh=36
sh=67
vh=168

Thresholds have to be adjusted.

Ev3 Mood
code-v4.py
Run the motor and takes 300 samples of color with the color sensor.
The color is converted into HSV ( Hue Saturation Value) to detect colors.
The lego train is recomposed from color in ima5.png
The code is mocked to do tests without the ev3.





