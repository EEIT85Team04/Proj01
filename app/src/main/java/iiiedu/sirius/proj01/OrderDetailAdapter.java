package iiiedu.sirius.proj01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import iiiedu.sirius.proj01.model.OrderdetailVO;


public class OrderDetailAdapter extends BaseAdapter {
    private List<OrderdetailVO> orders;
    private LayoutInflater inflater;
    private TextView orderdetail_name = null;
    private TextView orderdetail_quantity = null;
    private TextView orderdetail_price = null;
    private TextView orderdetail_status = null;

    public OrderDetailAdapter (LayoutInflater inflater) {
        this.inflater = inflater;
    }
    public void setOrders (List<OrderdetailVO> order)  {
        this.orders = order;
    }

    @Override
    public int getCount() {
        return orders.size();
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
        if (view == null) {
            view = inflater.inflate(R.layout.orderdetail,viewGroup,false);
        }
        orderdetail_name = (TextView) view.findViewById(R.id.orderdetail_name);
        orderdetail_name.setText(orders.get(groupPosition).getItem_name());
        orderdetail_quantity = (TextView) view.findViewById(R.id.orderdetail_quantity);
        orderdetail_quantity.setText(orders.get(groupPosition).getQuantity().toString());
        orderdetail_price = (TextView) view.findViewById(R.id.orderdetail_price);
        orderdetail_price.setText(orders.get(groupPosition).getOrd_det_price().toString());
        orderdetail_status = (TextView) view.findViewById(R.id.orderdetail_status);
        if(orders.get(groupPosition).getMeal_status() == 1){
            orderdetail_status.setText(R.string.simple);
        }
        return view;
    }
}
