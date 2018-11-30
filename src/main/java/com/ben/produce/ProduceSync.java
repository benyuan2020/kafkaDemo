package com.ben.produce;


import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

public class ProduceSync {

    private static final String Topic = "an4";

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers","127.0.0.1:9092");
        properties.put("acks", "all");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("buffer.memory", "33554432");
        properties.put("compression.type","gzip");

        //控制台会打印生产者的信息
        Producer<String,String> producer = new KafkaProducer<String,String>(properties);
        LocalDateTime localDateTime = LocalDateTime.now();
        String curTime = localDateTime.toString();
        for (int i = 0; i <1000 ; i++) {
            String msg = curTime  + " "+Topic + " " + i  ;
//
            ProducerRecord<String,String> producerRecord = new ProducerRecord<String,String>(Topic,msg);
            System.out.println("分区：" + producerRecord.partition());
            System.out.println("key：" + producerRecord.key());
            System.out.println("value：" + producerRecord.value());
            System.out.println("主题：" + producerRecord.topic());
            System.out.println("生产：" + msg);
//            异步发送消息，失败会记录
            producer.send(producerRecord,new ProducerCallBack());
//            producer.send(producerRecord);

        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        producer.close();


        Map<String,Object> map = new HashMap<>();
        map.put("yuan","111");
        map.put("ying","222");
        map.put("ming","333");

        JSONObject json = new JSONObject(map);

        System.out.println("map ：" + map);
        System.out.println("json ：" + json);



    }






}