package pacman.experiment.goodfish.com.pacmangameactivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import pacman.experiment.goodfish.com.pacmangameactivity.util.PacManSurfaceView;
import pacman.experiment.goodfish.com.pacmangameactivity.util.PacManThread;
import pacman.experiment.goodfish.com.pacmangameactivity.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    final static String TAG = "FullscreenActivity";
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_fullscreen);
        getActionBar().hide();

        final PacManSurfaceView surfaceView = (PacManSurfaceView) findViewById(R.id.surfaceView);
        final Button upButton = (Button) findViewById(R.id.upButton);
        final Button downButton = (Button) findViewById(R.id.downButton);
        final Button leftButton = (Button) findViewById(R.id.leftButton);
        final Button rightButton = (Button) findViewById(R.id.rightButton);

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceView.moveUp();
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceView.moveDown();
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceView.moveLeft();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceView.moveRight();
            }
        });

    }

}
