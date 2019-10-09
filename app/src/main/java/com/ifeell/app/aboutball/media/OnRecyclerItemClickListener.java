package com.ifeell.app.aboutball.media;

import androidx.annotation.NonNull;
import android.view.View;

public interface OnRecyclerItemClickListener {
    void itemClick(@NonNull View view, int position);
}
