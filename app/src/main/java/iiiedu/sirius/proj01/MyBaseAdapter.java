package iiiedu.sirius.proj01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;
import iiiedu.sirius.proj01.model.ItemVO;

public class MyBaseAdapter extends BaseAdapter {
        private List<ItemVO> items;
        private LayoutInflater inflater;
        private TextView item_name = null;
        private TextView item_price = null;
        private TextView item_content = null;

        private String url = AppController.SERVERIP + "/LuLu_Proj02/images/set2.png";

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        public MyBaseAdapter(List<ItemVO> items){
            this.items = items;
        }

        public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int groupPosition, View view, ViewGroup viewGroup) {

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();

            if (view == null) {
                view = inflater.inflate(R.layout.item,viewGroup,false);
            }

            NetworkImageView thumbNail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            thumbNail.setImageUrl(url, imageLoader);

            item_name = (TextView) view.findViewById(R.id.item);
            item_name.setText(items.get(groupPosition).getItem_name());
            item_price = (TextView) view.findViewById(R.id.item_price);
            item_price.setText(items.get(groupPosition).getPrice().toString());
            item_content = (TextView) view.findViewById(R.id.itemcontent);
            item_content.setText(items.get(groupPosition).getItem_content());
            return view;
        }
    }

