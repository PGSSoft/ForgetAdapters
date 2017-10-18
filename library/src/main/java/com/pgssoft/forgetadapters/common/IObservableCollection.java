package com.pgssoft.forgetadapters.common;

/**
 * Created by wsura on 13.10.2017.
 */

public interface IObservableCollection<T> {

    interface CollectionChangeListener {

        void collectionChanged();
    }

    int count();
    T getItem(int index);
    void addCollectionChangedListener(CollectionChangeListener listener);
    void removeCollectionChangedListener(CollectionChangeListener listener);
}