package vn.asiantech.internship.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.canvas.MyCanvas;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/7/2017.
 */
public class CanvasActivity extends AppCompatActivity {
    MyCanvas myCanvas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
    }
}
