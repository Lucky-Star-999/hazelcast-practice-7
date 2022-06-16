/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.source;

import java.util.List;
import org.apache.flink.api.java.functions.KeySelector;
import com.th6.model.OrderPayment;
import java.util.ArrayList;
import scala.Tuple2;

/**
 *
 * @author ASUS
 */
public class KeyByOrderPayment implements KeySelector<OrderPayment, Tuple2<Integer, Integer>> {

    @Override
    public Tuple2<Integer, Integer> getKey(OrderPayment orderPayment) throws Exception {
        return new Tuple2<>(orderPayment.CUSTOMERID, orderPayment.PAYABLETYPE);
    }
}
