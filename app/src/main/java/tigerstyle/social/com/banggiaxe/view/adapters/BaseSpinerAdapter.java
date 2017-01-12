package tigerstyle.social.com.banggiaxe.view.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import tigerstyle.social.com.banggiaxe.R;

/**
 * Created by billymobile on 12/20/16.
 */

public class BaseSpinerAdapter extends ArrayAdapter<String> {
    private int mSelectedIndex = -1;

    public void setSelection(int position) {
        mSelectedIndex =  position;
        notifyDataSetChanged();
    }

    public BaseSpinerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View itemView =  super.getDropDownView(position, convertView, parent);
        if (position == mSelectedIndex) {
            itemView.setBackgroundResource(R.color.cmn_gray_light);
        } else {
            itemView.setBackgroundResource(R.color.cmn_white);
        }
        return itemView;
    }
}