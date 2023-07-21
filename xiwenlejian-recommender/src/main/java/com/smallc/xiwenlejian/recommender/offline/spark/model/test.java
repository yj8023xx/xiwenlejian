package com.smallc.xiwenlejian.recommender.offline.spark.model;

import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/19
 * @since com.smallc.xiwenlejian.recommender.offline.spark.model
 */
public class test {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        byte[] embeddingBytes = jedis.get("u1000".getBytes());
        ArrayList<Double> embedding = (ArrayList<Double>) deserialize(embeddingBytes);
        System.out.println(Arrays.toString(embedding.toArray()));
    }

    public static List<Double> deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        bis.close();
        return (List<Double>)obj;
    }

}
