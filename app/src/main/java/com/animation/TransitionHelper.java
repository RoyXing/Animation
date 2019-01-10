package com.animation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author roy.xing
 * @date 2018/12/13
 */
public class TransitionHelper {

    public static Pair<View, String>[] createSafeTransitionParticipants(@NonNull Activity activity,
            boolean includeStatusBar,
            @NonNull Pair... otherParticipants) {
        View decorView = activity.getWindow().getDecorView();
        View statusBar = null;
        if (includeStatusBar) {
            statusBar = decorView.findViewById(android.R.id.statusBarBackground);
        }
        View navBar = decorView.findViewById(android.R.id.navigationBarBackground);

        List<Pair> participants = new ArrayList<>(3);
        addNonNullViewToTransitionParticipants(statusBar, participants);
        addNonNullViewToTransitionParticipants(navBar, participants);

        if (otherParticipants != null && !(otherParticipants.length == 1 && otherParticipants[0] == null)) {
            participants.addAll(Arrays.asList(otherParticipants));
        }
        return participants.toArray(new Pair[participants.size()]);
    }

    private static void addNonNullViewToTransitionParticipants(View view, List<Pair> participants) {
        if (view == null) {
            return;
        }
        participants.add(new Pair<>(view, view.getTransitionName()));
    }
}
