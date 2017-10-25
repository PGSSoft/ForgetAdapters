package com.pgssoft.forgetadapters.adapters;

/**
 * Created by wsura on 18.10.2017.
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pgssoft.forgetadapters.common.IIdProvider;
import com.pgssoft.forgetadapters.common.IObservableCollection;
import com.pgssoft.forgetadapters.dataBinding.ListViewProvider;
import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

/**
 * Created by wsura on 13.10.2017.
 */

public class ListViewCollectionAdapter<TModel extends IIdProvider, TView extends View & IDataViewModelProvider<TModel>> extends BaseAdapter {

    private final IObservableCollection<TModel> collection;
    private final ListViewProvider<TModel, TView> provider;
    private final boolean stableIds;

    private final IObservableCollection.CollectionChangeListener collectionChangeListener = new IObservableCollection.CollectionChangeListener() {

        @Override
        public void onItemRangeChanged(IObservableCollection sender, int start, int count) {

            ListViewCollectionAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(IObservableCollection sender, int start, int count) {

            ListViewCollectionAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(IObservableCollection sender, int start, int count) {

            ListViewCollectionAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onChanged(IObservableCollection sender) {

            ListViewCollectionAdapter.this.notifyDataSetChanged();
        }
    };

    public ListViewCollectionAdapter(IObservableCollection<TModel> collection, ListViewProvider<TModel, TView> provider, boolean stableIds) {
        this.collection = collection;
        this.provider = provider;
        this.stableIds = stableIds;

        this.collection.addCollectionChangedListener(this.collectionChangeListener);
    }

    @Override
    public int getCount() {

        return collection.size();
    }

    @Override
    public Object getItem(int i) {

        return collection.get(i);
    }

    @Override
    public long getItemId(int i) {

        return collection.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (!provider.checkIsValidView(view)) {
            view = provider.createView();
        }

        ((TView)view).getViewModel().setData(collection.get(i));

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return stableIds;
    }

    public IObservableCollection getItems() {

        return collection;
    }
}
