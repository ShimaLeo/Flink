flink程序运行模式
    standalone
    yarn
    k8s
flink的部署模式
    会话模式--session
    单作业模式--per-job
    应用模式--application
集群中的主要角色
    客户端
        提交，接收封装参数
    Job Manager
        分发器
        JobMaster
        资源管理器 ResourceManager
    Task Manager
        task slot 任务槽
        执行任务
并行度
    在程序中，可以将算子的操作复制多份到不同的节点上去执行，提高处理能力
    每一个节点都是当前算子的子任务
    一个算子子任务的个数称之为并行度
    在flink中设置并行度的数量==等价于==设置分区数
并行度的设置
    程序中算子上设置 > 程序中全局设置 > 在提交任务的参数中设置 > 在flink-conf配置文件中设置
算子链
    在程序运行的过程中，如果算子之间是one-to-one关系，可以将算子的子任务连在一起形成算子链
    one-to-one
        算子并行度必须相同
        数据没有进行重分区