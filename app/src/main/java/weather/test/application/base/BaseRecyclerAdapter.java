package weather.test.application.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T extends ViewDataBinding, P, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    private final List<P> items = new ArrayList<>();

    /**
     * Creates exemplar of Data Binding class.
     *
     * @param inflater - LayoutInflater for inflating view to ViewHolder.
     * @param parent   - ViewGroup that will contains RecyclerView items.
     * @return - exemplar of Data Binding.
     */
    protected abstract T getBinding(LayoutInflater inflater, ViewGroup parent);

    /**
     * Creates and inflates ViewHolder for item.
     *
     * @param binding - exemplar of Data Binding for bind view in ViewHolder.
     * @return - exemplar of ViewHolder with view and binding exemplar inside.
     */
    protected abstract V getViewHolder(T binding);

    // Creating ViewHolder.
    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return getViewHolder(getBinding(inflater, parent));
    }

    // Binds DataBinding exemplar to ViewHolder.
    // If you need handle other operations just override this method with call super.
    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Clears current list of items and add new items.
     *
     * @param items - new list of data class objects.
     */
    public void setItems(List<P> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Adds one item to list.
     *
     * @param item - new data object.
     */
    public void addItem(P item) {
        items.add(item);
        notifyDataSetChanged();
    }

    /**
     * Adds list of the items to current list.
     *
     * @param items - list with new data objects.
     */
    public void addItems(List<P> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    protected P getItem(int position) {
        return items.get(position);
    }

}