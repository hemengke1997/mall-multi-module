# 仿Mall后端项目

> 记录学习过程

原项目地址：https://github.com/macrozheng/mall

## 项目搭建

这是一个多module的项目，这样设计是为了解耦每个模块之间的功能，使得结构和功能更加清晰

### 开发服务环境搭建

mysql, redis, minio等服务使用Docker启动，便于开发和迁移
docker启动服务时挂载数据卷到本地

#### mysql

复制mysql的配置

docker cp 容器名称:/etc/my.cnf ~/Documents/docker_volume/mall/mysql/conf/my.cnf
docker cp 容器名称:/etc/mysql/conf.d ~/Documents/docker_volume/mall/mysql/conf/conf.d

```shell
docker run -p 3306:3306 -v ~/Documents/docker_volume/mall/mysql:/var/lib/mysql\ 
--name mysql-mall -e MYSQL_ROOT_PASSWORD=123456 -d mysql
```

#### redis

```shell
docker run -p 6379:6379 -v ~/Documents/docker_volume/mall/redis:/data redis:latest\
-d --name redis-mall redis-server --appendonly yes
```

#### minio

```shell
docker run -p 9000:9000 -p 9001:9001 --name minio-mall \
-v ~/Documents/docker_volume/mall/minio:/data \
-e MINIO_ROOT_USER=minioadmin \
-e MINIO_ROOT_PASSWORD=minioadmin \
-d minio/minio server /data --console-address ":9001"
```

minio
http://localhost:9001/

#### elasticsearch

```shell
docker run -itd -p 9200:9200 -p 9300:9300 \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms64m -Xmx128m" \
-v ~/Documents/docker_volume/mall/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v ~/Documents/docker_volume/mall/elasticsearch/data:/usr/share/elasticsearch/data \
-v ~/Documents/docker_volume/mall/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
--name es-mall --net elastic elasticsearch:8.8.0
```

#### rabbitmq

```shell
docker run --privileged=true \
-d -p 5672:5672 -p 15672:15672 \
--name rabbitmq-mall \
-v ~/Documents/docker_volume/mall/rabbitmq/data:/var/lib/rabbitmq \
-v ~/Documents/docker_volume/mall/rabbitmq/conf:/etc/rabbitmq \
-v ~/Documents/docker_volume/mall/rabbitmq/log:/var/log/rabbitmq \
--restart=always --hostname=rabbitmqhost -e RABBITMQ_DEFAULT_VHOST=my_vhost \ 
-e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin \
rabbitmq:3.9-management
```

rabbitmq
http://localhost:15672/

### 项目结构

- mall-common：通用模块，包含通用工具类、通用配置、通用异常类
- mall-admin: 后台管理模块，包含后台管理系统接口
- mall-security: SpringSecurity模块，包含SpringSecurity的配置和相关业务类
- mall-portal: 前台商城模块，包含前台商城系统接口

// mongodb常用注解

* `@Id` 主键，不可重复，自带索引，可以在定义的列名上标注，需要自己生成并维护不重复的约束。
* 如果自己不设置@Id主键，mongo会自动生成一个唯一主键，并且插入时效率远高于自己设置主键。
* 在实际业务中不建议自己设置主键，应交给mongo自己生成，自己可以设置一个业务id，如int型字段，用自己设置的业务id来维护相关联的表
* `@Document`  // 标注在实体类上，类似于entity注解，标明由mongo来维护该表
* `@Indexed`   //声明该字段需要加索引，加索引后以该字段为条件检索将大大提高速度
* `@CompoundIndex`    //复合索引，加复合索引后通过复合索引字段查询将大大提高速度。
* `@Field`     //代表一个字段，可以不加，不加的话默认以参数名为列名
* `@Transient` //被该注解标注的，将不会被录入到数据库中。只作为普通的javaBean属性。
* `@DBRef`    //关联另一个document对象
