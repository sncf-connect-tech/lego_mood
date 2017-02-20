import cv2
import numpy as np

#Isabelle?, Jessica,  Pablo, Raph, Alexandre, Sam, Herve, Sylvain, Philippe, Landry, Henri

def nothing(x):
    pass 

def detectColor(b, g, r):
	# define range of blue color in HSV
	lower_blue = np.array([106,50,50])
	upper_blue = np.array([112,255,255])

	lower_red = np.array([1,150,55])
	upper_red = np.array([9,255,153])

	lower_green = np.array([39,118,33])
	upper_green = np.array([79,224,86])

	lower_orange = np.array([6,217,153])
	upper_orange = np.array([12,234,184])

	lower_pink = np.array([1,130,0])
	upper_pink = np.array([14,150,190])

	lower_grey = np.array([27,36,58])
	upper_grey = np.array([44,63,93])

	
	color = np.uint8([[[b,g,r ]]])
	hsv = cv2.cvtColor(color,cv2.COLOR_BGR2HSV)

	pixel=hsv[0,0]

	h=pixel[0]
	s=pixel[1]
	v=pixel[2]

	if h >= lower_blue[0] and h < upper_blue[0] and s >= lower_blue[1] and s < upper_blue[1] and v >= lower_blue[2] and v < upper_blue[2] :
		col="bleu"
	elif h >= lower_red[0] and h < upper_red[0] and s >= lower_red[1] and s < upper_red[1] and v >= lower_red[2] and v < upper_red[2] :
		col="red"
	elif h >= lower_green[0] and h < upper_green[0] and s >= lower_green[1] and s < upper_green[1] and v >= lower_green[2] and v < upper_green[2] :
		col="green"
	elif h >= lower_orange[0] and h < upper_orange[0] and s >= lower_orange[1] and s < upper_orange[1] and v >= lower_orange[2] and v < upper_orange[2] :
		col="orange"
	elif h >= lower_pink[0] and h < upper_pink[0] and s >= lower_pink[1] and s < upper_pink[1] and v >= lower_pink[2] and v < upper_pink[2] :
		col="pink"
	elif h >= lower_grey[0] and h < upper_grey[0] and s >= lower_grey[1] and s < upper_grey[1] and v >= lower_grey[2] and v < upper_grey[2] :
		col="grey"
	else :
		col="????"
	return col


img = cv2.imread('step3.jpg', 1)
imgorig = cv2.imread('step3.jpg', 1)
#img = cv2.imread('5TTQ7iv.jpg', 1)
hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

#Detecter les coins
#orange
lower_range = np.array([20/2, 200, 0], dtype=np.uint8)
upper_range = np.array([40/2, 255, 255], dtype=np.uint8)
mask = cv2.inRange(hsv, lower_range, upper_range)
height = np.size(img, 0)
width = np.size(img, 1)

#Redresser ?

#diviser en grille 924/602
#32/32 ?
xmin=0
xmax=924
ymin=58
ymax=602
cv2.line(img,(xmin,ymin),(xmax,ymin),(255,0,0),1)
cv2.line(img,(xmax,ymin),(xmax,ymax),(255,0,0),1)
cv2.line(img,(xmax,ymax),(xmin,ymax),(255,0,0),1)
cv2.line(img,(xmin,ymax),(xmin,ymin),(255,0,0),1)

nbx=54
nby=32

dx=float(xmax-xmin)/float(nbx)
dy=float(ymax-ymin)/float(nby)

for x in range(0, nbx):
	xg=(int)(float(x)*dx)
	cv2.line(img,(xmin+xg,ymin),(xmin+xg,ymax),(0,255,0),1)
for y in range(0, nby):
	yg=(int)(float(y)*dy)
	cv2.line(img,(xmin,ymin+yg),(xmax,ymin+yg),(0,0,255),1)

#Moyenner
im2lx=xmax-xmin
im2ly=ymax-ymin
img2 = np.zeros((im2ly,im2lx,3), np.uint8) 

