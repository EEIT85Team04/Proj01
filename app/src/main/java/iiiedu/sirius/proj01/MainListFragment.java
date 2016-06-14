package iiiedu.sirius.proj01;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

import iiiedu.sirius.proj01.model.ItemVO;
import iiiedu.sirius.proj01.model.LuLuDBHelper;
import iiiedu.sirius.proj01.model.MenuVO;
import iiiedu.sirius.proj01.model.SubMenuVO;

public class MainListFragment extends Fragment {
    private List<MenuVO> menulist;
    View rootView;                      //mainlistfragment view
    View promptsView;                   //itemprompts
    AlertDialog.Builder alertDialogBuilder;
    Fragment itemlistfragment;          //itemlistfragment
    static ExpandableListView expandableList;
    ListView itemList;
    MyExpandableAdapter adapter;        //大&中項目清單用
    MyBaseAdapter baseadapter;          //小項目清單用
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private List<ItemVO> items = new ArrayList<ItemVO>();               //暫存目前所選的小項目清單
    LayoutInflater inflater;                                            //layout.mainlistfragment
    private int position;                                               //暫存目前所選的小項目index
    String urlstr = AppController.SERVERIP + "/LuLu_Proj02/menu";
    static int previousItem = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        rootView = inflater.inflate(R.layout.mainlistfragment, container, false);
        expandableList = (ExpandableListView)rootView.findViewById(R.id.expandablelist);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        adapter = new MyExpandableAdapter(parentItems, childItems);
        adapter.setInflater(inflater);
        expandableList.setAdapter(adapter);
        new RetrieveJsonContentTask().execute();
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    expandableList.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });  //關閉非使用中的Menu(拿掉會有bug)
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {          //在此控制被選中的SubMenu
            private List<SubMenuVO> child;
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                child = (List<SubMenuVO>) childItems.get(groupPosition);
                findViews(child.get(childPosition).getItems());
                //Toast.makeText(getActivity(), child.get(childPosition).getSubmenu_name(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return rootView;
    }

    public void findViews(List<ItemVO> selecteditem){   //產生小項目清單
        this.items = selecteditem;
        itemlistfragment = MainListFragment.this.getFragmentManager().findFragmentById(R.id.itemlistfrag);
        itemList = (ListView)itemlistfragment.getView().findViewById(R.id.itemfraglist);
        baseadapter = new MyBaseAdapter(items);
        baseadapter.setInflater(inflater);
        itemList.setAdapter(baseadapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {             //在此控制被選中的Item
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {   //產生訂單清單
                //Toast.makeText(getActivity(), items.get(position).getItem_name(),Toast.LENGTH_SHORT).show();
                MainListFragment.this.position = position;
                promptsView = inflater.inflate(R.layout.itemprompts,null);
                alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(promptsView);
                final EditText item_input = (EditText)promptsView.findViewById(R.id.item_quantity_input);
                TextView item_name = (TextView)promptsView.findViewById(R.id.item_name);
                item_name.setText(items.get(position).getItem_name());
                alertDialogBuilder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("確認",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!item_input.getText().toString().isEmpty()) {
                            OrderAddedListener.addOrder(Integer.parseInt(item_input.getText().toString()),items.get(MainListFragment.this.position));
                        }else
                            dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private class RetrieveJsonContentTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getText(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlstr,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            LuLuDBHelper dbhelper = new LuLuDBHelper();
                            menulist = dbhelper.getMenusFromJSON(response);
                            if (menulist == null || menulist.isEmpty()) {
                                progressDialog.dismiss();
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getString(R.string.nodata))
                                        .setNeutralButton(R.string.submit,new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,	int which) {}
                                                })
                                        .show();
                            } else {
                                Log.i("MENUGET",menulist.toString());
                                Log.i("MENUSIZE",""+menulist.size());
                                setGroupParents();
                                setChildData();
                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                    progressDialog.dismiss();
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getString(R.string.error))
                            .setNeutralButton(R.string.submit,new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,	int which) {}
                                    })
                            .show();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
            return null;
        }
    } // end of nested class RetrieveJsonContentTask

    public void setGroupParents() {
        for(MenuVO menu : menulist) {
            parentItems.add(menu.getMenu_name());
        }
    }

    public void setChildData() {
        for(MenuVO menu : menulist) {
            childItems.add(menu.getSubmenus());
        }
    }

    public interface onOrderAddedListener{
        void addOrder(int quantity,ItemVO item);
    }

    onOrderAddedListener OrderAddedListener;

     @Override
     public void onActivityCreated(Bundle savedInstanceState){
         super.onActivityCreated(savedInstanceState);
         OrderAddedListener = (onOrderAddedListener)getActivity();
     }
}
