package com.pgssoft.forgetAdaptersDemo.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.pgssoft.forgetAdaptersDemo.R;
import com.pgssoft.forgetAdaptersDemo.databinding.ViewPersonBinding;
import com.pgssoft.forgetAdaptersDemo.models.PersonModel;
import com.pgssoft.forgetAdaptersDemo.viewmodels.PersonViewModel;
import com.pgssoft.forgetAdaptersDemo.viewmodels.interfaces.IPersonViewAccess;
import com.pgssoft.forgetadapters.viewmodels.base.IDataViewModel;
import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

public class PersonView extends FrameLayout implements IPersonViewAccess, IDataViewModelProvider<PersonModel> {

    // Private fields ---------------------------------------------------------

    private ViewPersonBinding binding;
    private PersonViewModel viewModel;

    // Public methods ---------------------------------------------------------

    public PersonView(Context context) {

        super(context);

        this.binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_person, this, true);
        this.viewModel = new PersonViewModel(this);
        this.binding.setViewModel(viewModel);
    }

    @Override
    public IDataViewModel<PersonModel> getViewModel() {
        return viewModel;
    }
}