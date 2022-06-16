/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.model;

/**
 *
 * @author ASUS
 */
public class PaymentTotal {
    public float totalAmount;
    public int useTime;
    public OrderPayment orderPayment;
    public long lastModified;

    public PaymentTotal() {
        totalAmount = 0;
        useTime = 0;
    }
    
    
}
