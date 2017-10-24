package com.pgssoft.forgetadapters.common;

import android.databinding.CallbackRegistry;
import android.support.v4.util.Pools;

/**
 * Created by wsura on 24.10.2017.
 */

class ListChangeRegistry extends CallbackRegistry<IObservableCollection.CollectionChangeListener, ObservableCollection, ListChangeRegistry.ListChanges> {
    private static final Pools.SynchronizedPool<ListChangeRegistry.ListChanges> sListChanges =
            new Pools.SynchronizedPool<ListChangeRegistry.ListChanges>(10);

    private static final int ALL = 0;
    private static final int CHANGED = 1;
    private static final int INSERTED = 2;
    private static final int REMOVED = 3;

    private static final CallbackRegistry.NotifierCallback<IObservableCollection.CollectionChangeListener,
            ObservableCollection, ListChangeRegistry.ListChanges> NOTIFIER_CALLBACK = new CallbackRegistry.NotifierCallback<IObservableCollection.CollectionChangeListener, ObservableCollection, ListChangeRegistry.ListChanges>() {
        @Override
        public void onNotifyCallback(IObservableCollection.CollectionChangeListener callback,
                                     ObservableCollection sender, int notificationType, ListChangeRegistry.ListChanges listChanges) {
            switch (notificationType) {
                case CHANGED:
                    callback.onItemRangeChanged(sender, listChanges.start, listChanges.count);
                    break;
                case INSERTED:
                    callback.onItemRangeInserted(sender, listChanges.start, listChanges.count);
                    break;
                case REMOVED:
                    callback.onItemRangeRemoved(sender, listChanges.start, listChanges.count);
                    break;
                default:
                    callback.onChanged(sender);
                    break;
            }
        }
    };

    public void notifyChanged(ObservableCollection list) {
        notifyCallbacks(list, ALL, null);
    }

    public void notifyChanged(ObservableCollection list, int start, int count) {
        ListChangeRegistry.ListChanges listChanges = acquire(start, 0, count);
        notifyCallbacks(list, CHANGED, listChanges);
    }

    public void notifyInserted(ObservableCollection list, int start, int count) {
        ListChangeRegistry.ListChanges listChanges = acquire(start, 0, count);
        notifyCallbacks(list, INSERTED, listChanges);
    }

    public void notifyRemoved(ObservableCollection list, int start, int count) {
        ListChangeRegistry.ListChanges listChanges = acquire(start, 0, count);
        notifyCallbacks(list, REMOVED, listChanges);
    }

    private static ListChangeRegistry.ListChanges acquire(int start, int to, int count) {
        ListChangeRegistry.ListChanges listChanges = sListChanges.acquire();
        if (listChanges == null) {
            listChanges = new ListChangeRegistry.ListChanges();
        }
        listChanges.start = start;
        listChanges.to = to;
        listChanges.count = count;
        return listChanges;
    }

    @Override
    public synchronized void notifyCallbacks(ObservableCollection sender, int notificationType,
                                             ListChangeRegistry.ListChanges listChanges) {
        super.notifyCallbacks(sender, notificationType, listChanges);
        if (listChanges != null) {
            sListChanges.release(listChanges);
        }
    }

    public ListChangeRegistry() {
        super(NOTIFIER_CALLBACK);
    }

    static class ListChanges {
        public int start;
        public int count;
        public int to;
    }
}

