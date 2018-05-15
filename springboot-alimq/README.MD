### springboot集成阿里云MQ

* 添加Maven依赖

    ```$xslt
        <dependency>
            <groupId>com.aliyun.openservices</groupId>
            <artifactId>ons-client</artifactId>
            <version>1.7.7.Final</version>
        </dependency>
        <dependency>
            <groupId>com.aliyun</groupId>
            <artifactId>aliyun-java-sdk-core</artifactId>
            <version>3.6.0</version>
        </dependency>
    ```

* 配置MQ属性（application.properties）

    在文件application.properties中配置MQ属性
   
* 配置生产者消费者属性及创建Bean

    在文件config/MqConfig中配置MQ属性
    
* 实现生产者生产及消费者消费消息方法类

    在文件mq/MqConsumer、MqProducer中

* 设置项目启动时消费者即启动消费

    在文件config/ListenerConfig、ListenerConfig2
    
* https://github.com/wuqinduo/springboot-aliyunmq

    

    

