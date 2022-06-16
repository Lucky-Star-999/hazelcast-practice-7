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
public class CustomerPayment {

    public String CUSTOMERPAYMENTID;
    public int CUSTOMERID;             //Mã khách hàng
    public int BRANDID;                //Site
    public int PAYABLETYPE;            //HTTT(1.Thanh toán trực tuyến, 2.Tiền mặt, 3.Cà thẻ, 4.Chuyển khoản)
    public int TRANSACTIONTYPEID;      //Cổng thanh toán trực tuyến
    public int USETIME;                //Số lần dùng HTTT này
    public float TOTALAMOUNT;          //Tổng tiền thanh toán)

    public CustomerPayment(int CUSTOMERID, int BRANDID, int PAYABLETYPE, int TRANSACTIONTYPEID, int USETIME, float TOTALAMOUNT) {
        Random generator = new Random();
        this.CUSTOMERPAYMENTID = String.valueOf(generator.nextInt(100));
        this.CUSTOMERID = CUSTOMERID;
        this.BRANDID = BRANDID;
        this.PAYABLETYPE = PAYABLETYPE;
        this.TRANSACTIONTYPEID = TRANSACTIONTYPEID;
        this.USETIME = USETIME;
        this.TOTALAMOUNT = TOTALAMOUNT;
    }
    
    
    
    @Override
    public String toString() {    
        return "[{\"CUSTOMERPAYMENTID\": \"" + CUSTOMERPAYMENTID + "\", " +
                "\"CUSTOMERID\": \"" + CUSTOMERID + "\", " +
                "\"BRANDID\": \"" + BRANDID + "\", " +
                "\"PAYABLETYPE\": \"" + PAYABLETYPE + "\", " +
                "\"TRANSACTIONTYPEID\": \"" + TRANSACTIONTYPEID + "\", " +
                "\"USETIME\": \"" + USETIME + "\", " +
                "\"TOTALAMOUNT\": \"" + TOTALAMOUNT + "\"" +
                "}]";
    }
}
