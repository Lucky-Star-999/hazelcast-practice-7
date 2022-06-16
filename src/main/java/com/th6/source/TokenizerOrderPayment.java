/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.source;

import java.util.Arrays;
import java.util.List;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.util.Collector;
import com.th6.model.OrderPayment;

/**
 *
 * @author ASUS
 */
public class TokenizerOrderPayment implements FlatMapFunction<String, OrderPayment> {

    @Override
    public void flatMap(String value, Collector<OrderPayment> out) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        List<OrderPayment> serviceModels = Arrays.asList(mapper.readValue(value, OrderPayment[].class));

        for (OrderPayment item : serviceModels) {
            // convert string to my object
            out.collect(item);
        }

    }
}
