执行环境
    创建本地环境
        StreamExecutionEnvironment.createLocalEnvironment();

    创建远程环境
    根据执行环境自动创建

    提交job
        env.execute()
    一个app(java程序)下可以有多个job
        env.executeAsync()

源算子
    可以从集合读取数据
        env.fromCollection()
    可以从固定的元素读取数据
        env.fromElements()
    可以从文件读取数据
        env.readTextFile()---已经过时
        读取文件连接器
         FileSource<String> fileSource
                        = FileSource.forRecordStreamFormat(new TextLineInputFormat(),
                                new Path("D:\\Atguigu\\BigData\\上课资料\\实时数仓\\code\\bigdata0710-parent\\flink0710\\input\\wordcount.txt"))
                        .build();
    可以从指定端口号读取数据
        env.socketTextStream()
    可以从Kafka主题中读取数据--操作Kafka的连接器
     KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                    .setBootstrapServers(Kafka集群的地址)
                    .setTopics(要消费的主题的名称)
                    .setGroupId(消费者组的名称)
                    .setStartingOffsets(消费起始偏移量位点)
                    .setValueOnlyDeserializer(反序列化器)
                    .build();
                    env.fromSource(kafkaSource,水位线，source名称);
    如果以后在开发flink程序的时候，需要读取外部数据源的数据或者将处理结果写到外部数据库
    首先看官网flink是否提供了线程的连接器，如果有，直接按照官网的提示使用
    如果没有，可以自定义数据源
        class 类名 implements SourceFunction{
            run:生成数据的逻辑写到当前的方法中
            cancel:取消job的时候，会被调用
        }
        env.addSource()
基本转换算子
    map
        通过方法的返回值将数据传递到下面
    filter
        方法的返回值是Boolean，如果为true，向下传递；如果为false，直接过滤掉
    flatMap
        将整体拆分为个体的过程
        可以向下多次传递数据 通过Collector中的collect方法向下传递数据





















