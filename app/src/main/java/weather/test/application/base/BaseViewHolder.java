package weather.test.application.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public abstract class BaseViewHolder<T extends ViewDataBinding, P> extends RecyclerView.ViewHolder{

    private T binding;

    /**
     * Sets view to ViewHolder and binding object to local variable.
     *
     * @param binding - exemplar of Data Binding class.
     */
    public BaseViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Method for binding data to the view.
     *
     * @param data - exemplar of data class.
     */
    public abstract void bind(P data);

    /**
     * Provide access to binding class for child classes.
     *
     * @return - exemplar of Data Binding.
     */
    protected T getBinding() {
        return binding;
    }
}