import sun.misc.Queue;

import java.awt.image.BufferedImage;
import java.util.AbstractQueue;
import java.util.ArrayList;

/**
 * Created by sainath on 18/2/15.
 */
//This class is designed to store separated GreenPixelsData
public class GreenPixelsData {
    BufferedImage bi;
    int number_of_green_pixels;
    ArrayList<Pixel> greenPixels;
    //This function performs BreadthFirstSearch on GreenPixelsData
    void breadthFirstSearch(){
        int minx=bi.getMinX();
        int miny=bi.getMinY();
        int width=bi.getWidth();
        int height= bi.getHeight();
        int[][] greenery=new int[width][height];
        int[][] visited=new int[width][height];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                visited[i][j]=0;
                greenery[i][j]=0;
            }
        }

        for(int i=0;i<greenPixels.size();i++){
            greenery[greenPixels.get(i).x-minx][greenPixels.get(i).y-miny]=1;
        }
        int pk=0;
        int number_of_components=0;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                if(greenery[i][j]>0 && visited[i][j]==0){
                    ArrayList<Pixel> queue=new ArrayList<Pixel>();
                    queue.add(new Pixel(i,j));
                    number_of_components++;
                    while (!queue.isEmpty()){
                        Pixel present=queue.remove(0);
                        int x=present.x;
                        int y=present.y;
                        visited[x][y]=1;
                        if(pk==0)
                            bi.setRGB(minx+x,miny+y,0x00ff0000);
                        else if(pk==1)
                            bi.setRGB(minx+x,miny+y,0x0000ff00);
                        else if(pk==2)
                            bi.setRGB(minx+x,miny+y,0x000000ff);
                        if(x-1>=0 && y-1>=0 && greenery[x-1][y-1]>0 && visited[x-1][y-1]==0){
                            queue.add(new Pixel(x-1,y-1));
                        }
                        else if(x-1>=0 && greenery[x-1][y]>0 && visited[x-1][y]==0){
                            queue.add(new Pixel(x-1,y));
                        }
                        else if(x-1>=0 && y+1<height && greenery[x-1][y+1]>0 && visited[x-1][y+1]==0){
                            queue.add(new Pixel(x-1,y+1));
                        }
                        else if( y-1>=0 && greenery[x][y-1]>0 && visited[x][y-1]==0){
                            queue.add(new Pixel(x,y-1));
                        }
                        else if(y+1<height && greenery[x][y+1]>0 && visited[x][y+1]==0){
                            queue.add(new Pixel(x,y+1));
                        }
                        else if(x+1<width && y-1>=0 && greenery[x+1][y-1]>0 && visited[x+1][y-1]==0){
                            queue.add(new Pixel(x+1,y-1));
                        }
                        else if(x+1<width && greenery[x+1][y]>0 && visited[x+1][y]==0){
                            queue.add(new Pixel(x+1,y));
                        }
                        else if(x+1<width && y+1<height && greenery[x+1][y+1]>0 && visited[x+1][y+1]==0){
                            queue.add(new Pixel(x+1,y+1));
                        }
                    }
                    if(pk==0)
                        pk=1;
                    else if(pk==1)
                        pk=2;
                    else if(pk==2)
                        pk=0;
                }
            }
        }
        System.out.println(number_of_components);
    }
}
