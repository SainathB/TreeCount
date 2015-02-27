import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sainath on 11/2/15.
 */

public class Main {
    //This function basically returns a copy a BufferImage that is passed as argument
    static BufferedImage getCopy(BufferedImage original){
        ColorModel c=original.getColorModel();
        boolean b=original.isAlphaPremultiplied();
        WritableRaster r=original.copyData(null);
        return new BufferedImage(c,r,b,null);
    }
    //This function creates a JFrame Window and displays BufferedImage bi in that Window
    static void display(BufferedImage bi){
        JFrame fr=new JFrame("Counting Trees");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lb=new JLabel(new ImageIcon(bi));
        lb.setSize(100,100);
        fr.getContentPane().add(lb);
        fr.pack();
        fr.setVisible(true);
    }
    //This function separates green pixels from BufferedImage and returns complete information as GreenPixelsData Object
    static GreenPixelsData separateGreenPixels(BufferedImage bi){
        BufferedImage green=getCopy(bi);
        ArrayList<Pixel> lp=new ArrayList<Pixel>();
        int precision=100;
        int minx=green.getMinX();
        int miny=green.getMinY();
        int height=green.getHeight();
        int width=green.getWidth();
        int mingreenery=10000;
        for(int i=minx;i<minx+width;i++){
            for(int j=miny;j<miny+height;j++){
                int colour=green.getRGB(i,j);
                int  r = (colour & 0x00ff0000) >> 16;
                int  g = (colour & 0x0000ff00) >> 8;
                int  b = colour & 0x000000ff;
                if(r+(255-g)+b<mingreenery)
                    mingreenery=r+(255-g)+b;
            }
        }
        int count1=0;
        int count2=0;
        for(int i=minx;i<minx+width;i++){
            for(int j=miny;j<miny+height;j++){
                int colour=green.getRGB(i,j);
                int  r = (colour & 0x00ff0000) >> 16;
                int  g = (colour & 0x0000ff00) >> 8;
                int  b = colour & 0x000000ff;
                if(r+(255-g)+b>mingreenery+precision){
                    green.setRGB(i,j,0x00ffffff);
                    count1++;
                }
                else{
                    count2++;
                    lp.add(new Pixel(i,j));
                }
            }
        }
        GreenPixelsData gpd=new GreenPixelsData();
        gpd.bi=green;
        gpd.number_of_green_pixels=count2;
        gpd.greenPixels=lp;

        return gpd;

    }
    public static void main(String[] args){
        try {
            File fin=new File("/home/sainath/Downloads/sai.jpg");
            BufferedImage im= ImageIO.read(fin);
            display(im);
            GreenPixelsData gpd=separateGreenPixels(im);
            BufferedImage p=getCopy(gpd.bi);
            display(p);
            System.out.println(gpd.number_of_green_pixels);
            System.out.println(gpd.bi.getWidth()+" "+gpd.bi.getHeight());
            gpd.breadthFirstSearch();
            int x=1;
            display(gpd.bi);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
