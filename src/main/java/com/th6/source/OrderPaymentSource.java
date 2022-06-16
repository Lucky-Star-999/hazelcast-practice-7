/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.source;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import com.th6.model.OrderPayment;

/**
 *
 * @author ASUS
 */
public class OrderPaymentSource implements SourceFunction<String> {

    public int ORDER_NUMBER = 5;

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        for (int i = 1; i <= ORDER_NUMBER; i++) {
            OrderPayment orderPayment = new OrderPayment();
            sourceContext.collect(orderPayment.toString());
            System.out.println("Order Payment " + i + ": " + orderPayment.toString());
        }

        sourceContext.close();
    }

    @Override
    public void cancel() {
        //running=false;
    }
}
