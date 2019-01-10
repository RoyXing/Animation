package com.animation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.View;

import com.animation.databinding.ActivityTransition1Binding;

public class TransitionActivity1 extends BaseActivity {

    private Sample sample;
    private ActivityTransition1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setupWindowAnimations();
        setupLayout();
    }

    private void bindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transition1);
        sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setTransition1Sample(sample);
    }

    private void setupWindowAnimations() {
        Visibility visibility = buildEnterTransition();
        getWindow().setEnterTransition(visibility);
    }

    private void setupLayout() {
        binding.sample1Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitionActivity1.this, TransitionActivity2.class);
                intent.putExtra(EXTRA_SAMPLE, sample);
                intent.putExtra(EXTRA_TYPE, TYPE_PROGRAMMATICALLY);
                transitionTo(intent);
            }
        });
        binding.sample1Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitionActivity1.this, TransitionActivity2.class);
                intent.putExtra(EXTRA_SAMPLE, sample);
                intent.putExtra(EXTRA_TYPE, TYPE_XML);
                transitionTo(intent);
            }
        });
        binding.sample1Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitionActivity1.this, TransitionActivity3.class);
                intent.putExtra(EXTRA_SAMPLE, sample);
                intent.putExtra(EXTRA_TYPE, TYPE_PROGRAMMATICALLY);
                transitionTo(intent);
            }
        });
        binding.sample1Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitionActivity1.this, TransitionActivity3.class);
                intent.putExtra(EXTRA_SAMPLE, sample);
                intent.putExtra(EXTRA_TYPE, TYPE_XML);
                transitionTo(intent);
            }
        });
        binding.sample1Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visibility returnTransition = buildReturnTransition();
                getWindow().setReturnTransition(returnTransition);

                finishAfterTransition();
            }
        });
        binding.sample1Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    private Visibility buildEnterTransition() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        enterTransition.excludeTarget(R.id.square_red, false);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }

}
