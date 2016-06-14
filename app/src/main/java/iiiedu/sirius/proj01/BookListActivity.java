package iiiedu.sirius.proj01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iiiedu.sirius.proj01.model.CusOrderVO;
import iiiedu.sirius.proj01.model.DetailMealVO;
import iiiedu.sirius.proj01.model.GsonRequest;
import iiiedu.sirius.proj01.model.ItemVO;
import iiiedu.sirius.proj01.model.LoginResultVO;
import iiiedu.sirius.proj01.model.MealMenuVO;
import iiiedu.sirius.proj01.model.MealVO;
import iiiedu.sirius.proj01.model.OrderListGsonRequest;
import iiiedu.sirius.proj01.model.OrderListVO;
import iiiedu.sirius.proj01.model.OrderdetailVO;


public class BookListActivity extends Activity implements MainListFragment.onOrderAddedListener, MealDetailDialogFragment.MealInputListener {
    ExpandableListView orderList;
    Button btn_bill;
    Button btn_sendorderlist;
    Button btn_clearorderlist;
    Fragment itemlistfragment;          //itemlistfragment
    MyMealAdapter mealAdapter;          //小項目清單用
    ListView itemList;
    TextView btn_meal;
    OrderDetailExpandableAdapter ordersadapter;
    private List<OrderdetailVO> orders;             //暫存目前所選的訂單清單
    private OrderdetailVO order;                    //暫存目前所選的訂單
    private OrderListVO orderlist;
    private int position;                           //暫存目前所選的訂單index
    View promptsView;
    AlertDialog.Builder alertDialogBuilder;
    String urlstr = AppController.SERVERIP + "/LuLu_Proj02/orderlist";
    String mealurl = AppController.SERVERIP + "/LuLu_Proj02/ismeal/T";
    private boolean orderstatus = true;             //判斷訂單是否可送

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orderList=(ExpandableListView)findViewById(R.id.orderlistView);
        orderList.setDividerHeight(2);
        orderList.setClickable(true);
        btn_meal=(TextView)findViewById(R.id.meal);
        orders = new ArrayList<OrderdetailVO>();
        btn_sendorderlist = (Button)findViewById(R.id.sendorderlist);
        btn_clearorderlist = (Button)findViewById(R.id.clearorderlist);
        btn_bill = (Button)findViewById(R.id.btn_bill);
        new RetrieveJsonContentTask().execute();
        btn_clearorderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orders.size() != 0) {
                    orders.clear();
                    ordersadapter.notifyDataSetChanged();
                }
            }
        });
        btn_sendorderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderstatus && orders.size() != 0) {
                    sendOrderList();
                }
            }
        });
        btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillDialogFragment billdialog = new BillDialogFragment();
                billdialog.show(getFragmentManager(),"billdialog");
            }
        });
    }

    public void onBtn_MealClick(View v) {
        itemlistfragment = this.getFragmentManager().findFragmentById(R.id.itemlistfrag);
        itemList = (ListView)itemlistfragment.getView().findViewById(R.id.itemfraglist);
        mealAdapter = new MyMealAdapter(AppController.getInstance().getMeallist());
        mealAdapter.setInflater(getLayoutInflater());
        itemList.setAdapter(mealAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {             //在此控制被選中的Item
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {   //產生套餐選項
                AppController.getInstance().setMealposition(position);      //暫存目前選擇的套餐Index
                MealDetailDialogFragment mealdialog = new MealDetailDialogFragment();
                mealdialog.setCancelable(false);
                mealdialog.show(getFragmentManager(),"mealdialog");
            }
        });
        if(MainListFragment.previousItem != -1){
            MainListFragment.expandableList.collapseGroup(MainListFragment.previousItem);
            MainListFragment.previousItem = -1;
        }
    }           //當套餐按鈕被點擊時觸發

    @Override
    public void addOrder(int quantity, ItemVO item)     //接收MainListFragment的回傳值(單點)
    {
        order = new OrderdetailVO();
        order.setItem_name(item.getItem_name());
        order.setOrd_det_price(item.getPrice());
        order.setQuantity(quantity);
        order.setMeal_status(1);
        orders.add(order);
        setOrder();
    }

    @Override
    public void onMealInputComplete(MealVO meal)     //接收套餐選擇完成的回傳值
    {
        CusOrderVO cusorder = null;
        List<CusOrderVO> cusorders = new ArrayList<CusOrderVO>();
        order = new OrderdetailVO();
        order.setItem_name(meal.getMeal_name());
        order.setOrd_det_price(meal.getPrice());
        order.setQuantity(1);                   //套餐數量一定為1
        order.setMeal_status(2);                //套餐狀態為2
        for(MealMenuVO mealmenu : meal.getMealmenus()){
            for(DetailMealVO detalmeal : mealmenu.getDetailmeals()) {
                if(detalmeal.getItemVO().getMealcount() != 0) {
                    cusorder = new CusOrderVO();
                    cusorder.setMenu_id(mealmenu.getMenu_id());
                    cusorder.setCus_meal_name(meal.getMeal_name());
                    cusorder.setCus_item_name(detalmeal.getItemVO().getItem_name());
                    cusorder.setQuantity(detalmeal.getItemVO().getMealcount());
                    cusorders.add(cusorder);
                }
            }
        }
        order.setCusorders(cusorders);
        orders.add(order);
        setOrder();
    }

    public void setOrder(){
        ordersadapter = new OrderDetailExpandableAdapter(orders);
        ordersadapter.setInflater(getLayoutInflater());
        orderList.setAdapter(ordersadapter);
        orderList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick( AdapterView<?> parent, View view, int position, long id) {
                long packedPosition = orderList.getExpandableListPosition(position);
                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);

                //  if group item clicked
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    if(orders.get(groupPosition).getMeal_status() == 1) {       //單點用
                        promptsView = getLayoutInflater().inflate(R.layout.itemprompts, null);
                        alertDialogBuilder = new AlertDialog.Builder(BookListActivity.this);
                        alertDialogBuilder.setView(promptsView);
                        final EditText item_input = (EditText) promptsView.findViewById(R.id.item_quantity_input);
                        TextView item_name = (TextView) promptsView.findViewById(R.id.item_name);
                        item_name.setText(orders.get(groupPosition).getItem_name());
                        item_input.setText(orders.get(groupPosition).getQuantity().toString());
                        BookListActivity.this.position = groupPosition;
                        alertDialogBuilder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                orders.remove(BookListActivity.this.position);
                                ordersadapter.notifyDataSetChanged();
                            }
                        }).setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!item_input.getText().toString().isEmpty()) {
                                    orders.get(BookListActivity.this.position).setQuantity(Integer.parseInt(item_input.getText().toString()));
                                    ordersadapter.notifyDataSetChanged();
                                } else
                                    dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }else if(orders.get(groupPosition).getMeal_status() == 2) {      //套餐用
                        promptsView = getLayoutInflater().inflate(R.layout.mealdialog, null);
                        alertDialogBuilder = new AlertDialog.Builder(BookListActivity.this);
                        alertDialogBuilder.setView(promptsView);
                        TextView meal_name = (TextView) promptsView.findViewById(R.id.meal_name);
                        meal_name.setText(orders.get(groupPosition).getItem_name());
                        BookListActivity.this.position = groupPosition;
                        alertDialogBuilder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                orders.remove(BookListActivity.this.position);
                                ordersadapter.notifyDataSetChanged();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    return true;
                }
                //  if child item clicked
                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    //  ...
                    //onChildLongClick(groupPosition, childPosition);
                    return true;
                }
                return false;
            }
        });
    }

    private class RetrieveJsonContentTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(BookListActivity.this);
            progressDialog.setMessage(getText(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            GsonRequest<MealVO[]> mealreq = new GsonRequest<MealVO[]>(Request.Method.GET,mealurl,null,null,MealVO[].class,new Response.Listener<MealVO[]>() {
                @Override
                public void onResponse(MealVO[] response) {
                    if (response == null) {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(BookListActivity.this)
                                .setMessage(getString(R.string.nodata))
                                .setNeutralButton(R.string.submit,new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,	int which) {}
                                })
                                .show();
                    } else {
                        Log.i("MEALGET",response.toString());
                        Log.i("MEALSIZE",""+response.length);
                        AppController.getInstance().setMeallist(response);
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                    progressDialog.dismiss();
                    new AlertDialog.Builder(BookListActivity.this)
                            .setMessage(getString(R.string.error))
                            .setNeutralButton(R.string.submit,new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,	int which) {}
                            })
                            .show();
                }
            });
            AppController.getInstance().addToRequestQueue(mealreq);
            return null;
        }
    }// end of nested class RetrieveJsonContentTask

    public void sendOrderList(){
        LoginResultVO userinfo = AppController.userinfo;
        int subtotal = 0;
        for(OrderdetailVO order:orders){
            subtotal += order.getOrd_det_price()*order.getQuantity();
        }
        orderlist = new OrderListVO();
        orderlist.setBill_id(userinfo.getBill_id());
        orderlist.setTableno(userinfo.getTableno());
        orderlist.setOrd_money(subtotal);
        orderlist.setOrders(orders);
        OrderListGsonRequest<OrderListVO> gReq = new OrderListGsonRequest<OrderListVO>(Request.Method.POST,urlstr,null,orderlist,OrderListVO.class,new Response.Listener<OrderListVO>() {
            @Override
            public void onResponse(OrderListVO response) {
                orderstatus = true;
                if(response != null) {
                    Toast.makeText(BookListActivity.this, "傳送成功", Toast.LENGTH_SHORT).show();
                    orders.clear();
                    ordersadapter.notifyDataSetChanged();
                    AppController.getInstance().setBillstatus(true);
                }else{
                    Toast.makeText(BookListActivity.this,"伺服器錯誤",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                orderstatus = true;
                error.printStackTrace();
                Toast.makeText(BookListActivity.this,"傳送失敗",Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(gReq);
        orderstatus = false;
    }       //送出訂單

}