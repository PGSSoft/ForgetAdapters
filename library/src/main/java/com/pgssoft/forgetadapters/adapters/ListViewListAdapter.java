package com.pgssoft.forgetadapters.adapters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pgssoft.forgetadapters.common.IIdProvider;
import com.pgssoft.forgetadapters.dataBinding.ListViewProvider;
import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

import java.util.List;

/**
 * Created by wsura on 25.10.2017.
 */

public class ListViewListAdapter<TModel extends IIdProvider, TView extends View & IDataViewModelProvider<TModel>> extends BaseAdapter {

    private final ObservableArrayList<TModel> list;
    private final ListViewProvider<TModel, TView> provider;
    private final boolean stableIds;

    private final ObservableList.OnListChangedCallback listChangedCallback = new ObservableList.OnListChangedCallback() {
        @Override
        public void onChanged(ObservableList observableList) {

            ListViewListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList observableList, int i, int i1) {

            ListViewListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(ObservableList observableList, int i, int i1) {

            ListViewListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {

            ListViewListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {

            ListViewListAdapter.this.notifyDataSetChanged();
        }
    };

    public ListViewListAdapter(ObservableArrayList<TModel> list, ListViewProvider<TModel, TView> provider, boolean stableIds) {
        this.list = list;
        this.provider = provider;
        this.stableIds = stableIds;

        this.list.addOnListChangedCallback(listChangedCallback);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int i) {

        return list.get(i);
    }

    @Override
    public long getItemId(int i) {

        return list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (!provider.checkIsValidView(view)) {
            view = provider.createView();
        }

        ((TView)view).getViewModel().setData(list.get(i));

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return stableIds;
    }

    public ObservableArrayList<TModel> getItems() {

        return list;
    }
}
