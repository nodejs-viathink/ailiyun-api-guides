### springboot-mybatis

* 先配置`application.properties`
* 再配置 `mybatis-config.xml`
* 定义`UserEntity`类 => 配置`UserMapper.xml` => `UserMapper.java` => `UserService` = >`UserController`
* 一般参数传参时定义一个参数类param来封装要传的参数,
* 返回数据定义一个结果类result来封装要返回的数据

### Pagehelper分页插件

* 添加Maven依赖

  ```$xslt
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper-spring-boot-starter</artifactId>
        <version>1.2.5</version>
    </dependency>
  ```
* pagehelper分页插件配置

  ```$xslt
   pagehelper.helperDialect=mysql
   pagehelper.reasonable=true
   pagehelper.supportMethodsArguments=true
   pagehelper.params=count=countSql
  ```

  https://www.jianshu.com/p/6752c3d2c19d
  https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
  https://github.com/pagehelper/pagehelper-spring-boot

    