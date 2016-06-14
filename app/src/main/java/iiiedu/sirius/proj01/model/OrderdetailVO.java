package iiiedu.sirius.proj01.model;

import java.util.ArrayList;
import java.util.List;

public class OrderdetailVO implements java.io.Serializable{
    private Integer ord_id;
    private String item_name;
    private Integer quantity;
    private Integer ord_det_price;
    private Integer meal_status;
    private List<CusOrderVO> cusorders = new ArrayList<CusOrderVO>();

    public List<CusOrderVO> getCusorders() {
        return cusorders;
    }
    public void setCusorders(List<CusOrderVO> cusorders) {
        this.cusorders = cusorders;
    }
    public Integer getOrd_id() {
        return ord_id;
    }
    public void setOrd_id(Integer ord_id) {
        this.ord_id = ord_id;
    }
    public String getItem_name() {
        return item_name;
    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getOrd_det_price() {
        return ord_det_price;
    }
    public void setOrd_det_price(Integer ord_det_price) {
        this.ord_det_price = ord_det_price;
    }
    public Integer getMeal_status() {
        return meal_status;
    }
    public void setMeal_status(Integer meal_status) {
        this.meal_status = meal_status;
    }

}
