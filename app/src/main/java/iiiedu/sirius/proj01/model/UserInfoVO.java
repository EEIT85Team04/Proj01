package iiiedu.sirius.proj01.model;

public class UserInfoVO implements java.io.Serializable{
    private String account;
    private String password;
    private Integer peoplenumber;
    private String seat_name;
    private String site_name;

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getPeoplenumber() {
        return peoplenumber;
    }
    public void setPeoplenumber(Integer peoplenumber) {
        this.peoplenumber = peoplenumber;
    }
    public String getSeat_name() {
        return seat_name;
    }
    public void setSeat_name(String seat_name) {
        this.seat_name = seat_name;
    }
    public String getSite_name() {
        return site_name;
    }
    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }
}
