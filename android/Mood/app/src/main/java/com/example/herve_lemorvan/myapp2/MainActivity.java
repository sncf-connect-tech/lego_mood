package com.example.herve_lemorvan.myapp1;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;




public class MainActivity extends AppCompatActivity {

    private enum MyColor {BLUE,RED,GREEN,ORANGE,PINK,GREY,UNKNOWN};

    SeekBar hueBarMin, satBarMin, valBarMin;
    SeekBar hueBarMax, satBarMax, valBarMax;

    TextView hueTextMin, satTextMin, valTextMin;
    TextView hueTextMax, satTextMax, valTextMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hueTextMin = (TextView) findViewById(R.id.texthuemin);
        satTextMin = (TextView) findViewById(R.id.textsatmin);
        valTextMin = (TextView) findViewById(R.id.textvalmin);
        hueTextMax = (TextView) findViewById(R.id.texthuemax);
        satTextMax = (TextView) findViewById(R.id.textsatmax);
        valTextMax = (TextView) findViewById(R.id.textvalmax);

        hueBarMin = (SeekBar) findViewById(R.id.huebarmin);
        satBarMin = (SeekBar) findViewById(R.id.satbarmin);
        valBarMin = (SeekBar) findViewById(R.id.valbarmin);
        hueBarMin.setOnSeekBarChangeListener(seekBarChangeListener);
        satBarMin.setOnSeekBarChangeListener(seekBarChangeListener);
        valBarMin.setOnSeekBarChangeListener(seekBarChangeListener);
        hueBarMax = (SeekBar) findViewById(R.id.huebarmax);
        satBarMax = (SeekBar) findViewById(R.id.satbarmax);
        valBarMax = (SeekBar) findViewById(R.id.valbarmax);
        hueBarMax.setOnSeekBarChangeListener(seekBarChangeListener);
        satBarMax.setOnSeekBarChangeListener(seekBarChangeListener);
        valBarMax.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                // now we can call opencv code !
                helloworld();
            } else {
                super.onManagerConnected(status);
            }
        }
    };

    @Override
    public void onResume() {;
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0,this, mLoaderCallback);
        // you may be tempted, to do something here, but it's *async*, and may take some time,
        // so any opencv call here will lead to unresolved native errors.
    }

    public void moyenner() {
    }

    private final Scalar lower_blue =  new Scalar(2,131,68);
    private final Scalar upper_blue = new Scalar(10,211,115);

    private final Scalar lower_red = new Scalar(115,209,94);
    private final Scalar upper_red = new Scalar(124,255,154);

    private final Scalar lower_green = new Scalar(45,124,35);
    private final Scalar upper_green = new Scalar(78,240,74);

    private final Scalar lower_orange = new Scalar(112,215,150);
    private final Scalar upper_orange = new Scalar(120,234,191);

    private final Scalar lower_pink = new Scalar(120,133,149);
    private final Scalar upper_pink = new Scalar(132,149,198);

    private Scalar lower_grey = new Scalar(27,36,58);
    private Scalar upper_grey = new Scalar(44,63,93);


    public MyColor detectColor(Scalar pt) {
        MyColor retour=MyColor.UNKNOWN;
        Mat color = Mat.zeros(1,1,CvType.CV_8UC3);
        color.setTo(pt);
        Mat hsv = Mat.zeros(1,1,CvType.CV_8UC3);
        Imgproc.cvtColor(color, hsv, Imgproc.COLOR_RGB2HSV,0);
        double[] hsv2=hsv.get(0,0);
        System.out.println(hsv2);

        Mat color2 = Mat.zeros(1,1,CvType.CV_8UC3);


        Core.inRange(hsv, lower_blue, upper_blue, color2);
        if (color2.get(0,0)[0]>0)
            return MyColor.BLUE;
        Core.inRange(hsv, lower_red, upper_red, color2);
        if (color2.get(0,0)[0]>0)
            return MyColor.RED;
        Core.inRange(hsv, lower_green, upper_green, color2);
        if (color2.get(0,0)[0]>0)
            return MyColor.GREEN;
        Core.inRange(hsv, lower_orange, upper_orange, color2);
        if (color2.get(0,0)[0]>0)
            return MyColor.ORANGE;
        Core.inRange(hsv, lower_pink, upper_pink, color2);
        if (color2.get(0,0)[0]>0)
            return MyColor.PINK;

        //Reglages
        Core.inRange(hsv, lower_grey, upper_grey, color2);
        if (color2.get(0,0)[0]>0)
            return MyColor.GREY;






        return retour;
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            helloworld();
        }

    };


    public void helloworld() {

        int progressHueMin = hueBarMin.getProgress()/2;
        int progressSatMin = satBarMin.getProgress()/2;
        int progressValMin = valBarMin.getProgress()/2;
        int progressHueMax = hueBarMax.getProgress()/2;
        int progressSatMax = satBarMax.getProgress()/2;
        int progressValMax = valBarMax.getProgress()/2;

        hueTextMin.setText("Hue min: " + progressHueMin);
        satTextMin.setText("Saturation min: " + progressSatMin);
        valTextMin.setText("Value min: " + progressValMin);
        hueTextMax.setText("Hue max: " + progressHueMax);
        satTextMax.setText("Saturation max: " + progressSatMax);
        valTextMax.setText("Value max: " + progressValMax);


        lower_grey = new Scalar(progressHueMin,progressSatMin,progressValMin);
        upper_grey = new Scalar(progressHueMax,progressSatMax,progressValMax);

        int xmin=0;
        int xmax=924;
        int ymin=58;
        int ymax=602;

        int nbx=54;
        int nby=32;

        float dx=(float)(xmax-xmin)/(float)(nbx);
        float dy=(float)(ymax-ymin)/(float)(nby);

        Mat cokeBGR = null;
        try {
            cokeBGR = Utils.loadResource(MainActivity.this, R.drawable.step3, Imgcodecs.IMREAD_COLOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mat cokeRGBA = new Mat();
        Imgproc.cvtColor(cokeBGR, cokeRGBA, Imgproc.COLOR_BGR2RGBA);

        //Moyenner
        int im2lx=xmax-xmin;
        int im2ly=ymax-ymin;

        Mat mImg2 = Mat.zeros(ymax,xmax, CvType.CV_8UC3);

        for (int ligne=0; ligne<nby; ligne++) { //32
            for (int colonne = 0; colonne < nbx; colonne++) { //54
                //calcul 1er carre
                //B V R
                double sb = 0;
                double sv = 0;
                double sr = 0;
                for (int x = 0; x < (int) dx; x++) {
                    for (int y = 0; y < (int) dy; y++) {
                        int px = (int) (x + xmin + colonne * dx);
                        int py = (int) (y + ymin + ligne * dx);
                        if (py > ymax - 1)
                            py = ymax - 1;
                        if (px > xmax - 1)
                            px = xmax - 1;
                        double[] pixel = cokeRGBA.get(py, px);
                        double bcolor = pixel[0];
                        sb = sb + bcolor;
                        double vcolor = pixel[1];
                        sv = sv + vcolor;
                        double rcolor = pixel[2];
                        sr = sr + rcolor;
                    }
                }
                sb=sb/(dx*dy);
                sv=sv/(dx*dy);
                sr=sr/(dx*dy);

                int y1=(int)((ligne)*dy);
                int x1=(int)((colonne)*dx);
                int y2=(int)(dx+(ligne)*dy);
                int x2=(int)((colonne)*dx+dx);

                Scalar colorBGR=new Scalar(sb, sv, sr);
                Scalar colorRGB=new Scalar(sr, sv, sb);

                Imgproc.rectangle(mImg2,new Point(x1+xmin,y1+ymin),new Point(x2+xmin, y2+ymin), colorBGR,Core.FILLED);

                MyColor col=detectColor(colorRGB);


                switch(col) {
                    case BLUE:
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(255, 255, 255), Core.FILLED);
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(0, 0, 255), 4);
                        break;
                    case RED:
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(255, 255, 255), Core.FILLED);
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(255, 0, 0), 4);
                        break;
                    case GREEN:
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(255, 255, 255), Core.FILLED);
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(0, 255, 0), 4);
                        break;
                    case ORANGE:
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(255, 255, 255), Core.FILLED);
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(255, 140, 0), 4);
                        break;
                    case PINK:
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(255, 255, 255), Core.FILLED);
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(140, 0, 255), 2);
                        break;

                    case GREY:
                        Imgproc.rectangle(mImg2, new Point(x1+xmin, y1+ymin), new Point(x2+xmin, y2+ymin), new Scalar(255, 255, 255), 4);
                        break;
                }
            }
        }

        Mat hsv = Mat.zeros(ymax,xmax, CvType.CV_8UC3);

        Imgproc.cvtColor(cokeBGR, hsv, Imgproc.COLOR_BGR2HSV,0);
        cokeRGBA=mImg2;

        Bitmap bm = Bitmap.createBitmap(cokeRGBA.cols(), cokeRGBA.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(cokeRGBA, bm);


        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        iv.setImageBitmap(bm);
    }
}
