package iiiedu.sirius.proj01;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.List;

import iiiedu.sirius.proj01.model.BillVO;
import iiiedu.sirius.proj01.model.LoginResultVO;
import iiiedu.sirius.proj01.model.LruBitmapCache;
import iiiedu.sirius.proj01.model.MealVO;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    public static final String SERVERIP = "http://192.168.23.147:8081";

    private MealVO[] meallist = null;

    private Integer mealposition;

    private BillVO bill;

    private boolean billstatus = true;

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;


    private static AppController mInstance;

    public static LoginResultVO userinfo;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public MealVO[] getMeallist(){
        return meallist;
    }

    public void setMeallist(MealVO[] meallist){
        this.meallist = meallist;
    }

    public Integer getMealposition() {
        return mealposition;
    }

    public void setMealposition(Integer mealposition) {
        this.mealposition = mealposition;
    }

    public BillVO getBill(){
        return bill;
    }

    public void setBill(BillVO bill){
        this.bill = bill;
        billstatus = false;
    }

    public boolean getBillstatus(){
        return billstatus;
    }

    public void setBillstatus(boolean billstatus){
        this.billstatus = billstatus;
    }

}
