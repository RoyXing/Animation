package com.animation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.animation.databinding.ActivityAnimations1Binding;

/**
 * @author roy.xing
 * @date 2019/1/10
 */
public class AnimationsActivity1 extends BaseActivity {

    ActivityAnimations1Binding binding;
    private Sample sample;
    private boolean sizeChanged;
    private boolean positionChanged;
    private int savedWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setupWindowAnimations();
        setupLayout();
        setupToolbar();
    }

    private void bindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animations1);
        sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setAnimationsSample(sample);
    }

    private void setupWindowAnimations() {
        getWindow().setReenterTransition(new Fade());
    }

    private void setupLayout() {

        findViewById(R.id.sample3_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayout();
            }
        });
        findViewById(R.id.sample3_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosition();
            }
        });

        findViewById(R.id.sample3_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationsActivity1.this, AnimationsActivity2.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                transitionTo(i);
            }
        });
    }

    private void changeLayout() {
        TransitionManager.beginDelayedTransition(binding.sample3Root);

        ViewGroup.LayoutParams layoutParams = binding.squareGreen.getLayoutParams();
        if (sizeChanged) {
            layoutParams.width = savedWidth;
        } else {
            savedWidth = layoutParams.width;
            layoutParams.width = 200;
        }
        sizeChanged = !sizeChanged;
        binding.squareGreen.setLayoutParams(layoutParams);
    }

    private void changePosition() {
        TransitionManager.beginDelayedTransition(binding.sample3Root);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.squareGreen.getLayoutParams();
        if (positionChanged) {
            layoutParams.gravity = Gravity.CENTER;
        } else {
            layoutParams.gravity = Gravity.LEFT;
        }
        positionChanged = !positionChanged;
        binding.squareGreen.setLayoutParams(layoutParams);
    }
}
