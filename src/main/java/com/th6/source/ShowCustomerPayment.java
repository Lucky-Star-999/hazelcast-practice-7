/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.source;

import com.th6.model.CustomerPayment;
import java.util.List;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import scala.Tuple2;

/**
 *
 * @author ASUS
 */
public class ShowCustomerPayment implements FlatMapFunction<CustomerPayment, String> {
    @Override
    public void flatMap(CustomerPayment t, Collector<String> out) {
        String kq = t.toString();
        out.collect(kq);

    }
}
