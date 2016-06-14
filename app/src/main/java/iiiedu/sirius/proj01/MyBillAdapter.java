package iiiedu.sirius.proj01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import iiiedu.sirius.proj01.model.BillVO;
import iiiedu.sirius.proj01.model.CusOrderVO;
import iiiedu.sirius.proj01.model.OrderdetailVO;


public class MyBillAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    BillVO bill;
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;


    public MyBillAdapter(BillVO bill) {
        this.bill = bill;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        CusOrderVO cusorder = bill.getOrderdetails().get(groupPosition).getCusorders().get(childPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cusorder,null);

            childViewHolder = new ChildViewHolder();
            childViewHolder.cusorder_name = (TextView) convertView.findViewById(R.id.cusorder_name);
            childViewHolder.cusorder_quantity = (TextView) convertView.findViewById(R.id.cusorder_quantity);

            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.cusorder_name.setText(cusorder.getCus_item_name());
        childViewHolder.cusorder_quantity.setText(cusorder.getQuantity().toString());

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        OrderdetailVO orderdetail = bill.getOrderdetails().get(groupPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.orderdetail,null);

            groupViewHolder = new GroupViewHolder();
            groupViewHolder.orderdetail_name = (TextView) convertView.findViewById(R.id.orderdetail_name);
            groupViewHolder.orderdetail_quantity = (TextView) convertView.findViewById(R.id.orderdetail_quantity);
            groupViewHolder.orderdetail_price = (TextView) convertView.findViewById(R.id.orderdetail_price);
            groupViewHolder.orderdetail_status = (TextView) convertView.findViewById(R.id.orderdetail_status);

            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.orderdetail_name.setText(orderdetail.getItem_name());
        groupViewHolder.orderdetail_quantity.setText(orderdetail.getQuantity().toString());
        groupViewHolder.orderdetail_price.setText(orderdetail.getOrd_det_price().toString());
        if(orderdetail.getMeal_status() == 1){
            groupViewHolder.orderdetail_status.setText(R.string.simple);
        }else if(orderdetail.getMeal_status() == 2){
            groupViewHolder.orderdetail_status.setText(R.string.set);
        }
        return convertView;
    }

    @Override
    public CusOrderVO getChild(int groupPosition, int childPosition) {
        return bill.getOrderdetails().get(groupPosition).getCusorders().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bill.getOrderdetails().get(groupPosition).getCusorders().size();
    }

    @Override
    public OrderdetailVO getGroup(int groupPosition) {
        return bill.getOrderdetails().get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return bill.getOrderdetails().size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public final class GroupViewHolder {
        TextView orderdetail_name;
        TextView orderdetail_quantity;
        TextView orderdetail_price;
        TextView orderdetail_status;
    }

    public final class ChildViewHolder {
        TextView cusorder_name;
        TextView cusorder_quantity;
    }
}
