/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th7.job;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ProcessingTimeTrigger;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import com.th6.model.OrderPayment;
import com.th6.model.CustomerPayment;
import com.th6.source.OrderPaymentKeyByDescription;
import com.th6.source.OrderPaymentTokenizer;
import com.th6.source.ProcessSinkOrderPayment;
import com.th6.source.SimpleStringSchema;

import java.util.*;

/**
 *
 * @author ASUS
 */
public class HazelcastPost {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "10.1.12.183:9092");
        properties.setProperty("group.id", "group1");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer <>("OrderPaymentLinh188192",  new SimpleStringSchema(), properties);
        DataStream<String> strInputStream = env.addSource(consumer);
        DataStream<OrderPayment> message=strInputStream.flatMap(new OrderPaymentTokenizer());
        DataStream<CustomerPayment>
                groupMessage= message.keyBy(new OrderPaymentKeyByDescription())
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .trigger(ProcessingTimeTrigger.create())
                .process(new ProcessSinkOrderPayment("10.1.6.216:5701"));
        env.execute();
    }
}
