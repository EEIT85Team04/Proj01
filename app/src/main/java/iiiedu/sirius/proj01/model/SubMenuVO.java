package iiiedu.sirius.proj01.model;

import java.util.*;

public class SubMenuVO implements java.io.Serializable{
	private Integer submenu_id;
	private String submenu_name;
	private Integer menu_id;
	private String submenu_img;
	private List<ItemVO> items = new ArrayList<ItemVO>();
	
	public Integer getSubmenu_id() {
		return submenu_id;
	}
	public void setSubmenu_id(Integer submenu_id) {
		this.submenu_id = submenu_id;
	}	
	public String getSubmenu_name() {
		return submenu_name;
	}
	public void setSubmenu_name(String submenu_name) {
		this.submenu_name = submenu_name;
	}
	public Integer getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}
	public String getSubmenu_img() {
		return submenu_img;
	}
	public void setSubmenu_img(String submenu_img) {
		this.submenu_img = submenu_img;
	}
	public List<ItemVO> getItems() {
		return items;
	}
	public void setItems(List<ItemVO> items) {
		this.items = items;
	}

}
