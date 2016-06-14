package iiiedu.sirius.proj01;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import iiiedu.sirius.proj01.model.BillVO;
import iiiedu.sirius.proj01.model.GsonRequest;

public class BillDialogFragment extends DialogFragment {
    MyBillAdapter myBillAdapter;
    LayoutInflater inflater;
    Context context;
    View rootView;
    ExpandableListView expandableList;
    TextView billtotal;
    BillVO bill;
    String urlstr = AppController.SERVERIP + "/LuLu_Proj02/bill/" + AppController.userinfo.getBill_id();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.billprompts, null);
        expandableList = (ExpandableListView)rootView.findViewById(R.id.billExpandableListView);
        billtotal = (TextView)rootView.findViewById(R.id.billtotal);
        expandableList.setDividerHeight(2);
        expandableList.setClickable(true);
        expandableList.setGroupIndicator(null);
        if(AppController.getInstance().getBillstatus()) {
            new RetrieveJsonContentTask().execute();        //如果bill資料有更新
        }else{
            setListView();      //如果bill資料沒更新
        }
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("確認",null);
        return builder.create();
    }

    private void setListView(){
        bill = AppController.getInstance().getBill();
        billtotal.setText(bill.getBill_money().toString());
        myBillAdapter = new MyBillAdapter(bill);
        myBillAdapter.setInflater(inflater);
        expandableList.setAdapter(myBillAdapter);
    }

    private void setListView(BillVO bill){
        billtotal.setText(bill.getBill_money().toString());
        myBillAdapter = new MyBillAdapter(bill);
        myBillAdapter.setInflater(inflater);
        expandableList.setAdapter(myBillAdapter);
    }

    private class RetrieveJsonContentTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(getText(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            GsonRequest<BillVO> billreq = new GsonRequest<BillVO>(Request.Method.POST,urlstr,null,null,BillVO.class,new Response.Listener<BillVO>() {
                @Override
                public void onResponse(BillVO response) {
                    if (response == null) {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(context)
                                .setMessage(getString(R.string.nodata))
                                .setNeutralButton(R.string.submit,new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,	int which) {}
                                })
                                .show();
                    } else {
                        AppController.getInstance().setBill(response);
                        setListView(response);
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("BILLERROR", error.getMessage(), error);
                    progressDialog.dismiss();
                    new AlertDialog.Builder(context)
                            .setMessage(getString(R.string.error))
                            .setNeutralButton(R.string.submit,new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,	int which) {}
                            })
                            .show();
                }
            });
            AppController.getInstance().addToRequestQueue(billreq);
            return null;
        }
    }// end of nested class RetrieveJsonContentTask

}
