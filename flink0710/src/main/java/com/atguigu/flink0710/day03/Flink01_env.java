package com.atguigu.flink0710.day03;

import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Flink环境准备
 */
public class Flink01_env {
    public static void main(String[] args) throws Exception {
        //准备环境
        //获取本地执行环境，不带webUI
        //LocalStreamEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        //获取本地执行环境，带webUI
        //StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI();
        StreamExecutionEnvironment env
                = StreamExecutionEnvironment.createRemoteEnvironment("hadoop102", 8081, "D:\\Atguigu\\BigData\\上课资料\\实时数仓\\code\\bigdata0710-parent\\flink0710\\target\\flink0710-1.0-SNAPSHOT.jar");

        env.readTextFile("D:\\Atguigu\\BigData\\上课资料\\实时数仓\\code\\bigdata0710-parent\\flink0710\\input\\wordcount.txt")
                .print();
        env.execute();

    }
}
