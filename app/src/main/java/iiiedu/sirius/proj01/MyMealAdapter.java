package iiiedu.sirius.proj01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import iiiedu.sirius.proj01.model.MealVO;

public class MyMealAdapter extends BaseAdapter {
    private MealVO[] meals;
    private LayoutInflater inflater;
    private TextView item_name = null;


    public MyMealAdapter(MealVO[] meals){
        this.meals = meals;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return meals.length;
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
            view = inflater.inflate(R.layout.item,viewGroup,false);
        }
        item_name = (TextView) view.findViewById(R.id.item);
        item_name.setText(meals[groupPosition].getMeal_name());
        return view;
    }
}
