/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.model;

import java.util.Random;

/**
 *
 * @author ASUS
 */
public class OrderPayment {
    public String ID;
    public int CUSTOMERID;          // Mã khách hàng
    public int BRANDID;             // Site
    public String SALEORDERID;      // mã SO
    public int PAYABLETYPE;         // HTTT(1.Thanh toán trực tuyến, 2.Tiền mặt, 3.Cà thẻ, 4.Chuyển khoản)
    public int TRANSACTIONTYPEID;   // Cổng thanh toán trực tuyến(Lưu khi PAYMENTTYPE=1)
    public long PAYTIME;            // Thời gian thanh toán
    public float TOTALAMOUNT;       // Tổng tiền thanh toán)

    public OrderPayment() {
        Random generator = new Random();
        ID = "ID";
        for (int i = 0; i < 5; i++) {
            ID += Integer.toString(generator.nextInt(10));
        }
        CUSTOMERID = generator.nextInt(100);
        BRANDID = generator.nextInt(100);
        SALEORDERID = "S";
        for (int i = 0; i < 5; i++) {
            SALEORDERID += Integer.toString(generator.nextInt(10));
        }
        PAYABLETYPE = generator.nextInt(4) + 1;
        TRANSACTIONTYPEID = generator.nextInt(2);
        PAYTIME = generator.nextLong();
        TOTALAMOUNT = generator.nextFloat();
    }

    @Override
    public String toString() {    
        return "[{\"ID\": \"" + ID + "\", " +
                "\"CUSTOMERID\": \"" + CUSTOMERID + "\", " +
                "\"BRANDID\": \"" + BRANDID + "\", " +
                "\"SALEORDERID\": \"" + SALEORDERID + "\", " +
                "\"PAYABLETYPE\": \"" + PAYABLETYPE + "\", " +
                "\"TRANSACTIONTYPEID\": \"" + TRANSACTIONTYPEID + "\", " +
                "\"PAYTIME\": \"" + PAYTIME + "\", " +
                "\"TOTALAMOUNT\": \"" + TOTALAMOUNT + "\"" +
                "}]";
    }
}
