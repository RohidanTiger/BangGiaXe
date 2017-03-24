package tigerstyle.social.com.banggiaxe.view.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;

/**
 * Created by billymobile on 2/2/17.
 */

public class ResultDetailAdapter extends RecyclerView.Adapter{
    private int[] mValue;
    private MainActivity mContext;
    private OnItemClickListener mListener;

    public ResultDetailAdapter(MainActivity mContext,int[] mValue){
        this.mContext = mContext;
        this.mValue = mValue;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_result_detail, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ResultDetailAdapter.ViewHolder) holder).textViewName.setText(String.valueOf(position+1));
        if(mValue[position] == 2){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.cmn_orange));
        }else if(mValue[position] == 1){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.cmn_price2));
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.cardview_shadow_start_color));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValue.length;
    }

    public void setBackgroundColor(int position, int value){
        mValue[position] = value;
        notifyItemChanged(position);
    }

    /* Setter for listener. */
    public void setOnItemClickListener(final OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;

        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.txt_exam_number);
        }
    }
}
