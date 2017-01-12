package tigerstyle.social.com.banggiaxe.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tigerstyle.social.com.banggiaxe.R;

/**
 * Created by billymobile on 1/9/17.
 */

public class AreaInforAdapter extends RecyclerView.Adapter{
    private List<String> mTitle = new ArrayList<>();
    private List<String> mContent = new ArrayList<>();
    private Context mContext;

    public AreaInforAdapter(Context context, List<String> title, List<String> content){
        this.mContext = context;
        this.mTitle = title;
        this.mContent = content;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_area_infor, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AreaInforAdapter.ViewHolder)holder).lblTitle.setText(mTitle.get(position));
        ((AreaInforAdapter.ViewHolder)holder).lblDescription.setText(mContent.get(position));
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final public TextView lblTitle;
        final public TextView lblDescription;
        final public View viewDivider;

        public ViewHolder(View itemView) {
            super(itemView);
            lblTitle = (TextView) itemView.findViewById(R.id.lblTitle);
            lblDescription = (TextView) itemView.findViewById(R.id.lblDescription);
            viewDivider = itemView.findViewById(R.id.viewDivider);
        }
    }
}
