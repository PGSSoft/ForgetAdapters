package com.pgssoft.forgetAdaptersDemo.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pgssoft.forgetAdaptersDemo.R;
import com.pgssoft.forgetAdaptersDemo.databinding.ActivityMainBinding;
import com.pgssoft.forgetAdaptersDemo.viewmodels.MainActivityViewModel;
import com.pgssoft.forgetAdaptersDemo.viewmodels.interfaces.IMainActivityAccess;

public class MainActivity extends AppCompatActivity implements IMainActivityAccess {

    // Private fields ---------------------------------------------------------

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    // Private methods --------------------------------------------------------

    private void initializeControls() {

        setSupportActionBar(binding.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    // Protected methods ------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new MainActivityViewModel(this);
        binding.setViewModel(viewModel);

        initializeControls();
    }
}