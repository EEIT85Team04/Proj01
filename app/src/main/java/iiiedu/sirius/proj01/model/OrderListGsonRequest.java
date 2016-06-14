package iiiedu.sirius.proj01.model;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class OrderListGsonRequest<T> extends JsonRequest<T> {
    private final Gson myGson = new GsonBuilder()
            .registerTypeAdapter(OrderListVO.class, new OrderlistDeserializer()).create();
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private String requestBody = null;
    private static final String PROTOCOL_CHARSET = "utf-8";

    public OrderListGsonRequest(int method,
                       String url,
                       Map<String, String> headers,
                       Object parameterObject,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener){
        super(method, url, null, listener, errorListener);
        try {
            if (parameterObject != null)
                this.requestBody = gson.toJson(parameterObject);
        }catch(Exception e){
            e.printStackTrace();
        }
        this.headers = headers;
        this.clazz = clazz;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        T obj;
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            obj = myGson.fromJson(json, clazz);
            return Response.success(obj,HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException je) {
            je.printStackTrace();
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public byte[] getBody() {
        try {
            return requestBody == null ? null : requestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    requestBody, PROTOCOL_CHARSET);
            return null;
        }
    }
}
