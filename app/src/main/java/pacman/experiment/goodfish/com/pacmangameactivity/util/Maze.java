package pacman.experiment.goodfish.com.pacmangameactivity.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by jessemacgregor on 3/28/15.
 */
public class Maze {
    private int width;
    private int height;
    private enum MazeType {PELLET, POWER, NONE};
    private MazeElement theMaze[][];

    private class MazeElement{
        private MazeType type;
        private boolean empty;
        public MazeElement(MazeType _type){
            type  = _type;
            empty = false;
        }
    }

    private void initializeMaze(){
        String[] mazeString = new String[]{
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                "x*...........xx...........*x",
                "x.xxxx.xxxxx.xx.xxxxx.xxxx.x",
                "x.xxxx.xxxxx.xx.xxxxx.xxxx.x",
                "x.xxxx.xxxxx.xx.xxxxx.xxxx.x",
                "x..........................x",
                "x.xxxx.xx.xxxxxxxx.xx.xxxx.x",
                "x.xxxx.xx.xxxxxxxx.xx.xxxx.x",
                "x......xx....xx....xx......x",
                "xxxxxx.xxxxx xx xxxxx.xxxxxx",
                "xxxxxx.xxxxx xx xxxxx.xxxxxx",
                "xxxxxx.xx          xx.xxxxxx",
                "xxxxxx.xx xxxxxxxx xx.xxxxxx",
                "xxxxxx.xx x      x xx.xxxxxx",
                "      .   x      x   .      ",
                "xxxxxx.xx x      x xx.xxxxxx",
                "xxxxxx.xx xxxxxxxx xx.xxxxxx",
                "xxxxxx.xx          xx.xxxxxx",
                "xxxxxx.xx xxxxxxxx xx.xxxxxx",
                "xxxxxx.xx xxxxxxxx xx.xxxxxx",
                "x............xx............x",
                "x.xxxx.xxxxx.xx.xxxxx.xxxx.x",
                "x.xxxx.xxxxx.xx.xxxxx.xxxx.x",
                "x*..xx.......  .......xx..*x",
                "xxx.xx.xx.xxxxxxxx.xx.xx.xxx",
                "xxx.xx.xx.xxxxxxxx.xx.xx.xxx",
                "x......xx....xx....xx......x",
                "x.xxxxxxxxxx.xx.xxxxxxxxxx.x",
                "x..........................x",
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxx"   };

        // Set the width and height
        height = mazeString.length;
        width = mazeString[0].length();
        theMaze = new MazeElement[height][width];

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                switch(mazeString[y].charAt(x)){
                    case 'x':
                        theMaze[y][x] = null;
                        break;
                    case ' ':
                        theMaze[y][x] = new MazeElement(MazeType.NONE);
                        break;
                    case '.':
                        theMaze[y][x] = new MazeElement(MazeType.PELLET);
                        break;
                    case '*':
                        theMaze[y][x] = new MazeElement(MazeType.PELLET);
                        break;
                }
            }
        }

    }

    public boolean eatPellet(int x, int y){
        boolean rVal = false;
        if(theMaze[y][x].type == MazeType.PELLET && theMaze[y][x].empty == false){
            rVal = true;
            theMaze[y][x].empty = true;
        }
        return rVal;
    }

    public PointF getStart(){
        return new PointF((float)12.5, (float)23.0);
    }

    public Maze(int _width, int _height){
        width  = _width;
        height = _height;
        initializeMaze();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean canGoUp(int x, int y){
        return theMaze[(height+y-1)%height][x%width]!=null;
    }

    public boolean canGoDown(int x, int y){
        return theMaze[(y+1)%height][x%width]!=null;
    }

    public boolean canGoLeft(int x, int y){
        return theMaze[y%height][(width+x-1)%width]!=null;
    }

    public boolean canGoRight(int x, int y){
        return theMaze[y%height][(x+1)%width]!=null;
    }

    public void draw(Canvas c, float scale){
        RectF rect = new RectF(0,0, scale, scale);

        Paint backGroundPaint = new Paint();
        backGroundPaint.setColor(0xFF000000);
        Paint outOfBoundsPaint = new Paint();
        outOfBoundsPaint.setColor(0xFF0000FF);
        Paint pelletPaint = new Paint();
        pelletPaint.setColor(0xFFFFFFFF);

        //Walk through the maze, drawing the elements
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                MazeElement elem = theMaze[i][j];
                rect.offsetTo(j*scale, i*scale);
                if(elem == null){
                    // Draw a block for the maze boundary
                    c.drawRect(rect, outOfBoundsPaint);
                }else if(elem.type == MazeType.PELLET && !elem.empty){
                    // TODO - Draw a pellet
                    c.drawRect(rect, backGroundPaint);
                    c.drawCircle(rect.centerX(), rect.centerY(), scale/5, pelletPaint);
                }else if(elem.type == MazeType.POWER && !elem.empty){
                    // TODO - Draw a power pellet
                    c.drawRect(rect, backGroundPaint);
                    c.drawCircle(rect.centerX(), rect.centerY(), scale/3, pelletPaint);
                }
            }
        }
    }

}
