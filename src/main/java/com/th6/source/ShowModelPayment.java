/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.source;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import com.th6.model.OrderPayment;

/**
 *
 * @author ASUS
 */
public class ShowModelPayment implements FlatMapFunction<OrderPayment, OrderPayment> {

    @Override
    public void flatMap(OrderPayment value, Collector<OrderPayment> out) {
        System.out.println(value.toString());
        // convert string to my object
        out.collect(value);
    }
}
