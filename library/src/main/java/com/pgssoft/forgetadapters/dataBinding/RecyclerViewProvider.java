package com.pgssoft.forgetadapters.dataBinding;

import android.view.View;

import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

/**
 * Created by wsura on 13.10.2017.
 */

public abstract class RecyclerViewProvider<TModel, TView extends View & IDataViewModelProvider<TModel>> {

    public abstract TView createView(int viewType);

    public int getTypeFor(TModel data) {
        return 0;
    }

    public boolean hasStableIds() {
        return false;
    }
}
