package com.animation;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;

import java.util.Arrays;
import java.util.List;

import com.animation.databinding.ActivityMainBinding;

/**
 * @author xingzhengyi
 * @date 2018/12/13
 */
public class MainActivity extends AppCompatActivity {

    private List<Sample> samples;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupWindowAnimations();
        setupSamples();
        setupToolbar();
        setupLayout();
    }

    private void setupLayout() {
        binding.sampleList.setHasFixedSize(true);
        binding.sampleList.setAdapter(new SamplesRecyclerAdapter(this, samples));
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @SuppressLint("ResourceType")
    private void setupSamples() {
        samples = Arrays.asList(
                new Sample(ContextCompat.getColor(this, R.color.sample_red), "Transitions"),
                new Sample(ContextCompat.getColor(this, R.color.sample_blue), "Shared Elements"),
                new Sample(ContextCompat.getColor(this, R.color.sample_green), "View animations"),
                new Sample(ContextCompat.getColor(this, R.color.sample_yellow), "Circular Reveal Animation")
        );
    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setReenterTransition(slide);
        getWindow().setExitTransition(slide);
    }
}
