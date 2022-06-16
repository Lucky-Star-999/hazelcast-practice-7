/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.source;

/**
 *
 * @author ASUS
 */
import com.th6.model.OrderPayment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;

public class OrderPaymentKeyByDescription implements KeySelector<OrderPayment, Tuple2<Integer, Integer>> {
    @Override
    public Tuple2<Integer, Integer> getKey(OrderPayment orderPayment) throws Exception {
        Tuple2<Integer, Integer> result=new Tuple2<>();
        result.f0 = orderPayment.CUSTOMERID;
        result.f1 = orderPayment.PAYABLETYPE;
        return result;
    }
}
