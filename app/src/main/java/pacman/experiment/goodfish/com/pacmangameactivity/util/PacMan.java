package pacman.experiment.goodfish.com.pacmangameactivity.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by jessemacgregor on 4/4/15.
 */
public class PacMan {
    private static final int MAX_OPEN = 45;
    private int x, y;
    private int stepsPerGrid = 10;
    private static final int size = 80;
    private Paint paint = new Paint();
    PacManThread.Dir direction = PacManThread.Dir.RIGHT;
    PacManThread.Dir nextDirection = PacManThread.Dir.RIGHT;
    private int open = MAX_OPEN;
    private int openDelta = -3;
    private RectF rect = new RectF(-size/2, -size/2, size/2, size/2);
    boolean doStop = false;
    Maze theMaze;

    public PacMan(Maze _theMaze, PacManThread.Dir _dir, int _color){
        theMaze = _theMaze;
        PointF startPoint= theMaze.getStart();
        this.x = (int)(startPoint.x*stepsPerGrid);
        this.y = (int)(startPoint.y*stepsPerGrid);
        this.direction = _dir;
        this.nextDirection = _dir;
        paint.setColor(_color);
    }

    public void draw(Canvas c, float scale){
        RectF rect = new RectF(0,0,scale, scale);
        rect.offsetTo(x*scale/(float)stepsPerGrid, y*scale/(float)stepsPerGrid);
        switch(direction){
            case RIGHT:
                c.drawArc(rect, open, 360-2*open, true, paint);
                break;
            case UP:
                c.drawArc(rect, 270+open, 360-2*open, true, paint);
                break;
            case LEFT:
                c.drawArc(rect, 180+open, 360-2*open, true, paint);
                break;
            case DOWN:
                c.drawArc(rect, 90+open, 360-2*open, true, paint);
                break;
        }
    }

    public void setNextDirection(PacManThread.Dir _direction){
        nextDirection = _direction;
    }

    public void move(){
        boolean atIntersection = x % stepsPerGrid == 0 && y % stepsPerGrid == 0;

        // Set the stop to false -- will reevaluate if we're stopped
        doStop = false;

        // If the next queued direction is along the same axis, make it happen immediately
        if((direction == PacManThread.Dir.LEFT && nextDirection == PacManThread.Dir.RIGHT) ||
                (direction == PacManThread.Dir.RIGHT && nextDirection == PacManThread.Dir.LEFT) ||
                (direction == PacManThread.Dir.UP && nextDirection == PacManThread.Dir.DOWN) ||
                (direction == PacManThread.Dir.DOWN && nextDirection == PacManThread.Dir.UP)){
            direction = nextDirection;
        }

        // If at an integer, check to see if nextMove is valid
        if(atIntersection){
            // Eat the Pellet at the intersection
            theMaze.eatPellet(x/stepsPerGrid, y/stepsPerGrid);

            // Check to see if we can switch to the queued up direction
            if( (nextDirection == PacManThread.Dir.UP    && theMaze.canGoUp(x/stepsPerGrid, y/stepsPerGrid)) ||
                    (nextDirection == PacManThread.Dir.DOWN  && theMaze.canGoDown(x / stepsPerGrid, y / stepsPerGrid)) ||
                    (nextDirection == PacManThread.Dir.LEFT  && theMaze.canGoLeft(x / stepsPerGrid, y / stepsPerGrid)) ||
                    (nextDirection == PacManThread.Dir.RIGHT && theMaze.canGoRight(x/stepsPerGrid, y/stepsPerGrid)))
            {
                direction = nextDirection;
            }

            // Check to see if we are at the end of the path and need to stop
            if( (direction == PacManThread.Dir.UP    && !theMaze.canGoUp(x/stepsPerGrid, y/stepsPerGrid)) ||
                    (direction == PacManThread.Dir.DOWN  && !theMaze.canGoDown(x/stepsPerGrid, y/stepsPerGrid)) ||
                    (direction == PacManThread.Dir.LEFT  && !theMaze.canGoLeft(x/stepsPerGrid, y/stepsPerGrid)) ||
                    (direction == PacManThread.Dir.RIGHT && !theMaze.canGoRight(x/stepsPerGrid, y/stepsPerGrid)) )
            {
                doStop = true;
            }
        }

        // If we're not stopped process the move and increment the chomp
        if(!doStop){
            if(direction == PacManThread.Dir.UP){
                y -= 1;
            }else if(direction == PacManThread.Dir.DOWN){
                y += 1;
            }else if(direction == PacManThread.Dir.LEFT){
                x -= 1;
            }else if(direction == PacManThread.Dir.RIGHT){
                x += 1;
            }
            incOpen();
        }

        // Handle wrap around
        y = (theMaze.getHeight()*stepsPerGrid+y) % (theMaze.getHeight() * stepsPerGrid);
        x = (theMaze.getWidth()*stepsPerGrid+x) % (theMaze.getWidth() * stepsPerGrid);
    }

    public void incOpen(){
        open += openDelta;
        if(open >= MAX_OPEN){
            openDelta = -openDelta;
            open = MAX_OPEN;
        }
        else if(open <= 0){
            openDelta = -openDelta;
            open = 0;
        }
    }

}
