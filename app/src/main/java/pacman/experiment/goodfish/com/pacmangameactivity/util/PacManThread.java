package pacman.experiment.goodfish.com.pacmangameactivity.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by jessemacgregor on 3/28/15.
 */
public class PacManThread extends Thread {
    public enum Dir {LEFT, RIGHT, UP, DOWN, STOP};
    private final String TAG = "PACMAN_THREAD";
    private SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private Maze theMaze = new Maze(30, 33);
    private PacMan p1 = new PacMan(theMaze, Dir.RIGHT,0xFFFFFF00);
    private Dir moveDir = Dir.STOP;

    PacManThread(SurfaceHolder surfaceHolder){
        this.surfaceHolder = surfaceHolder;
    }

    private void tryDraw(){
        Canvas c = surfaceHolder.lockCanvas();
        if(c != null){
            doDraw(c);
            surfaceHolder.unlockCanvasAndPost(c);
        }else{
        }
    }

    private void doDraw(Canvas c){
        float scale = c.getWidth() / (float) theMaze.getWidth();

        c.drawARGB(0xFF, 0, 0, 0);
        theMaze.draw(c, scale);
        p1.draw(c, scale);
    }

    public void requestStop(){
        isRunning = false;
    }

    public void move(Dir direction){
        p1.setNextDirection(direction);
    }

    @Override
    public void run() {
        super.run();
        Log.d(TAG, "STARTED");
        isRunning = true;
        while(isRunning){
            p1.move();
            try {
                tryDraw();
                this.wait(100);
            }catch(Exception e){
                // Do nothing on wait interrupt
            }
        }
    }
}
