package iiiedu.sirius.proj01;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.wefika.horizontalpicker.HorizontalPicker;
import java.util.ArrayList;
import java.util.List;
import iiiedu.sirius.proj01.model.DetailMealVO;
import iiiedu.sirius.proj01.model.MealMenuVO;

public class MyMealExpandableAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private List<MealMenuVO> childtems = new ArrayList<MealMenuVO>();
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;


    public MyMealExpandableAdapter(List<MealMenuVO> childtems) {
        this.childtems = childtems;
        for(MealMenuVO childtem:childtems){                 //初始化
            childtem.setMealitemtotal(0);
            for(DetailMealVO child:childtem.getDetailmeals()){
                child.getItemVO().setMealcount(0);
            }
        }
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }


    @Override
    public View getChildView(int groupPosition,int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final int mGroupPosition = groupPosition;
        final DetailMealVO child = childtems.get(mGroupPosition).getDetailmeals().get(childPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mealitem, null);

            childViewHolder = new ChildViewHolder();
            childViewHolder.mealitemname = (TextView) convertView.findViewById(R.id.mealitemname);
            childViewHolder.picker = (HorizontalPicker) convertView.findViewById(R.id.picker);
            childViewHolder.picker.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected(){
                @Override
                public void onItemSelected(int index) {
                    Log.i("picker","" + index);
                    child.getItemVO().setMealcount(index);
                    int total = 0;
                    for(DetailMealVO child : childtems.get(mGroupPosition).getDetailmeals()){
                        total += child.getItemVO().getMealcount();
                    }
                    childtems.get(mGroupPosition).setMealitemtotal(total);
                    notifyDataSetChanged();
                }
            });

            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.mealitemname.setText(child.getItemVO().getItem_name());

        int oldmealcount = child.getItemVO().getMealcount();    //將之前輸入的值放入

        if(oldmealcount != 0 ){
            childViewHolder.picker.setTag(child);
            childViewHolder.picker.setSelectedItem(oldmealcount);
        }
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MealMenuVO childtem = childtems.get(groupPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mealmenu, null);

            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mealmenuname = (TextView) convertView.findViewById(R.id.mealmenuname);
            groupViewHolder.mealmenuquantity = (TextView) convertView.findViewById(R.id.mealmenuquantity);

            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.mealmenuname.setText(childtem.getMenu_name());
        int mealmenucount = childtem.getQuantity()-childtem.getMealitemtotal();
        groupViewHolder.mealmenuquantity.setText(""+mealmenucount);
        groupViewHolder.mealmenuquantity.setSelectAllOnFocus(true);
        return convertView;
    }

    @Override
    public DetailMealVO getChild(int groupPosition, int childPosition) {
        return childtems.get(groupPosition).getDetailmeals().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childtems.get(groupPosition).getDetailmeals().size();
    }

    @Override
    public MealMenuVO getGroup(int groupPosition) {
        return childtems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return childtems.size();
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
        TextView mealmenuname;
        TextView mealmenuquantity;
    }

    public final class ChildViewHolder {
        TextView mealitemname;
        HorizontalPicker picker;
    }
}
