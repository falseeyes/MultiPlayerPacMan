package pacman.experiment.goodfish.com.pacmangameactivity.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by jessemacgregor on 3/26/15.
 */
public class PacManSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    final static String TAG = "PacManSurfaceView";
    private PacManThread game;

    public PacManSurfaceView(Context context) {
        super(context);
        init();
    }

    public PacManSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PacManSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setVisibility(VISIBLE);
        getHolder().addCallback(this);
        game = new PacManThread(getHolder());
        Log.d(TAG, "init");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        tryToDraw();
        game.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
        tryToDraw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        game.requestStop();
    }

    public void moveUp(){game.move(Character.Dir.UP);}
    public void moveDown(){game.move(Character.Dir.DOWN);}
    public void moveLeft(){game.move(Character.Dir.LEFT);}
    public void moveRight(){game.move(Character.Dir.RIGHT);}
    public void moveStop(){game.move(Character.Dir.STOP);}

    private void tryToDraw(){
        Log.d(TAG, "tryToDraw");
        Canvas c = getHolder().lockCanvas();
        if(c != null){
            Log.d(TAG, "tryToDraw.ACQUIRED_CANVAS!");
            drawStuff(c);
            getHolder().unlockCanvasAndPost(c);
        }
    }

    private void drawStuff(Canvas c){
        c.drawARGB(0xFF, 0xFF, 0, 0);
    }
}

