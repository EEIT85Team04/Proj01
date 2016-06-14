package iiiedu.sirius.proj01.model;


import java.util.ArrayList;
import java.util.List;

public class BillVO implements java.io.Serializable{
    private Integer bill_id;
    private Integer peoplenumber;
    private Integer bill_money;
    private List<OrderdetailVO> orderdetails = new ArrayList<>();
    public Integer getBill_id() {
        return bill_id;
    }
    public void setBill_id(Integer bill_id) {
        this.bill_id = bill_id;
    }
    public Integer getPeoplenumber() {
        return peoplenumber;
    }
    public void setPeoplenumber(Integer peoplenumber) {
        this.peoplenumber = peoplenumber;
    }
    public Integer getBill_money() {
        return bill_money;
    }
    public void setBill_money(Integer bill_money) {
        this.bill_money = bill_money;
    }
    public List<OrderdetailVO> getOrderdetails() {
        return orderdetails;
    }
    public void setOrderdetails(List<OrderdetailVO> orderdetails) {
        this.orderdetails = orderdetails;
    }
}
