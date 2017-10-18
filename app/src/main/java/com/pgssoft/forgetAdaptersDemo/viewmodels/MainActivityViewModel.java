package com.pgssoft.forgetAdaptersDemo.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.pgssoft.forgetAdaptersDemo.viewmodels.interfaces.IMainActivityAccess;

public class MainActivityViewModel extends BaseObservable {

    // Private fields ---------------------------------------------------------

    private IMainActivityAccess access;

    public MainActivityViewModel(IMainActivityAccess access) {

        this.access = access;
    }
}
