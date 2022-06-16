/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.consumer;

import java.util.Properties;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

/**
 *
 * @author ASUS
 */
public class ConsumerJob1 {
    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "group1");

        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>("linh-th-6", new SimpleStringSchema(), properties);
        DataStream<String> eachKafkaData = env.addSource(kafkaConsumer);

        //Print ra de test
        eachKafkaData.print();

        // execute program
        env.execute("Flink consumer");
    }
}
