package pacman.experiment.goodfish.com.pacmangameactivity.util;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by jessemacgregor on 4/4/15.
 */
public class PacMan extends Character{
    protected static final int MAX_OPEN = 45;

    public PacMan(Maze _theMaze, Dir _dir, int _color) {
        super(_theMaze, _dir, _color);
    }

    @Override
    public void draw(Canvas c, float scale) {
        RectF rect = new RectF(0,0,scale, scale);
        rect.offsetTo(x*scale/(float)stepsPerGrid, y*scale/(float)stepsPerGrid);
        int open = (animState % 8) * 45 / 8;
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

    @Override
    public void handleIntersection(int mazeX, int mazeY) {
        theMaze.eatPellet(mazeX, mazeY);
    }

}