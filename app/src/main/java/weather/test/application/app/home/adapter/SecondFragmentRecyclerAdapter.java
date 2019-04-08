package weather.test.application.app.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import weather.test.application.R;


public class SecondFragmentRecyclerAdapter extends RecyclerView.Adapter<SecondFragmentRecyclerAdapter.ViewHolder> {

    private List<String> tableCorgis = new ArrayList<>();
    private Context context;
    //FirstFragmentRecyclerAdapter.MyListener listener;

    public SecondFragmentRecyclerAdapter() {
    }

    @Override
    public SecondFragmentRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_recycler_item, parent, false);
        return new SecondFragmentRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SecondFragmentRecyclerAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText("item " + (position + 1));

//        Glide.with(context)
//                .load(tableCorgis.get(position).message)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (tableCorgis == null)
            return 0;
        return tableCorgis.size();
    }

    public void setItems(List<String> items, Context context) {
        this.tableCorgis.clear();
        this.context = context;
        this.tableCorgis.addAll(items);
        notifyDataSetChanged();
    }

//    public interface MyListener {
//        void callback(int pos, String url);
//    }
//
//    public void setMyListener(FirstFragmentRecyclerAdapter.MyListener listener) {
//        this.listener = listener;
//    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            //imageView = itemView.findViewById(R.id.image);
           // tvTitle = itemView.findViewById(R.id.tv_title);

            itemView.setOnLongClickListener(v -> {
              //  listener.callback(tableCorgis.get(getAdapterPosition()).id, tableCorgis.get(getAdapterPosition()).message);
                return false;
            });
        }


    }
}