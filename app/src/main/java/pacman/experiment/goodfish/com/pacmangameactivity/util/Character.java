package pacman.experiment.goodfish.com.pacmangameactivity.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by jessemacgregor on 4/4/15.
 */
public abstract class Character {
    public enum Dir {LEFT, RIGHT, UP, DOWN, STOP};
    protected static int ANIM_STATE_RANGE = 256;

    protected int x, y;
    protected int stepsPerGrid = 8;
    protected Paint paint = new Paint();
    protected Dir direction = Dir.RIGHT;
    protected Dir nextDirection = Dir.RIGHT;
    protected boolean doStop = false;
    protected Maze theMaze;
    protected int animState = 0;

    public Character(Maze _theMaze, Dir _dir, int _color){
        theMaze = _theMaze;
        PointF startPoint= theMaze.getStart();
        this.x = (int)(startPoint.x*stepsPerGrid);
        this.y = (int)(startPoint.y*stepsPerGrid);
        this.direction = _dir;
        this.nextDirection = _dir;
        paint.setColor(_color);
    }

    public abstract void draw(Canvas c, float scale);

    public abstract void handleIntersection(int mazeX, int mazeY);

    public void setNextDirection(Dir _direction){
        nextDirection = _direction;
    }

    public void move(){
        boolean atIntersection = x % stepsPerGrid == 0 && y % stepsPerGrid == 0;

        // Set the stop to false -- will reevaluate if we're stopped
        doStop = false;

        // If the next queued direction is along the same axis, make it happen immediately
        if((direction == Dir.LEFT && nextDirection == Dir.RIGHT) ||
                (direction == Dir.RIGHT && nextDirection == Dir.LEFT) ||
                (direction == Dir.UP && nextDirection == Dir.DOWN) ||
                (direction == Dir.DOWN && nextDirection == Dir.UP)){
            direction = nextDirection;
        }

        // If at an integer, check to see if nextMove is valid
        if(atIntersection){
            // Eat the Pellet at the intersection
            handleIntersection(x/stepsPerGrid, y/stepsPerGrid);

            // Check to see if we can switch to the queued up direction
            if( (nextDirection == Dir.UP    && theMaze.canGoUp(x/stepsPerGrid, y/stepsPerGrid)) ||
                    (nextDirection == Dir.DOWN  && theMaze.canGoDown(x / stepsPerGrid, y / stepsPerGrid)) ||
                    (nextDirection == Dir.LEFT  && theMaze.canGoLeft(x / stepsPerGrid, y / stepsPerGrid)) ||
                    (nextDirection == Dir.RIGHT && theMaze.canGoRight(x/stepsPerGrid, y/stepsPerGrid)))
            {
                direction = nextDirection;
            }

            // Check to see if we are at the end of the path and need to stop
            if( (direction == Dir.UP    && !theMaze.canGoUp(x/stepsPerGrid, y/stepsPerGrid)) ||
                    (direction == Dir.DOWN  && !theMaze.canGoDown(x/stepsPerGrid, y/stepsPerGrid)) ||
                    (direction == Dir.LEFT  && !theMaze.canGoLeft(x/stepsPerGrid, y/stepsPerGrid)) ||
                    (direction == Dir.RIGHT && !theMaze.canGoRight(x/stepsPerGrid, y/stepsPerGrid)) )
            {
                doStop = true;
            }
        }

        // If we're not stopped process the move and increment the chomp
        if(!doStop){
            if(direction == Dir.UP){
                y -= 1;
            }else if(direction == Dir.DOWN){
                y += 1;
            }else if(direction == Dir.LEFT){
                x -= 1;
            }else if(direction == Dir.RIGHT){
                x += 1;
            }
            animState = (animState + 1) % ANIM_STATE_RANGE;
        }

        // Handle wrap around
        y = (theMaze.getHeight()*stepsPerGrid+y) % (theMaze.getHeight() * stepsPerGrid);
        x = (theMaze.getWidth()*stepsPerGrid+x) % (theMaze.getWidth() * stepsPerGrid);
    }

}
