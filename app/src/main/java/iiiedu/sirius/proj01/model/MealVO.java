package iiiedu.sirius.proj01.model;

import java.util.ArrayList;
import java.util.List;

public class MealVO implements java.io.Serializable{
    private Integer meal_id	;
    private String 	meal_name;
    private Integer price;
    private String meal_content;
    private String meal_img;
    private List<MealMenuVO> mealmenus = new ArrayList<MealMenuVO>();

    public Integer getMeal_id() {
        return meal_id;
    }
    public void setMeal_id(Integer meal_id) {
        this.meal_id = meal_id;
    }
    public String getMeal_name() {
        return meal_name;
    }
    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getMeal_content() {
        return meal_content;
    }
    public void setMeal_content(String meal_content) {
        this.meal_content = meal_content;
    }
    public String getMeal_img() {
        return meal_img;
    }
    public void setMeal_img(String meal_img) {
        this.meal_img = meal_img;
    }
    public List<MealMenuVO> getMealmenus() {
        return mealmenus;
    }
    public void setMealmenus(List<MealMenuVO> mealmenus) {
        this.mealmenus = mealmenus;
    }

}