package iiiedu.sirius.proj01.model;

public class LoginResultVO implements java.io.Serializable{
    private String message;
    private String emp_name;
    private Integer bill_id;
    private String tableno;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getEmp_name() {
        return emp_name;
    }
    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }
    public Integer getBill_id() {
        return bill_id;
    }
    public void setBill_id(Integer bill_id) {
        this.bill_id = bill_id;
    }
    public String getTableno() {
        return tableno;
    }
    public void setTableno(String tableno) {
        this.tableno = tableno;
    }

}
