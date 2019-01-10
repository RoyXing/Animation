package com.animation;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.animation.databinding.RowSampleBinding;

import java.util.List;

/**
 * @author roy.xing
 * @date 2018/12/12
 */
public class SamplesRecyclerAdapter extends RecyclerView.Adapter<SamplesRecyclerAdapter.SampleViewHolder> {

    private final Activity activity;
    private final List<Sample> samples;

    public SamplesRecyclerAdapter(Activity activity, List<Sample> samples) {
        this.activity = activity;
        this.samples = samples;
    }

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowSampleBinding binding = RowSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false);
        return new SampleViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final SampleViewHolder viewHolder, int i) {
        final Sample sample = samples.get(viewHolder.getAdapterPosition());
        viewHolder.binding.setSample(sample);
        viewHolder.binding.sampleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewHolder.getLayoutPosition()) {
                    case 0:
                        transitionToActivity(TransitionActivity1.class, sample);
                        break;
                    case 1:
                        transitionToActivity(SharedElementActivity.class, viewHolder, sample);
                        break;
                    case 2:
                        transitionToActivity(AnimationsActivity1.class, sample);
                        break;
                    case 3:
                        transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void transitionToActivity(Class sharedElementActivityClass, SampleViewHolder viewHolder, Sample sample) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(viewHolder.binding.sampleIcon, activity.getString(R.string.square_blue_name)),
                new Pair<>(viewHolder.binding.sampleName, activity.getString(R.string.sample_blue_title)));
        startActivity(sharedElementActivityClass, pairs, sample);
    }

    private void transitionToActivity(Class target, SampleViewHolder viewHolder, Sample sample, int transitionName) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(viewHolder.binding.sampleIcon, activity.getString(transitionName)));
        startActivity(target, pairs, sample);
    }

    private void transitionToActivity(Class transitionActivity1Class, Sample sample) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, true);
        startActivity(transitionActivity1Class, pairs, sample);
    }

    private void startActivity(Class target, Pair<View, String>[] pairs, Sample sample) {
        Intent i = new Intent(activity, target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        i.putExtra("sample", sample);
        activity.startActivity(i, transitionActivityOptions.toBundle());
    }

    @Override
    public int getItemCount() {
        return samples.size();
    }

    public class SampleViewHolder extends RecyclerView.ViewHolder {

        final RowSampleBinding binding;

        public SampleViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
