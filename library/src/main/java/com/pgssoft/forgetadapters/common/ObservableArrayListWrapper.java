package com.pgssoft.forgetadapters.common;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;

/**
 * Created by wsura on 13.10.2017.
 */

public class ObservableArrayListWrapper<T> implements IObservableCollection<T> {

    private final ObservableArrayList<T> items;
    private final ArrayList<IObservableCollection.CollectionChangeListener> collectionChangeListeners = new ArrayList<>();

    private final ObservableList.OnListChangedCallback listChangedCallback = new ObservableList.OnListChangedCallback() {

        @Override
        public void onChanged(ObservableList observableList) {

            for (IObservableCollection.CollectionChangeListener listener : collectionChangeListeners) {
                listener.onChanged(ObservableArrayListWrapper.this);
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList observableList, int positionStart, int itemCount) {

            for (CollectionChangeListener listener : collectionChangeListeners) {
                listener.onItemRangeChanged(ObservableArrayListWrapper.this, positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList observableList, int positionStart, int itemCount) {

            for (CollectionChangeListener listener : collectionChangeListeners) {
                listener.onItemRangeInserted(ObservableArrayListWrapper.this, positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList observableList, int fromPosition, int toPosition, int itemCount) {

            for (CollectionChangeListener listener : collectionChangeListeners) {
                listener.onChanged(ObservableArrayListWrapper.this);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList observableList, int positionStart, int itemCount) {

            for (CollectionChangeListener listener : collectionChangeListeners) {
                listener.onItemRangeRemoved(ObservableArrayListWrapper.this, positionStart, itemCount);
            }
        }
    };

    public ObservableArrayListWrapper(ObservableArrayList<T> items) {

        this.items = items;
        this.items.addOnListChangedCallback(listChangedCallback);
    }

    @Override
    public int size() {

        return items.size();
    }

    @Override
    public T get(int index) {

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

    public ObservableArrayList<T> getItems() {
        return items;
    }
}
