package tigerstyle.social.com.banggiaxe.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.SectionRecycleView.SectionedRecyclerViewAdapter;
import tigerstyle.social.com.banggiaxe.customize.SectionRecycleView.SectionedViewHolder;
import tigerstyle.social.com.banggiaxe.model.ListOldCar;
import tigerstyle.social.com.banggiaxe.model.OldCarObject;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;

/**
 * Created by quydv on 2/4/18.
 */

public class OldCarAdapter extends SectionedRecyclerViewAdapter<SectionedViewHolder> {

    private MainActivity mContext;
    private List<ListOldCar> listOldCars;
    private OnItemClickListener listener;

    public OldCarAdapter(MainActivity mContext, List<ListOldCar> listOldCars) {
        this.mContext = mContext;
        this.listOldCars = listOldCars;
    }

    public void setListOldCars(List<ListOldCar> listOldCars) {
        this.listOldCars = listOldCars;
    }

    @Override
    public int getSectionCount() {
        return listOldCars.size();
    }

    @Override
    public int getItemCount(int section) {
        return listOldCars.get(section).getListVersions().size();
    }

    @Override
    public void onBindHeaderViewHolder(SectionedViewHolder holder, int section, boolean expanded) {
        ListOldCar key = listOldCars.get(section);
        ((MainVH) holder).txtNameValue.setText(key.getCarName());
        ((MainVH) holder).txtBrandValue.setText(key.getCarBrand());
        ((MainVH) holder).txtTypeValue.setText(key.getCarType());
    }

    @Override
    public void onBindViewHolder(SectionedViewHolder holder, int section, int relativePosition, int absolutePosition) {
        ListOldCar key = listOldCars.get(section);
        OldCarObject object = key.getListVersions().get(relativePosition);
        ((ViewHolder) holder).txtVersion.setText(object.getVersion());
        ((ViewHolder) holder).txtName.setText(key.getCarName());
        ((ViewHolder) holder).txtPrice.setText(object.getCurent_price());
        PicassoLoader.getInstance(mContext).load(object.getImage()).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into(((ViewHolder) holder).imgVehical);

    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        return super.getItemViewType(section, relativePosition, absolutePosition);
    }

    @Override
    public SectionedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                layout = R.layout.list_item_header;
                View v1 = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                return new MainVH(v1, this);

            case VIEW_TYPE_ITEM:
                layout = R.layout.old_car_item;
                View v2 = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                return new ViewHolder(v2);

            default:
                layout = R.layout.list_item_header;
                View v3 = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                return new MainVH(v3, this);

        }
    }

    static class MainVH extends SectionedViewHolder implements View.OnClickListener {

        Toast toast;
        final TextView txtNameValue;
        final TextView txtBrandValue;
        final TextView txtTypeValue;
        final OldCarAdapter adapter;

        MainVH(View itemView, OldCarAdapter adapter) {
            super(itemView);
            this.txtNameValue = (TextView) itemView.findViewById(R.id.txt_name_value);
            this.txtBrandValue = (TextView) itemView.findViewById(R.id.txt_brand_value);
            txtTypeValue = (TextView) itemView.findViewById(R.id.txt_type_value);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (isHeader()) {
                adapter.toggleSectionExpanded(getRelativePosition().section());
            } else {
                if (toast != null) {
                    toast.cancel();
                }
                toast =
                        Toast.makeText(view.getContext(), getRelativePosition().toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public static class ViewHolder extends SectionedViewHolder {
        public RelativeLayout layoutContent;
        public ImageView imgVehical;
        public TextView txtName;
        public TextView txtVersion;
        public TextView txtPrice;

        public ViewHolder(View v) {
            super(v);
            layoutContent = (RelativeLayout) v.findViewById(R.id.layout_content);
            imgVehical = (ImageView) v.findViewById(R.id.imgVehical);
            txtName = (TextView) v.findViewById(R.id.txtName);
            txtVersion = (TextView) v.findViewById(R.id.txt_version);
            txtPrice = (TextView) v.findViewById(R.id.txtPrice);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