for ligne in range(0, 32) :
	for colonne in range(0, 54) :
		#calcul 1er carre
		#B V R
		sb=0
		sv=0
		sr=0
		for x in range(0, int(dx)):
			for y in range(0, int(dy)):
				px=int(x+xmin+colonne*dx)
				py=int(y+ymin+ligne*dx)
				if py>ymax-1 :
					py=ymax-1
				if px>xmax-1 :
					px=xmax-1
				pixel=img[py,px]
				bcolor = pixel[0]
				sb=sb+bcolor
				vcolor = pixel[1]
				sv=sv+vcolor
				rcolor = pixel[2]
				sr=sr+rcolor

		sb=sb/(dx*dy)
		sv=sv/(dx*dy)
		sr=sr/(dx*dy)

		y1=int((ligne)*dy)
		x1=int((colonne)*dx)
		y2=int(dx+(ligne)*dy)
		x2=int((colonne)*dx+dx)
		

		col=detectColor(sb, sv, sr)
		cv2.rectangle(img2, (x1,y1), (x2, y2), (int(sb),int(sv),int(sr)),  thickness=-1)		
		if col=="bleu":
			cv2.rectangle(img2, (x1,y1), (x2, y2), (255,0,0),  thickness=1)
			cv2.rectangle(imgorig, (x1+xmin,y1+ymin), (x2+xmin, y2+ymin), (255,0,0),  thickness=2)
		elif col=="red":
			cv2.rectangle(img2, (x1,y1), (x2, y2), (0,0,255),  thickness=1)
			cv2.rectangle(imgorig, (x1+xmin,y1+ymin), (x2+xmin, y2+ymin), (0,0,255),  thickness=2)
		elif col=="green":
			cv2.rectangle(img2, (x1,y1), (x2, y2), (0,255,0),  thickness=1)
			cv2.rectangle(imgorig, (x1+xmin,y1+ymin), (x2+xmin, y2+ymin), (0,255,0),  thickness=2)
		elif col=="orange":
			cv2.rectangle(img2, (x1,y1), (x2, y2), (0,140,255),  thickness=1)
			cv2.rectangle(imgorig, (x1+xmin,y1+ymin), (x2+xmin, y2+ymin), (0,140,255),  thickness=2)
		elif col=="pink":
			cv2.rectangle(img2, (x1,y1), (x2, y2), (140,0,255),  thickness=1)
			cv2.rectangle(imgorig, (x1+xmin,y1+ymin), (x2+xmin, y2+ymin), (140,0,255),  thickness=2)
		elif col=="grey":
			cv2.rectangle(img2, (x1,y1), (x2, y2), (128,128,128),  thickness=1)
			cv2.rectangle(imgorig, (x1+xmin,y1+ymin), (x2+xmin, y2+ymin), (128,128,128),  thickness=2)
			
cv2.imshow('mask',img2)

cv2.namedWindow('image')

cv2.createTrackbar('hl','image',0,255,nothing)
cv2.createTrackbar('sl','image',0,255,nothing)
cv2.createTrackbar('vl','image',0,255,nothing)
cv2.createTrackbar('hh','image',0,255,nothing)
cv2.createTrackbar('sh','image',0,255,nothing)
cv2.createTrackbar('vh','image',0,255,nothing)

cv2.setTrackbarPos('hl','image',0)
cv2.setTrackbarPos('sl','image',0)
cv2.setTrackbarPos('vl','image',0)
cv2.setTrackbarPos('hh','image',255)
cv2.setTrackbarPos('sh','image',255)
cv2.setTrackbarPos('vh','image',255)

cv2.imshow('orig',imgorig)

while(1):
    k = cv2.waitKey(1) & 0xFF
    if k == 27:
        break

    # get current positions of four trackbars
    hl = cv2.getTrackbarPos('hl','image')
    sl = cv2.getTrackbarPos('sl','image')
    vl = cv2.getTrackbarPos('vl','image')
    hh = cv2.getTrackbarPos('hh','image')
    sh = cv2.getTrackbarPos('sh','image')
    vh = cv2.getTrackbarPos('vh','image')

    hsv = cv2.cvtColor(img2,cv2.COLOR_BGR2HSV)
    thrImg = cv2.inRange(hsv,np.array([hl,sl,vl]),np.array([hh,sh,vh]))
    # Bitwise-AND mask and original image
    res = cv2.bitwise_and(img2,img2, mask= thrImg)
    cv2.imshow('image',res)

cv2.imwrite('step5.png',img)
cv2.imwrite('step6.png',img2)
cv2.imwrite('step7.png',imgorig)
cv2.destroyAllWindows()
