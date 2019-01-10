package com.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.animation.databinding.ActivityRevealBinding;

/**
 * @author roy.xing
 * @date 2019/1/10
 */
public class RevealActivity extends BaseActivity implements View.OnTouchListener {

    private static final int DELAY = 100;
    private Interpolator interpolator;
    private ActivityRevealBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setupWindowAnimations();
        setupLayout();
    }

    private void setupLayout() {
        binding.squareGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealGreen();
            }
        });

        binding.squareRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealRed();
            }
        });

        binding.squareBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealBlue();
            }
        });
        findViewById(R.id.square_yellow).setOnTouchListener(this);
    }

    private void revealBlue() {
        animateButtonsOut();
        Animator animator = animateRevealColorFromCoordinates(binding.revealRoot, R.color.sample_blue, binding.revealRoot.getWidth() / 2, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animateButtonsIn();
            }
        });
        binding.sampleBody.setText(R.string.reveal_body4);
        binding.sampleBody.setTextColor(ContextCompat.getColor(this, R.color.theme_blue_background));
    }

    private void animateButtonsOut() {
        for (int i = 0; i < binding.revealRoot.getChildCount(); i++) {
            View childAt = binding.revealRoot.getChildAt(i);
            childAt.animate()
                    .setStartDelay(i)
                    .setInterpolator(interpolator)
                    .alpha(0)
                    .scaleX(0)
                    .scaleY(0);
        }
    }

    private void revealRed() {
        final ViewGroup.LayoutParams params = binding.squareRed.getLayoutParams();
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealColor(binding.revealRoot, R.color.sample_red);
                binding.sampleBody.setText(R.string.reveal_body3);
                binding.sampleBody.setTextColor(ContextCompat.getColor(RevealActivity.this, R.color.theme_red_background));
                binding.squareRed.setLayoutParams(params);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        TransitionManager.beginDelayedTransition(binding.revealRoot, transition);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        binding.squareRed.setLayoutParams(layoutParams);
    }

    private void revealGreen() {
        animateRevealColor(binding.revealRoot, R.color.sample_green);
    }

    private void animateRevealColor(RelativeLayout viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        animateRevealColorFromCoordinates(viewRoot, color, cx, cy);
    }

    private Animator animateRevealColorFromCoordinates(RelativeLayout viewRoot, @ColorRes int color, int cx, int cy) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        animator.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        return animator;
    }

    private void bindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reveal);
        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setReveal1Sample(sample);
    }

    private void setupWindowAnimations() {
        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        setupEnterAnimations();
        setupExitAnimations();
    }

    private void setupEnterAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                hideTarget();
                animateRevealShow();
                animateButtonsIn();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animateButtonsIn() {
        for (int i = 0; i < binding.revealRoot.getChildCount(); i++) {
            View childAt = binding.revealRoot.getChildAt(i);
            childAt.animate()
                    .setStartDelay(100 + i * DELAY)
                    .setInterpolator(interpolator)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);
        }
    }

    private void animateRevealShow() {
        View viewRoot = binding.toolbar;
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    private void hideTarget() {
        binding.sharedTarget.setVisibility(View.GONE);

    }

    private void setupExitAnimations() {
        Fade returnTransition = new Fade();
        getWindow().setReturnTransition(returnTransition);
        returnTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        returnTransition.setStartDelay(getResources().getInteger(R.integer.anim_duration_medium));
        returnTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                animateButtonsOut();
                animateRevealHide();
            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animateRevealHide() {
        final View viewRoot = binding.revealRoot;
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int initialRadius = viewRoot.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewRoot.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        anim.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (view.getId() == R.id.square_yellow) {
                revealYellow(event.getRawX(), event.getRawY());
            }
        }
        return false;
    }

    private void revealYellow(float rawX, float rawY) {
        animateRevealColorFromCoordinates(binding.revealRoot,R.color.sample_yellow,(int)rawX,(int)rawY);
        binding.sampleBody.setText(R.string.reveal_body2);
        binding.sampleBody.setTextColor(ContextCompat.getColor(this, R.color.theme_green_background));
    }
}
