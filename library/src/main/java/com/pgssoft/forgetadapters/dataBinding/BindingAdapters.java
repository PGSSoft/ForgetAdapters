package com.pgssoft.forgetadapters.dataBinding;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.pgssoft.forgetadapters.adapters.ListViewAdapter;
import com.pgssoft.forgetadapters.adapters.RecyclerViewAdapter;
import com.pgssoft.forgetadapters.common.IIdProvider;
import com.pgssoft.forgetadapters.common.IObservableCollection;
import com.pgssoft.forgetadapters.common.ObservableArrayListWrapper;
import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

/**
 * Created by wsura on 13.10.2017.
 */

public class BindingAdapters {

    @BindingAdapter(value = {"items", "viewProvider"}, requireAll = true)
    public static <TModel, TView extends View & IDataViewModelProvider<TModel>> void setItems(RecyclerView recyclerView, ObservableArrayList<TModel> items, RecyclerViewProvider<TModel, TView> viewProvider) {

        if (recyclerView.getAdapter() != null &&
                recyclerView.getAdapter() instanceof RecyclerViewAdapter &&
                ((RecyclerViewAdapter)recyclerView.getAdapter()).getItems() instanceof ObservableArrayListWrapper &&
                ((ObservableArrayListWrapper)((RecyclerViewAdapter)recyclerView.getAdapter()).getItems()).getItems() == items)
            return;

        ObservableArrayListWrapper<TModel> wrapper = new ObservableArrayListWrapper<>(items);
        RecyclerViewAdapter<TModel, TView> adapter = new RecyclerViewAdapter<TModel, TView>(wrapper, viewProvider);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"items", "viewProvider"}, requireAll = true)
    public static <TModel, TView extends View & IDataViewModelProvider<TModel>> void setItems(RecyclerView recyclerView, IObservableCollection<TModel> items, RecyclerViewProvider<TModel, TView> viewProvider) {

        RecyclerViewAdapter<TModel, TView> adapter = new RecyclerViewAdapter<TModel, TView>(items, viewProvider);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"items", "viewProvider"}, requireAll = true)
    public static <TModel extends IIdProvider, TView extends View & IDataViewModelProvider<TModel>> void setItems(ListView listView, ObservableArrayList<TModel> items, ListViewProvider<TModel, TView> viewProvider) {

        if (listView.getAdapter() != null &&
                listView.getAdapter() instanceof ListViewAdapter &&
                ((ListViewAdapter)listView.getAdapter()).getItems() instanceof ObservableArrayListWrapper &&
                ((ObservableArrayListWrapper)((ListViewAdapter)listView.getAdapter()).getItems()).getItems() == items)
            return;

        ObservableArrayListWrapper<TModel> wrapper = new ObservableArrayListWrapper<>(items);
        ListViewAdapter<TModel, TView> adapter = new ListViewAdapter<TModel, TView>(wrapper, viewProvider, false);
        listView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"items", "viewProvider"}, requireAll = true)
    public static <TModel extends IIdProvider, TView extends View & IDataViewModelProvider<TModel>> void setItems(ListView listView, IObservableCollection<TModel> items, ListViewProvider<TModel, TView> viewProvider) {

        ListViewAdapter<TModel, TView> adapter = new ListViewAdapter<TModel, TView>(items, viewProvider, false);
        listView.setAdapter(adapter);
    }

}
