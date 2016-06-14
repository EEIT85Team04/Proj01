package iiiedu.sirius.proj01.model;

import java.util.*;

public class MenuVO implements java.io.Serializable{
	private Integer menu_id;
	private String menu_name;
	private String menu_img;
	private List<SubMenuVO> submenus = new ArrayList<SubMenuVO>();
	
	public Integer getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_img() {
		return menu_img;
	}
	public void setMenu_img(String menu_img) {
		this.menu_img = menu_img;
	}
	public List<SubMenuVO> getSubmenus() {
		return submenus;
	}
	public void setSubmenus(List<SubMenuVO> submenus) {
		this.submenus = submenus;
	}

}
