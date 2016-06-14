package iiiedu.sirius.proj01.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MealMenuVO implements java.io.Serializable{
    private Integer menu_id;
    private Integer quantity;
    private String menu_name;
    private Integer mealitemtotal = 0;      //紀錄child數量
    public String getMenu_name() {
        return menu_name;
    }
    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    private List<DetailMealVO> detailmeals = new ArrayList<DetailMealVO>();
    public Integer getMenu_id() {
        return menu_id;
    }
    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }
    public List<DetailMealVO> getDetailmeals() {
        return detailmeals;
    }
    public void setDetailmeals(List<DetailMealVO> detailmeals) {
        this.detailmeals = detailmeals;
    }
    public Integer getMealitemtotal() {
        return mealitemtotal;
    }
    public void setMealitemtotal(Integer mealitemtotal) {
        this.mealitemtotal = mealitemtotal;
    }
    public boolean checkquantity() {
        if(mealitemtotal == quantity)
            return true;
        return false;
    }
}
