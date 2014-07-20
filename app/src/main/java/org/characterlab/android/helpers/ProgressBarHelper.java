package org.characterlab.android.helpers;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;

/**
 * Created by mandar.b on 7/18/2014.
 */
public class ProgressBarHelper {

    private Activity context;

    // below are defined in layout/progress_bar.xml which is included in individual activity/fragment
    private LinearLayout llProgressBar;
    private ImageView wvProgresssBar;
    ObjectAnimator animator;
//    private WebView wvProgresssBar;

    // this should be called from onCreate before setContentView is called.
    public ProgressBarHelper(Activity context) {
        this.context = context;

        if (CharacterLabApplication.isActionBarBasedProgressBar()) {
            this.context.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        }
    }

    public void setupProgressBarViews(View containerView) {
        if (CharacterLabApplication.isActionBarBasedProgressBar()) {
            return;
        }

        try {
            llProgressBar = (LinearLayout) containerView.findViewById(R.id.llProgressBar);
            wvProgresssBar = (ImageView) containerView.findViewById(R.id.wvProgressBar);
//            wvProgresssBar = (WebView) containerView.findViewById(R.id.wvProgressBar);
//            wvProgresssBar.loadUrl("file:///android_asset/cl_progress.gif");
        } catch (Exception e) {
            Log.d("debug", "Could not find custom Progress bar components in container " + containerView.getId());
            e.printStackTrace();
        }
    }

    public void setupProgressBarViews(Activity activity) {
        if (CharacterLabApplication.isActionBarBasedProgressBar()) {
            return;
        }

        try {
            llProgressBar = (LinearLayout) activity.findViewById(R.id.llProgressBar);
            wvProgresssBar = (ImageView) activity.findViewById(R.id.wvProgressBar);
        } catch (Exception e) {
            Log.d("debug", "Could not find custom Progress bar components in container activity.");
            e.printStackTrace();
        }
    }

    public void showProgressBar() {
        if (!CharacterLabApplication.isActionBarBasedProgressBar()) {
            if (llProgressBar != null && wvProgresssBar != null) {
                llProgressBar.setVisibility(View.VISIBLE);

                animator = ObjectAnimator.ofFloat(wvProgresssBar, "rotation", 0.0f, 360.0f);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.setDuration(750);
                animator.start();
            }
        } else {
            context.setProgressBarIndeterminateVisibility(true);
        }
    }

    public void hideProgressBar() {
        if (!CharacterLabApplication.isActionBarBasedProgressBar()) {
            if (llProgressBar != null) {
                llProgressBar.setVisibility(View.GONE);

                if (animator != null) {
                    animator.setRepeatCount(0);
                    animator.cancel();
                }

//                animator = ObjectAnimator.ofFloat(wvProgresssBar, "scaleX", 0.0f, 360.0f)
//                                         .ofFloat(wvProgresssBar, "scaleY", 0.0f, 360.0f)
//                                         .ofFloat(wvProgresssBar, "alpha", 1.0f, 0.0f);
//                animator.setRepeatCount(0);
//                animator.setDuration(750);
//                animator.start();
            }
        } else {
            context.setProgressBarIndeterminateVisibility(false);
        }
    }
}
