package com.pgssoft.forgetadapters.adapters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.pgssoft.forgetadapters.common.IIdProvider;
import com.pgssoft.forgetadapters.dataBinding.RecyclerViewProvider;
import com.pgssoft.forgetadapters.viewmodels.base.IDataViewModel;
import com.pgssoft.forgetadapters.views.interfaces.IDataViewModelProvider;

/**
 * Created by wsura on 25.10.2017.
 */

public class RecyclerViewListAdapter<TModel, TView extends View & IDataViewModelProvider<TModel>> extends RecyclerView.Adapter<RecyclerViewListAdapter<TModel, TView>.ViewHolder<TView>> {

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

    private final ObservableList.OnListChangedCallback listChangeListener = new ObservableList.OnListChangedCallback() {
        @Override
        public void onChanged(ObservableList observableList) {

            RecyclerViewListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList observableList, int positionStart, int itemCount) {

            RecyclerViewListAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList observableList, int positionStart, int itemCount) {

            RecyclerViewListAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList observableList, int fromPosition, int toPosition, int itemCount) {

            RecyclerViewListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(ObservableList observableList, int positionStart, int itemCount) {

            RecyclerViewListAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
        }
    };

    // Private fields ---------------------------------------------------------

    private final ObservableArrayList<TModel> observableList;
    private RecyclerViewProvider<TModel, TView> viewProvider;

    // Public methods ---------------------------------------------------------

    public RecyclerViewListAdapter(ObservableArrayList<TModel> observableList, RecyclerViewProvider<TModel, TView> viewProvider) {

        this.observableList = observableList;
        this.viewProvider = viewProvider;

        observableList.addOnListChangedCallback(listChangeListener);
        setHasStableIds(viewProvider.hasStableIds());
    }

    @Override
    public ViewHolder<TView> onCreateViewHolder(ViewGroup parent, int viewType) {

        TView view = viewProvider.createView(viewType);
        return new ViewHolder<>(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder<TView> holder, int position) {

        TModel data = observableList.get(position);
        holder.getViewModel().setData(data);
    }

    @Override
    public int getItemCount() {

        return observableList.size();
    }

    @Override
    public long getItemId(int position) {

        if (observableList.get(position) instanceof IIdProvider)
            return ((IIdProvider) observableList.get(position)).getId();
        else {
            if (viewProvider.hasStableIds())
                throw new RuntimeException("If supplied ViewProvider claims, that data has stable IDs, model class must implement IIdProvider interface.");
        }

        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return viewProvider.getTypeFor(observableList.get(position));
    }

    public ObservableArrayList<TModel> getList() {
        return observableList;
    }
}
