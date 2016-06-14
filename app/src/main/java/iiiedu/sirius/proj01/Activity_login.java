package iiiedu.sirius.proj01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import iiiedu.sirius.proj01.model.GsonRequest;
import iiiedu.sirius.proj01.model.LoginResultVO;
import iiiedu.sirius.proj01.model.MealVO;
import iiiedu.sirius.proj01.model.UserInfoVO;

public class Activity_login extends AppCompatActivity {
    private Button btn_login;
    private EditText account;
    private EditText password;
    private EditText peoplenumber;
    private EditText site;
    private EditText seat;
    String urlstr = AppController.SERVERIP + "/LuLu_Proj02/login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login=(Button)findViewById(R.id.login);
        account=(EditText)findViewById(R.id.account);
        password=(EditText)findViewById(R.id.password);
        peoplenumber=(EditText)findViewById(R.id.peoplenumber);
        site=(EditText)findViewById(R.id.site_id);
        seat=(EditText)findViewById(R.id.seat_id);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(account.getText().toString() != "")
                    if(password.getText().toString() != "")
                        if(peoplenumber.getText().toString() != "")
                            if(site.getText().toString() != "")
                                if(seat.getText().toString() != "")
                                    login();
            }
        });
    }

    public void login(){
        UserInfoVO user = new UserInfoVO();
        user.setAccount(account.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPeoplenumber(Integer.parseInt(peoplenumber.getText().toString()));
        user.setSite_name(site.getText().toString());
        user.setSeat_name(seat.getText().toString());
        GsonRequest<LoginResultVO> loginreq = new GsonRequest<LoginResultVO>(Request.Method.POST,urlstr,null,user,LoginResultVO.class,new Response.Listener<LoginResultVO>() {
            @Override
            public void onResponse(LoginResultVO response) {
                if("Success".equals(response.getMessage())) {
                    Log.i("LoginSuccess", response.getMessage());
                    Log.i("EmpName", response.getEmp_name());
                    AppController.userinfo = response;
                    Intent intent = new Intent(Activity_login.this, BookListActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Activity_login.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.i("LoginFail", response.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        AppController.getInstance().addToRequestQueue(loginreq);
    }
}
