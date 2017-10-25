package com.pgssoft.forgetadapters.dataBinding;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.pgssoft.forgetadapters.adapters.ListViewCollectionAdapter;
import com.pgssoft.forgetadapters.adapters.ListViewListAdapter;
import com.pgssoft.forgetadapters.adapters.RecyclerViewCollectionAdapter;
import com.pgssoft.forgetadapters.adapters.RecyclerViewListAdapter;
import com.pgssoft.forgetadapters.common.IIdProvider;
import com.pgssoft.forgetadapters.common.IObservableCollection;
import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

/**
 * Created by wsura on 13.10.2017.
 */

public class BindingAdapters {

    @BindingAdapter(value = {"items", "viewProvider"}, requireAll = true)
    public static <TModel, TView extends View & IDataViewModelProvider<TModel>> void setItems(RecyclerView recyclerView, ObservableArrayList<TModel> items, RecyclerViewProvider<TModel, TView> viewProvider) {

        if (recyclerView.getAdapter() != null &&
                recyclerView.getAdapter() instanceof RecyclerViewListAdapter &&
                ((RecyclerViewListAdapter)recyclerView.getAdapter()).getList() == items)
            return;

        RecyclerViewListAdapter<TModel, TView> adapter = new RecyclerViewListAdapter<>(items, viewProvider);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"items", "viewProvider"}, requireAll = true)
    public static <TModel, TView extends View & IDataViewModelProvider<TModel>> void setItems(RecyclerView recyclerView, IObservableCollection<TModel> items, RecyclerViewProvider<TModel, TView> viewProvider) {

        RecyclerViewCollectionAdapter<TModel, TView> adapter = new RecyclerViewCollectionAdapter<TModel, TView>(items, viewProvider);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"items", "viewProvider"}, requireAll = true)
    public static <TModel extends IIdProvider, TView extends View & IDataViewModelProvider<TModel>> void setItems(ListView listView, ObservableArrayList<TModel> items, ListViewProvider<TModel, TView> viewProvider) {

        if (listView.getAdapter() != null &&
                listView.getAdapter() instanceof ListViewCollectionAdapter &&
                ((ListViewCollectionAdapter)listView.getAdapter()).getItems() == items)
            return;

        ListViewListAdapter<TModel, TView> adapter = new ListViewListAdapter<TModel, TView>(items, viewProvider, false);
        listView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"items", "viewProvider"}, requireAll = true)
    public static <TModel extends IIdProvider, TView extends View & IDataViewModelProvider<TModel>> void setItems(ListView listView, IObservableCollection<TModel> items, ListViewProvider<TModel, TView> viewProvider) {

        ListViewCollectionAdapter<TModel, TView> adapter = new ListViewCollectionAdapter<TModel, TView>(items, viewProvider, false);
        listView.setAdapter(adapter);
    }

}
