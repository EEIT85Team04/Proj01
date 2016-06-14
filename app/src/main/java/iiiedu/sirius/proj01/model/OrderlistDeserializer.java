package iiiedu.sirius.proj01.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Timestamp;

public class OrderlistDeserializer implements JsonDeserializer<OrderListVO> {

    @Override
    public OrderListVO deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final Integer ord_id = jsonObject.get("ord_id").getAsInt();
        final String tableno = jsonObject.get("tableno").getAsString();
        final Integer bill_id = jsonObject.get("bill_id").getAsInt();
        final Timestamp ord_time = new Timestamp(jsonObject.get("ord_time").getAsInt());
        final Integer ord_money = jsonObject.get("ord_money").getAsInt();
        final Integer ord_status = jsonObject.get("ord_status").getAsInt();

        final OrderListVO orderlist = new OrderListVO();
        orderlist.setOrd_id(ord_id);
        orderlist.setTableno(tableno);
        orderlist.setBill_id(bill_id);
        orderlist.setOrd_time(ord_time);
        orderlist.setOrd_money(ord_money);
        orderlist.setOrd_status(ord_status);
        return orderlist;
    }
}
