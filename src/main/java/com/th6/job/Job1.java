/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.job;

import com.th6.source.OrderPaymentSource;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

/**
 *
 * @author ASUS
 */
public class Job1 {
    // Tạo và gửi JSON String cho đối tượng OrderPayment
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        
        DataStream<String> stringOutputStream = environment.addSource(new OrderPaymentSource());
         
        stringOutputStream.addSink(new FlinkKafkaProducer<>("localhost:9092",
                "linh-th-6",
                new SimpleStringSchema()));

        environment.execute();
    }
}
