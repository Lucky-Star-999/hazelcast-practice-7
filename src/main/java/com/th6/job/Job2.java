/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th6.job;

import com.th6.model.CustomerPayment;
import java.util.List;
import java.util.Properties;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import scala.Tuple2;
import com.th6.source.KeyByOrderPayment;
import com.th6.source.OrderPaymentSource;
import com.th6.source.StatefulProcessPayment;
import com.th6.model.OrderPayment;
import com.th6.source.TokenizerOrderPayment;
import com.th6.source.ShowModelPayment;
import com.th6.source.ShowCustomerPayment;

/**
 *
 * @author ASUS
 */
public class Job2 {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "linh-group-1");

        FlinkKafkaConsumer<String> flinkKafkaConsumer
                = new FlinkKafkaConsumer<>("linh-th-6", new SimpleStringSchema(), properties);
        FlinkKafkaProducer<String> flinkKafkaProducer
                = new FlinkKafkaProducer<>("localhost:9092",
                        "linh-th-6-payment",
                        new SimpleStringSchema());

        DataStream<String> stringInputStream = env.addSource(flinkKafkaConsumer);
        DataStream<OrderPayment> message = stringInputStream.flatMap(new TokenizerOrderPayment());
        message = message.flatMap(new ShowModelPayment());

        DataStream<CustomerPayment> groupMessage
                = message.keyBy(new KeyByOrderPayment()).process(new StatefulProcessPayment());

        DataStream<String> stringGroupMessage
                = groupMessage.flatMap(new ShowCustomerPayment());

        stringGroupMessage.addSink(flinkKafkaProducer);
        groupMessage.print();

        env.execute();
    }
}
