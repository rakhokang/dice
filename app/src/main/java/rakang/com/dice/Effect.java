package rakang.com.dice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

public class Effect {

    private String TAG = "AnimationEffects";
    private Context context;

    public Effect(Context context) {
        this.context = context;
    }

    public AnimatorSet RotateXY(View targetView, int time) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "rotation", 0, 360);
        animator.setRepeatCount(3);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.anim.accelerate_decelerate_interpolator));
        AnimatorSet rotateXY= new AnimatorSet();
        rotateXY.playTogether(animator);
        rotateXY.setDuration(time);
        return rotateXY;
    }

    public AnimatorSet ScaleXY(View targetView, int time, float scaleX, float scaleY) {
        ObjectAnimator _sacleX = ObjectAnimator.ofFloat(targetView, "scaleX", scaleX);
        ObjectAnimator _sacleY = ObjectAnimator.ofFloat(targetView, "scaleY", scaleY);
        AnimatorSet scaleXY = new AnimatorSet();
        scaleXY.playTogether(_sacleX, _sacleY);
        scaleXY.setDuration(time);
        return scaleXY;
    }

    public AnimatorSet ScaleUpDown(View targetView, int time){
        final AnimatorSet animScaleChange = new AnimatorSet();

        targetView.setScaleX(1f);
        targetView.setScaleY(1f);
        int halftime = time/2;

        AnimatorSet scaleUp= ScaleXY(targetView, 500, 3f, 3f);
        AnimatorSet scaleDown= ScaleXY(targetView, 500, 1f, 1f);

        animScaleChange.playSequentially(scaleUp, scaleDown);
        animScaleChange.setDuration(1000);
        return animScaleChange;
    }

    public ObjectAnimator FadeEffect(View targetView, int time, float startValue, float endValue) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "alpha", startValue, endValue);
        animator.setDuration(time);
        return animator;
    }

    public void FadeIn (View targetView) {
        final AnimatorSet animSetXY = new AnimatorSet();
        targetView.setAlpha(0f);
        ObjectAnimator fadeIn = FadeEffect(targetView, 300, 0f, 1f);
        animSetXY.playSequentially(fadeIn);
        animSetXY.start();
    }

    public AnimatorSet CubicAnim(View targetview, int time) {
        final AnimatorSet animSetCube = new AnimatorSet();

        AnimatorSet scaleChanger = ScaleUpDown(targetview, time);
        final AnimatorSet keepRotation = RotateXY(targetview, 500);

        animSetCube.playTogether(scaleChanger, keepRotation);
        animSetCube.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        return animSetCube;
    }
}
