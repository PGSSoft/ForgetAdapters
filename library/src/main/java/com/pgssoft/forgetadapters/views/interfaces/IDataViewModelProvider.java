package com.pgssoft.forgetadapters.views.interfaces;

import com.pgssoft.forgetadapters.viewmodels.base.IDataViewModel;

/**
 * Created by wsura on 13.10.2017.
 */

public interface IDataViewModelProvider<TModel> {

    IDataViewModel<TModel> getViewModel();
}