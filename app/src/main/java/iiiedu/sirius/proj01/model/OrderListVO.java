package iiiedu.sirius.proj01.model;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderListVO implements java.io.Serializable{
    private Integer ord_id;
    private String tableno;
    private Integer bill_id;
    private Timestamp ord_time;
    private Integer ord_money;
    private Integer ord_status;
    private List<OrderdetailVO> orderdetails = new ArrayList<OrderdetailVO>();

    public Integer getOrd_status() {
        return ord_status;
    }
    public void setOrd_status(Integer ord_status) {
        this.ord_status = ord_status;
    }
    public List<OrderdetailVO> getOrders() {
        return orderdetails;
    }
    public void setOrders(List<OrderdetailVO> orderdetails) {
        this.orderdetails = orderdetails;
    }
    public Integer getOrd_id() {
        return ord_id;
    }
    public void setOrd_id(Integer ord_id) {
        this.ord_id = ord_id;
    }
    public String getTableno() {
        return tableno;
    }
    public void setTableno(String tableno) {
        this.tableno = tableno;
    }
    public Integer getBill_id() {
        return bill_id;
    }
    public void setBill_id(Integer bill_id) {
        this.bill_id = bill_id;
    }
    public Timestamp getOrd_time() {
        return ord_time;
    }
    public void setOrd_time(Timestamp ord_time) {
        this.ord_time = ord_time;
    }
    public Integer getOrd_money() {
        return ord_money;
    }
    public void setOrd_money(Integer ord_money) {
        this.ord_money = ord_money;
    }

}
