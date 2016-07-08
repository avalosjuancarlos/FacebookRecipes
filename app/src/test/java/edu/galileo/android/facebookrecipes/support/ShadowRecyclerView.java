package edu.galileo.android.facebookrecipes.support;

import android.support.v7.widget.RecyclerView;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowViewGroup;

/**
 * Created by avalo.
 */
@Implements(RecyclerView.class)
public class ShadowRecyclerView extends ShadowViewGroup {
    private int smoothScrollToPosition = -1;

    @Implementation
    public void smoothScrollToPosition(int pos){
        setSmoothScrollToPosition(pos);
    }

    public int getSmoothScrollToPosition() {
        return this.smoothScrollToPosition;
    }

    public void setSmoothScrollToPosition(int smoothScrollToPosition) {
        this.smoothScrollToPosition = smoothScrollToPosition;
    }
}
