package pacman.experiment.goodfish.com.pacmangameactivity.util;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by jessemacgregor on 3/28/15.
 */
public class PacManThread extends Thread {
    private final String TAG = "PACMAN_THREAD";
    private SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private Maze theMaze = new Maze(30, 33);
    private Character p1 = new PacMan(theMaze, Character.Dir.RIGHT,0xFFFFFF00);
    private Character g1 = new Ghost(theMaze, Character.Dir.RIGHT,0xFFFF0000, 1, 1);
    private Character g2 = new Ghost(theMaze, Character.Dir.RIGHT,0xFFFF7F00, 26, 1);
    private Character g3 = new Ghost(theMaze, Character.Dir.RIGHT,0xFF00FF00, 1, 28);
    private Character g4 = new Ghost(theMaze, Character.Dir.RIGHT,0xFF007FFF, 26, 28);
    private Character.Dir moveDir = Character.Dir.STOP;

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
        g1.draw(c, scale);
        g4.draw(c, scale);
        g2.draw(c, scale);
        g3.draw(c, scale);
        p1.draw(c, scale);
    }

    public void requestStop(){
        isRunning = false;
    }

    public void move(Character.Dir direction){
        p1.setNextDirection(direction);
    }

    @Override
    public void run() {
        super.run();
        Log.d(TAG, "STARTED");
        isRunning = true;
        while(isRunning){
            p1.move();
            g1.move();
            g2.move();
            g3.move();
            g4.move();
            try {
                tryDraw();
                this.wait(100);
            }catch(Exception e){
                // Do nothing on wait interrupt
            }
        }
    }
}
