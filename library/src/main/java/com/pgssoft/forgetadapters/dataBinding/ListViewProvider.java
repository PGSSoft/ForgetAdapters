package com.pgssoft.forgetadapters.dataBinding;

import android.view.View;

import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

/**
 * Created by wsura on 13.10.2017.
 */

public abstract class ListViewProvider<TModel, TView extends View & IDataViewModelProvider<TModel>> {

    public abstract boolean checkIsValidView(View view);
    public abstract TView createView();
}
