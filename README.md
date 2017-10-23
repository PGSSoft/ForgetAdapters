![pgssoft-logo.png](Assets/pgssoft-logo.png)

# ForgetAdapters
An Android library, which (extremely) simplifies seeding lists with data

No more adapters. You're gonna love this.

# Introduction

Implementing lists in Android requires much boilerplate code. Each time you create new list, you need to create a new adapter, which seeds that list with data. Fortunately, since Google introduced Data Binding Library, this process may be simplified. A lot.

We dare say, that we bring that boilerplate code to absolute minimum (minus code you'd have to write anyway). Catch? **We assume, that the application is being written according to MVVM pattern.**

With our library you will be able to display list with the following code:

        <android.support.v7.widget.RecyclerView
            android:id="@+id/m_list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:items="@{viewModel.people}"
            app:viewProvider="@{viewProvider}"/>
			
`app:items` allows you to provide an ObservableArrayList with data and `app:viewProvider` - a ViewProvider object, which will instantiate views for list items.

You can find a complete tutorial below.

# How to use

TODO - add jitpack/maven link

**Keep in mind, that this is a complete tutorial how to implement displaying list in MVVM Android application with our library**. The actual boilerplate code does not exceed 10 lines.

## Create a model you want to display

First of all, let's create a model, which will populate our list.

```java
public class PersonModel {

    private String name;
    private String surname;
    private String company;

    public PersonModel() {

    }

    public PersonModel(String name, String surname, String company) {

        this.name = name;
        this.surname = surname;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
```

## MVVM Activity

Create MVVM activity with convigured viewmodel and bindings. You can do that simply with our [Android MVVM templates](https://github.com/PGSSoft/AndroidMvvmTemplates).

Activity code may look like following:

```java
public class MainActivity extends AppCompatActivity implements IMainActivityAccess {

    // Private fields ---------------------------------------------------------

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    // Protected methods ------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new MainActivityViewModel(this);
        binding.setViewModel(viewModel);
    }
}
```

## Create list of models

We need a list in which we will keep our models. ObservableArrayList allows the binding library track changes to list's items.

```java
public class MainActivityViewModel extends BaseObservable {

    // Private fields ---------------------------------------------------------

    private IMainActivityAccess access;
    private ObservableArrayList<PersonModel> people;

    public MainActivityViewModel(IMainActivityAccess access) {

        this.access = access;

        people = new ObservableArrayList<>();
        people.add(new PersonModel("Wojciech", "Sura", "PGS Software S.A."));
        people.add(new PersonModel("Bill", "Gates", "Microsoft"));
        people.add(new PersonModel("Steve", "Jobs", "Apple"));
    }
	
    public ObservableArrayList<PersonModel> getPeople() {
        
        return people;
    }	
}
```

## View for displaying model

We need to define, how a single item will look on the list. For that, create a MVVM View ([Android MVVM templates](https://github.com/PGSSoft/AndroidMvvmTemplates) provides a template for that as well)

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="viewModel"
            type="com.pgssoft.forgetAdaptersDemo.viewmodels.PersonViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@{viewModel.name}"/>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@{viewModel.surname}"/>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@{viewModel.company}"/>

    </LinearLayout>
</layout>
```

Obviously, we need to provide appropriate values for bound fields, so let's fill view's viewmodel:

```java
public class PersonViewModel extends BaseObservable {

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
}
```

## Attaching data source

So far we wrote only code, which needs to be written anyway. Let's now use our ForgetAdapters library. Place RecyclerView on the activity.

```xml
(...)
    <data>

        <variable
            name="viewModel"
            type="com.pgssoft.forgetAdaptersDemo.viewmodels.MainActivityViewModel" />
        <variable
            name="viewProvider"
            type="com.pgssoft.forgetadapters.dataBinding.RecyclerViewProvider" />
    </data>

(...)

        <android.support.v7.widget.RecyclerView
            android:id="@+id/m_list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:items="@{viewModel.people}"
            app:viewProvider="@{viewProvider}"/>
```

This is the way we provide RecyclerView with data source (app:items) and a way to generate views (app:viewProvider). Since ViewModel already provides ObservableArray with our models through people property, we only need to implement the ViewProvider.

Don't forget to set LayoutManager or otherwise RecyclerView won't show anything.

```java
public class MainActivity extends AppCompatActivity implements IMainActivityAccess {

    (...)

    private RecyclerViewProvider<PersonModel, PersonView> viewProvider = new RecyclerViewProvider<PersonModel, PersonView>() {
        @Override
        public PersonView createView(int viewType) {
            return new PersonView(MainActivity.this);
        }
    };
	
	(...)
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {

		(...)
        binding.setViewProvider(viewProvider);
        binding.mList.setLayoutManager(new LinearLayoutManager(this));
		(...)
    }
```

## Some additional changes

ViewProvider works correctly, when provided view can give access to its viewmodel. We can do that by implementing IDataViewModelProvider in our PersonView:

```java
public class PersonView extends FrameLayout implements IPersonViewAccess, IDataViewModelProvider<PersonModel> {

(...)

    @Override
    public IDataViewModel<PersonModel> getViewModel() {
        return viewModel;
    }
}
```

Also, view's viewmodel needs to provide a way to be filled with provided model. We can do that by implementing IDataViewModel<PersonModel> in view's viewmodel.

```java
public class PersonViewModel extends BaseObservable implements IDataViewModel<PersonModel> {

    (...)

    @Override
    public void setData(PersonModel data) {
        name.set(data.getName());
        surname.set(data.getSurname());
        company.set(data.getCompany());
    }
}
```

## Run the application

This is all - we can now run the application and see list's contents displayed in the RecyclerView. What's important, all changes in the underlying ObservableArrayList will be automatically transferred to the activity.

**What's most important, no adapters were written during writing this demo application**. We also managed to push boilerplate code to the absolute minimum: additional attributes in layout (2 lines), implementation of ViewProvider (6 lines) and setting it in activity (1 line).

If you don't like RecyclerView, very similar mechanism is ready for ListView - just use ListViewProvider instead.

# Contributing

Bug reports and pull requests are welcome on GitHub at [https://github.com/PGSSoft/ForgetAdapters](https://github.com/PGSSoft/ForgetAdapters).

# License

The project is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).

# About

The project is maintained by [software development agency](https://www.pgs-soft.com/) [PGS Software](https://www.pgs-soft.com/).
See our other [open-source projects](https://github.com/PGSSoft) or [contact us](https://www.pgs-soft.com/contact-us/) to develop your product.

# Follow us

[![Twitter URL](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=https://github.com/PGSSoft/ForgetAdapters)  
[![Twitter Follow](https://img.shields.io/twitter/follow/pgssoftware.svg?style=social&label=Follow)](https://twitter.com/pgssoftware)