package com.pgssoft.forgetadapters.common;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;

/**
 * Created by wsura on 13.10.2017.
 */

public class ObservableArrayListWrapper<T> implements IObservableCollection<T> {

    private final ObservableArrayList<T> items;
    private final ArrayList<CollectionChangeListener> collectionChangeListeners = new ArrayList<>();

    private final ObservableList.OnListChangedCallback listChangedCallback = new ObservableList.OnListChangedCallback() {
        @Override
        public void onChanged(ObservableList observableList) {
            notifyCollectionChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList observableList, int i, int i1) {
            notifyCollectionChanged();
        }

        @Override
        public void onItemRangeInserted(ObservableList observableList, int i, int i1) {
            notifyCollectionChanged();
        }

        @Override
        public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {
            notifyCollectionChanged();
        }

        @Override
        public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {
            notifyCollectionChanged();
        }
    };

    private void notifyCollectionChanged() {

        for (CollectionChangeListener listener : collectionChangeListeners) {
            listener.collectionChanged();
        }
    }

    public ObservableArrayListWrapper(ObservableArrayList<T> items) {

        this.items = items;
        this.items.addOnListChangedCallback(listChangedCallback);
    }

    @Override
    public int count() {

        return items.size();
    }

    @Override
    public T getItem(int index) {

        return items.get(index);
    }

    @Override
    public void addCollectionChangedListener(CollectionChangeListener listener) {

        collectionChangeListeners.add(listener);
    }

    @Override
    public void removeCollectionChangedListener(CollectionChangeListener listener) {

        collectionChangeListeners.remove(listener);
    }
}
