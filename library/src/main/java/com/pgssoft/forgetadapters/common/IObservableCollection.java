package com.pgssoft.forgetadapters.common;

/**
 * Created by wsura on 13.10.2017.
 */

public interface IObservableCollection<T> {

    interface CollectionChangeListener {

        void onItemRangeChanged(IObservableCollection sender, int start, int count);
        void onItemRangeInserted(IObservableCollection sender, int start, int count);
        void onItemRangeRemoved(IObservableCollection sender, int start, int count);
        void onChanged(IObservableCollection sender);
    }

    int size();
    T get(int index);
    void addCollectionChangedListener(CollectionChangeListener listener);
    void removeCollectionChangedListener(CollectionChangeListener listener);
}