package com.pgssoft.forgetAdaptersDemo.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.pgssoft.forgetAdaptersDemo.models.PersonModel;
import com.pgssoft.forgetAdaptersDemo.viewmodels.interfaces.IPersonViewAccess;
import com.pgssoft.forgetadapters.viewmodels.base.IDataViewModel;

public class PersonViewModel extends BaseObservable implements IDataViewModel<PersonModel> {

    // Private fields ---------------------------------------------------------

    private IPersonViewAccess access;
    private final ObservableField<String> name;
    private final ObservableField<String> surname;
    private final ObservableField<String> company;

    // Public methods ---------------------------------------------------------

    public PersonViewModel(IPersonViewAccess access) {

        this.access = access;
        name = new ObservableField<String>();
        surname = new ObservableField<String>();
        company = new ObservableField<String>();
    }

    // Public properties ------------------------------------------------------

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getSurname() {
        return surname;
    }

    public ObservableField<String> getCompany() {
        return company;
    }

    @Override
    public void setData(PersonModel data) {
        name.set(data.getName());
        surname.set(data.getSurname());
        company.set(data.getCompany());
    }
}
