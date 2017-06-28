package vn.asiantech.internship.Ultils;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by PC on 6/26/2017.
 */

public class ChangeDurationTimeViewPager extends Scroller {
    private int mDuration = 5000;

    public ChangeDurationTimeViewPager(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
