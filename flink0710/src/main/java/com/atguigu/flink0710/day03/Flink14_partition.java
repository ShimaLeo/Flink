package com.atguigu.flink0710.day03;

import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Flink14_partition {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        //从指定的网络端口读取数据

        env.setParallelism(4);

        DataStreamSource<String> stream = env.socketTextStream("hadoop102", 8888);

        //stream.shuffle().print();
        //stream.rebalance().print();
        //stream.rescale().print();
        //stream.broadcast().print();
        //stream.global().print();
        stream.partitionCustom(
                new MyPartitioner(),
                value -> value
        ).print();

        env.execute();
    }
}
class MyPartitioner implements Partitioner<String> {

    @Override
    public int partition(String key, int numPartitions) {
        return Integer.parseInt(key) % numPartitions;
    }
}
