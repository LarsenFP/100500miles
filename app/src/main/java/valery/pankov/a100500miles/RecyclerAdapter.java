package valery.pankov.a100500miles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Valery on 16.03.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<LatestNews> itemList;
    private Context context;
    private static OnItemClickListener mItemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView;
        public TextView mTextViewDate;
        public ImageView mImageView;


        public ViewHolder(View v) {
            super(v);
            mTextViewDate = (TextView) v.findViewById(R.id.item_explain_textView);
            mTextView = (TextView) v.findViewById(R.id.item_textView);
            mImageView = (ImageView) v.findViewById(R.id.item_imageView);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
            //System.out.println("onClick " + getAdapterPosition() + " ");
        }
    }

    public RecyclerAdapter(ArrayList<LatestNews> itemList) {
        this.itemList = itemList;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(itemList.get(position).getTitle());
        holder.mTextViewDate.setText(itemList.get(position).getDate());
        Glide.with(context)
                .load(itemList.get(position).getUrlImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.mImageView);

        if(position==(getItemCount()-1)){
            System.out.println("---------");
            System.out.println("Last Item");
            System.out.println("---------");
            // here goes some code
            //  callback.sendMessage(Message);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
