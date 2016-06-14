package iiiedu.sirius.proj01.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LuLuDBHelper {

        public List<MenuVO> getMenusFromJSON(JSONArray jsonContent) {
            if (jsonContent == null)
                return null;
            else {
                List<MenuVO> menus = new ArrayList<MenuVO>();
                try {
                    MenuVO menu=null;
                    for (int i = 0 , max1 = jsonContent.length() ; i < max1 ; i++) {
                        JSONObject menuobj = jsonContent.getJSONObject(i);
                        menu=new MenuVO();
                        menu.setMenu_id(menuobj.getInt("menu_id"));
                        menu.setMenu_name(menuobj.getString("menu_name"));
                        menu.setMenu_img(menuobj.getString("menu_img"));
                        List<SubMenuVO> submenus = new ArrayList<SubMenuVO>();
                        JSONArray submenuArray =menuobj.getJSONArray("submenus");
                        SubMenuVO submenu=null;
                        for(int y = 0 , max2 = submenuArray.length() ; y < max2 ; y++){
                            JSONObject submenuobj = submenuArray.getJSONObject(y);
                            submenu = new SubMenuVO();
                            submenu.setSubmenu_id(submenuobj.getInt("submenu_id"));
                            submenu.setSubmenu_name(submenuobj.getString("submenu_name"));
                            submenu.setMenu_id(submenuobj.getInt("menu_id"));
                            submenu.setSubmenu_img(submenuobj.getString("submenu_img"));
                            List<ItemVO> items = new ArrayList<ItemVO>();
                            JSONArray itemArray = submenuobj.getJSONArray("items");
                            ItemVO item = null;
                            for(int z = 0 , max3 = itemArray.length() ; z < max3 ; z++){
                                JSONObject itemobj = itemArray.getJSONObject(z);
                                item = new ItemVO();
                                item.setItem_id(itemobj.getInt("item_id"));
                                item.setItem_name(itemobj.getString("item_name"));
                                item.setPrice(itemobj.getInt("price"));
                                item.setSubmenu_id(itemobj.getInt("submenu_id"));
                                item.setItem_content(itemobj.getString("item_content"));
                                item.setItem_img(itemobj.getString("item_img"));
                                item.setIsmeal_id(itemobj.getString("ismeal_id"));
                                items.add(item);
                            }
                            submenu.setItems(items);
                            submenus.add(submenu);

                        }
                        menu.setSubmenus(submenus);
                        menus.add(menu);
                    }
                    return menus;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }


} // end of class LuLuHelper