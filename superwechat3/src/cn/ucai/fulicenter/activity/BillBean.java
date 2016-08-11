package cn.ucai.fulicenter.activity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/11.
 */
public class BillBean implements Serializable {
    String OrderName;
    String OrderPhone;
    String OrderProvince;
    String OrderStreet;

    public BillBean(String orderName, String orderPhone, String orderProvince, String orderStreet) {
        OrderName = orderName;
        OrderPhone = orderPhone;
        OrderProvince = orderProvince;
        OrderStreet = orderStreet;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public String getOrderPhone() {
        return OrderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        OrderPhone = orderPhone;
    }

    public String getOrderProvince() {
        return OrderProvince;
    }

    public void setOrderProvince(String orderProvince) {
        OrderProvince = orderProvince;
    }

    public String getOrderStreet() {
        return OrderStreet;
    }

    public void setOrderStreet(String orderStreet) {
        OrderStreet = orderStreet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillBean billBean = (BillBean) o;

        if (!OrderName.equals(billBean.OrderName)) return false;
        if (!OrderPhone.equals(billBean.OrderPhone)) return false;
        if (!OrderProvince.equals(billBean.OrderProvince)) return false;
        return OrderStreet.equals(billBean.OrderStreet);

    }

    @Override
    public int hashCode() {
        int result = OrderName.hashCode();
        result = 31 * result + OrderPhone.hashCode();
        result = 31 * result + OrderProvince.hashCode();
        result = 31 * result + OrderStreet.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BillBean{" +
                "OrderName='" + OrderName + '\'' +
                ", OrderPhone='" + OrderPhone + '\'' +
                ", OrderProvince='" + OrderProvince + '\'' +
                ", OrderStreet='" + OrderStreet + '\'' +
                '}';
    }
}
