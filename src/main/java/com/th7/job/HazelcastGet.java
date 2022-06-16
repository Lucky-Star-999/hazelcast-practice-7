/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.th7.job;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.IMap;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.Map;

/**
 *
 * @author ASUS
 */
public class HazelcastGet {
    public static void main(String[] args) throws Exception{
        IMap<Tuple2<Integer, Integer>, HazelcastJsonValue> mapMessageFlink;
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("dev");
        clientConfig.getNetworkConfig().addAddress("10.1.6.216:5701");

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        mapMessageFlink = client.getMap("CustomerPayment1");
        for (Map.Entry<Tuple2<Integer, Integer>, HazelcastJsonValue> entry : mapMessageFlink.entrySet()) {
            Tuple2<Integer, Integer> k = entry.getKey();
            HazelcastJsonValue v = entry.getValue();
            System.out.println("key: " + k + ", value: " + v);
        }
        System.out.println( "Map Size:" + mapMessageFlink.size() );
    }
}
