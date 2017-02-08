package tigerstyle.social.com.banggiaxe.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.MotobikeBrand;

/**
 * Created by billymobile on 12/27/16.
 */

public class DrawerMenuAdapter extends RecyclerView.Adapter{
    private MainActivity mContext;
    private int[] mTitle;
    private int[] mImage;
    private OnItemClickListener listener;

    public DrawerMenuAdapter(MainActivity context){
        this.mContext = context;
        mTitle = new int[]{R.string.cmn_moto_title,R.string.cmn_oto_title, R.string.cmn_comparision,R.string.cmn_a1_certificate,R.string.cmn_b2_certificate};
        mImage = new int[]{R.drawable.ic_moto, R.drawable.ic_sportcar, R.drawable.ic_comparison,0,0};
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.drawer_menu_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(mImage[position] != 0){
            ((DrawerMenuAdapter.ViewHolder) holder).imgVehical.setImageResource(mImage[position]);
        }else{
            ((DrawerMenuAdapter.ViewHolder) holder).imgVehical.setVisibility(View.INVISIBLE);
        }
        ((DrawerMenuAdapter.ViewHolder) holder).textViewName.setText(mContext.getResources().getString(mTitle[position]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    public interface OnItemClickListener {
        void onClick(int index);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgVehical;
        public TextView textViewName;

        public ViewHolder(View v) {
            super(v);
            imgVehical = (ImageView) v.findViewById(R.id.imgVehical);
            textViewName = (TextView) v.findViewById(R.id.txtNameMenu);
        }
    }
}
