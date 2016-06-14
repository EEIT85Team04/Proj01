package iiiedu.sirius.proj01.model;

public class ItemVO implements java.io.Serializable{
	private Integer item_id	;
	private String item_name;
	private Integer price;
	private Integer submenu_id;
	private String item_content;
	private String item_img;
	private String ismeal_id;
	private Integer mealcount = 0;	//紀錄目前選擇數量(套餐用)
	private IsMealVO isMealVO;

	public IsMealVO getIsMealVO() {
		return isMealVO;
	}
	public void setIsMealVO(IsMealVO isMealVO) {
		this.isMealVO = isMealVO;
	}
	public Integer getItem_id() {
		return item_id;
	}
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getSubmenu_id() {
		return submenu_id;
	}
	public void setSubmenu_id(Integer submenu_id) {
		this.submenu_id = submenu_id;
	}
	public String getItem_content() {
		return item_content;
	}
	public void setItem_content(String item_content) {
		this.item_content = item_content;
	}
	public String getItem_img() {
		return item_img;
	}
	public void setItem_img(String item_img) {
		this.item_img = item_img;
	}
	public String getIsmeal_id() {
		return ismeal_id;
	}
	public void setIsmeal_id(String ismeal_id) {
		this.ismeal_id = ismeal_id;
	}
	public Integer getMealcount() {
		return mealcount;
	}
	public void setMealcount(Integer mealcount) {
		this.mealcount = mealcount;
	}
	
}

