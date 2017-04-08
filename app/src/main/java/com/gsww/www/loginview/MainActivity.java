package com.gsww.www.loginview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity implements View.OnLayoutChangeListener {
    private RelativeLayout root; //根布局
    private CircleImageView header;//圆形头像
    private LinearLayout ll_bottom;//底部布局
    private int changeHeight; //屏幕高度的1/3
    private ScrollView scrollView;//滑动布局
    public static final String TAG = "MainActivity";
    private float scale = 0.7f; //缩放比例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    /**
     * 绑定布局
     */
    private void bindView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        changeHeight = dm.heightPixels / 3;

        root = (RelativeLayout) findViewById(R.id.root);
        header = (CircleImageView) findViewById(R.id.header);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        scrollView = (ScrollView) findViewById(R.id.scroll);

        root.addOnLayoutChangeListener(this);//对根布局变化时候监听
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.e(TAG, "onLayoutChange:bottom " + bottom + "oldBottom" + oldBottom);
        final int change = bottom - oldBottom;//键盘弹出时候布局的变化值
        if (bottom != 0 && oldBottom != 0 && change > changeHeight) {//键盘落下
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, scrollView.getHeight());
                }
            });
            int length = change - changeHeight;
            ZoomOut(header, length);
            ll_bottom.setVisibility(View.VISIBLE);


        } else if (bottom != 0 && oldBottom != 0 && -change > changeHeight) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, scrollView.getHeight());
                }
            });
            int length = -change - changeHeight;
            Log.e(TAG, "onLayoutChange: length=" + length);
            ZoomIn(header, length);
            ll_bottom.setVisibility(View.GONE);

        }

    }

    /**
     * 图像放大下移
     *
     * @param view
     * @param length
     */
    private void ZoomOut(CircleImageView view, int length) {


        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();
    }

    /**
     * 头像缩小上移
     *
     * @param view
     * @param length
     */

    private void ZoomIn(CircleImageView view, int length) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0, -length);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();


    }
}
