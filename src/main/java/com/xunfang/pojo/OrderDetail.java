package com.xunfang.pojo;

public class OrderDetail {
    private int id;
    private int oid;
    private OrderInfo oi;
    private int pid;
    private ProductInfo pi;
    private int num;


    //前端需要
    private double price;
    private double totalprice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public OrderInfo getOi() {
        return oi;
    }

    public void setOi(OrderInfo oi) {
        this.oi = oi;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public ProductInfo getPi() {
        return pi;
    }

    public void setPi(ProductInfo pi) {
        this.pi = pi;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return pi.getPrice();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalprice() {
        return num*this.getPrice();
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", oid=" + oid +
                ", oi=" + oi +
                ", pid=" + pid +
                ", pi=" + pi +
                ", num=" + num +
                ", price=" + price +
                ", totalprice=" + totalprice +
                '}';
    }
}
