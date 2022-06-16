/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.aggregation.Aggregator;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.com.google.common.collect.Lists;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.*;
import com.hazelcast.map.listener.MapListener;
import com.hazelcast.map.listener.MapPartitionLostListener;
import com.hazelcast.projection.Projection;
import com.hazelcast.query.Predicate;
import com.th6.model.CustomerPayment;
import com.th6.model.OrderPayment;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author ASUS
 */
public class ProcessSinkOrderPayment extends ProcessWindowFunction<OrderPayment, CustomerPayment, Tuple2<Integer, Integer>, TimeWindow> {

    private String hazelName = "CustomerPayment1";
    private String hazelUrl = "";
    private transient ValueState<Tuple2<Integer, Float>> sum;
    IMap<Tuple2<Integer, Integer>, HazelcastJsonValue> map;
    private ObjectMapper mapperHazel = null;
    private static final long serialVersionUID = 1111343231121L;

    public ProcessSinkOrderPayment(String url) {
        hazelUrl = url;
    }

    @Override
    public void process(
            Tuple2<Integer, Integer> key,
            ProcessWindowFunction<OrderPayment, CustomerPayment, Tuple2<Integer, Integer>, TimeWindow>.Context context,
            Iterable<OrderPayment> items,
            Collector<CustomerPayment> collector) throws Exception {

        System.out.println("key.f0");
        System.out.println(key.f0);
        Tuple2<Integer, Float> currentSum = sum.value();
        for (Map.Entry<Tuple2<Integer, Integer>, HazelcastJsonValue> entry : map.entrySet()) {
            Tuple2 keyEntry = entry.getKey();
            if (keyEntry.equals(key)
                    && currentSum.f0 == 0) {
                HazelcastJsonValue jsonString = entry.getValue();
                ObjectMapper mapper = new ObjectMapper();
                CustomerPayment customerPayment = mapper.readValue(jsonString.toString(), CustomerPayment.class);
                currentSum.f0 = customerPayment.USETIME;
                currentSum.f1 = customerPayment.TOTALAMOUNT;
            }
        }

        // update currentSum.usetime
        currentSum.f0++;

        //update currenSum.totalAmount
        ArrayList<OrderPayment> orderPayments = Lists.newArrayList(items);
        for (OrderPayment orderPayment : orderPayments) {
            try {
                currentSum.f1 += orderPayment.TOTALAMOUNT;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // update the state
        sum.update(currentSum);

        collector.collect(
                new CustomerPayment(
                        key.f0,
                        items.iterator().next().BRANDID,
                        key.f1,
                        items.iterator().next().TRANSACTIONTYPEID,
                        currentSum.f0,
                        currentSum.f1));
        CustomerPayment value = new CustomerPayment(
                key.f0,
                items.iterator().next().BRANDID,
                key.f1,
                items.iterator().next().TRANSACTIONTYPEID,
                currentSum.f0,
                currentSum.f1);
        map.put(key, new HazelcastJsonValue(mapperHazel.writeValueAsString(value)));
    }

    @Override
    public void open(Configuration config) {
        mapperHazel = new ObjectMapper();
        ValueStateDescriptor<Tuple2<Integer, Float>> descriptor
                = new ValueStateDescriptor<>(
                        "sum", // the state name
                        TypeInformation.of(new TypeHint<Tuple2<Integer, Float>>() {
                        }), // type information
                        Tuple2.of(0, 0F)); // default value of the state, if nothing was set
        sum = getRuntimeContext().getState(descriptor);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("dev");
        clientConfig.getNetworkConfig().addAddress("10.1.6.216:5701");

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        map = client.getMap(hazelName);

    }
}
