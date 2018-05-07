# springboot 集成 elasticsearch

## 项目搭建

* File>new>project>Spring INitializr>填写项目名称>选择DevTools、Web、NoSQL/elasticsearch模块

* Maven 依赖

    idea 创建项目时若勾选elasticsearch模块默认会配置
    ```$xslt
    <parent>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-parent</artifactId>
          <version>1.4.1.RELEASE</version>
          <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
     </dependency>
    
    ```
 
* 配置文件

    在application.properties中添加elasticsearch配置
    ```$xslt
    # elasticsearch  
    spring.data.elasticsearch.cluster-name=elasticsearch  #节点名字，默认elasticsearch  
    spring.data.elasticsearch.cluster-nodes=127.0.0.1:9300,119.29.38.169:9300  #节点地址，多个节点用逗号隔开  
    #spring.data.elasticsearch.local=false  
    spring.data.elasticsearch.repositories.enable=true #开启 Elasticsearch 仓库。(默认值:true。)
    ```
## 项目实现

1. 创建实体类

    domain/Employee.java 文件。
    ```$xslt
    // indexName ：索引名字（对应mysql的数据库名字）  
    //type:类型（对应mysql的表名）
    @Document(indexName = "megacorp",type = "employee", shards = 1,replicas = 0, refreshInterval = "-1")
    ```
2. 编写dao
   
   dao/EmployeeRepository.java 文件

3. 编写controller,实现简单的增删改查(此处没写service层)

    contorller/ElasticSearchController.java
