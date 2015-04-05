package pacman.experiment.goodfish.com.pacmangameactivity.util;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by jessemacgregor on 4/4/15.
 */
public class Ghost extends Character {
    protected int targetX, targetY;

    public Ghost(Maze _theMaze, Dir _dir, int _color, int _targetX, int _targetY){
        super(_theMaze, _dir, _color);
        targetX = _targetX;
        targetY = _targetY;
    }

    @Override
    public void draw(Canvas c, float scale) {
        RectF rect = new RectF(0,0,scale, scale);
        rect.offsetTo(x*scale/(float)stepsPerGrid, y*scale/(float)stepsPerGrid);
        c.drawArc(rect, 180, 180, true, paint);
        c.drawRect(rect.left, rect.centerY(), rect.right, rect.bottom, paint);

        switch(direction){
            case RIGHT:
                break;
            case UP:
                break;
            case LEFT:
                break;
            case DOWN:
                break;
        }
    }

    @Override
    public void handleIntersection(int mazeX, int mazeY) {
        // Need to evaluate which way to go at each intersection...
        // TODO: Randomize if don't care and there is a choice.
        if(direction == Dir.LEFT || direction == Dir.RIGHT){
            if(theMaze.canGoUp(mazeX, mazeY) && targetY < mazeY){
                nextDirection = Dir.UP;
            }else if(theMaze.canGoDown(mazeX, mazeY) && targetY > mazeY){
                nextDirection = Dir.DOWN;
            }else if(theMaze.canGoDown(mazeX, mazeY)){
                nextDirection = Dir.DOWN;
            }else if(theMaze.canGoUp(mazeX, mazeY)){
                nextDirection = Dir.UP;
            }
        }
        if(direction == Dir.UP || direction == Dir.DOWN){
            if(theMaze.canGoLeft(mazeX, mazeY) && targetX < mazeX){
                nextDirection = Dir.LEFT;
            }else if(theMaze.canGoRight(mazeX, mazeY) && targetX > mazeX) {
                nextDirection = Dir.RIGHT;
            }else if(theMaze.canGoLeft(mazeX, mazeY)){
                nextDirection = Dir.LEFT;
            }else if(theMaze.canGoRight(mazeX, mazeY)){
                nextDirection = Dir.RIGHT;
            }
        }

    }
}
