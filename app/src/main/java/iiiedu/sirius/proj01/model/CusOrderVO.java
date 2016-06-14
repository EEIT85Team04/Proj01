package iiiedu.sirius.proj01.model;

public class CusOrderVO implements java.io.Serializable{
    private Integer ord_id;
    private String cus_meal_name;
    private String cus_item_name;
    private Integer quantity;
    private Integer menu_id;

    public Integer getMenu_id() {
        return menu_id;
    }
    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }
    public Integer getOrd_id() {
        return ord_id;
    }
    public void setOrd_id(Integer ord_id) {
        this.ord_id = ord_id;
    }
    public String getCus_meal_name() {
        return cus_meal_name;
    }
    public void setCus_meal_name(String cus_meal_name) {
        this.cus_meal_name = cus_meal_name;
    }
    public String getCus_item_name() {
        return cus_item_name;
    }
    public void setCus_item_name(String cus_item_name) {
        this.cus_item_name = cus_item_name;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
