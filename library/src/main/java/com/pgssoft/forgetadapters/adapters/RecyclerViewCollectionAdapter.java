package com.pgssoft.forgetadapters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.pgssoft.forgetadapters.common.IIdProvider;
import com.pgssoft.forgetadapters.common.IObservableCollection;
import com.pgssoft.forgetadapters.dataBinding.RecyclerViewProvider;
import com.pgssoft.forgetadapters.viewmodels.base.IDataViewModel;
import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

/**
 * Created by wsura on 13.10.2017.
 */

public class RecyclerViewCollectionAdapter<TModel, TView extends View & IDataViewModelProvider<TModel>> extends RecyclerView.Adapter<RecyclerViewCollectionAdapter<TModel, TView>.ViewHolder<TView>> {

    // Private classes --------------------------------------------------------

    class ViewHolder<TView extends View & IDataViewModelProvider<TModel>> extends RecyclerView.ViewHolder {

        private TView view;

        public ViewHolder(TView view) {
            super(view);
            this.view = view;
        }

        public IDataViewModel<TModel> getViewModel() {

            return view.getViewModel();
        }
    }

    // Private event handlers -------------------------------------------------

    private final IObservableCollection.CollectionChangeListener collectionChangeListener = new IObservableCollection.CollectionChangeListener() {
        @Override
        public void onItemRangeChanged(IObservableCollection sender, int start, int count) {

            RecyclerViewCollectionAdapter.this.notifyItemRangeChanged(start, count);
        }

        @Override
        public void onItemRangeInserted(IObservableCollection sender, int start, int count) {

            RecyclerViewCollectionAdapter.this.notifyItemRangeInserted(start, count);
        }

        @Override
        public void onItemRangeRemoved(IObservableCollection sender, int start, int count) {

            RecyclerViewCollectionAdapter.this.notifyItemRangeRemoved(start, count);
        }

        @Override
        public void onChanged(IObservableCollection sender) {

            RecyclerViewCollectionAdapter.this.notifyDataSetChanged();
        }
    };

    // Private fields ---------------------------------------------------------

    private final IObservableCollection<TModel> observableCollection;
    private RecyclerViewProvider<TModel, TView> viewProvider;

    // Public methods ---------------------------------------------------------

    public RecyclerViewCollectionAdapter(IObservableCollection<TModel> observableCollection, RecyclerViewProvider<TModel, TView> viewProvider) {

        this.observableCollection = observableCollection;
        this.viewProvider = viewProvider;

        observableCollection.addCollectionChangedListener(collectionChangeListener);
        setHasStableIds(viewProvider.hasStableIds());
    }

    @Override
    public ViewHolder<TView> onCreateViewHolder(ViewGroup parent, int viewType) {

        TView view = viewProvider.createView(viewType);
        return new ViewHolder<>(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder<TView> holder, int position) {

        TModel data = observableCollection.get(position);
        holder.getViewModel().setData(data);
    }

    @Override
    public int getItemCount() {

        return observableCollection.size();
    }

    @Override
    public long getItemId(int position) {

        if (observableCollection.get(position) instanceof IIdProvider)
            return ((IIdProvider)observableCollection.get(position)).getId();
        else {
            if (viewProvider.hasStableIds())
                throw new RuntimeException("If supplied ViewProvider claims, that data has stable IDs, model class must implement IIdProvider interface.");
        }

        return 0;
    }

    public IObservableCollection getItems() {
        return observableCollection;
    }
}