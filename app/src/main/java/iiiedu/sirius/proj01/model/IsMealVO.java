package iiiedu.sirius.proj01.model;

import java.util.ArrayList;
import java.util.List;

public class IsMealVO implements java.io.Serializable{
    private String ismeal_id;
    private String meal_status;

    public String getIsmeal_id() {
        return ismeal_id;
    }
    public void setIsmeal_id(String ismeal_id) {
        this.ismeal_id = ismeal_id;
    }
    public String getMeal_status() {
        return meal_status;
    }
    public void setMeal_status(String meal_status) {
        this.meal_status = meal_status;
    }

}
