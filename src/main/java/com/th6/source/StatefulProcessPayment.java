/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.source;

import com.th6.model.PaymentTotal;
import com.th6.model.CustomerPayment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import scala.Tuple2;
import com.th6.model.OrderPayment;
import java.util.ArrayList;
import java.util.List;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 *
 * @author ASUS
 */
public class StatefulProcessPayment extends KeyedProcessFunction<Tuple2<Integer, Integer>, OrderPayment, CustomerPayment> {

    private transient ValueState<PaymentTotal> state;
    
    @Override
    public void open(Configuration parameters) {
        state = getRuntimeContext().getState(
                new ValueStateDescriptor<>("myState", PaymentTotal.class));

    }

    @Override
    public void processElement(OrderPayment orderPayment, KeyedProcessFunction<Tuple2<Integer, Integer>, OrderPayment, CustomerPayment>.Context context, Collector<CustomerPayment> out) throws Exception {
        PaymentTotal paymentTotal = state.value();
        if (paymentTotal == null) {
            paymentTotal = new PaymentTotal();
        }
        
        paymentTotal.totalAmount += orderPayment.TOTALAMOUNT;
        paymentTotal.useTime++;
        
        state.update(paymentTotal);

        out.collect(new CustomerPayment(orderPayment.CUSTOMERID, orderPayment.BRANDID,
                orderPayment.PAYABLETYPE, orderPayment.TRANSACTIONTYPEID,
                paymentTotal.useTime, paymentTotal.totalAmount));
    }

    
}
