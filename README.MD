![](https://img.shields.io/badge/license-Apache2.0-blue) ![](https://img.shields.io/badge/version-3.2.8.3-green)
# Lan base
## 项目介绍
一个基于springboot的快速开发框架，提供了一些常用的工具类和starter模块.

## 项目使用

#### 1.单独引入模块
```xml
<dependency>
    <groupId>io.github.xiechanglei</groupId>
    <artifactId>web-spring-boot-starter</artifactId>
    <version>3.2.8.3</version>
</dependency>
```
#### 2.作为父项目引入
添加parent
```xml
<prent>
    <groupId>io.github.xiechanglei</groupId>
    <artifactId>lan-base</artifactId>
    <version>3.2.8.3</version>
</prent>
```

按需添加依赖
```xml
<dependencys>
    <dependency>
        <groupId>io.github.xiechanglei</groupId>
        <artifactId>web-spring-boot-starter</artifactId>
    </dependency>
</dependencys>
```


## 模块介绍

**commons 模块**
- [x] [lan-beans](./commons/lan-beans/README.md) 通用bean模块
- [x] [lan-digest](./commons/lan-digest/README.md) 加密算法模块
- [x] [lan-http](./commons/lan-http/README.md) http请求模块
- [x] [lan-jpa-beans](./commons/lan-jpa-beans/README.md) jpa通用bean模块
- [x] [lan-json](./commons/lan-json/README.md) json工具模块
- [x] [lan-lang](./commons/lan-lang/README.md) 通用工具模块
- [x] [lan-net](./commons/lan-net/README.md) 网络工具模块
- [x] [lan-nginx](./commons/lan-nginx/readme.md) nginx配置生成模块

**starter 模块**
- [x] [eureka-spring-boot-starter](starters/eureka-spring-boot-starter/README.md) eureka模块
- [x] [web-spring-boot-starter](starters/web-spring-boot-starter/README.md) web模块
- [x] [jpa-spring-boot-starter](starters/jpa-spring-boot-starter/README.md) jpa模块
- [x] [rbac-spring-boot-starter](starters/rbac-spring-boot-starter/README.md) rbac模块

**plugin 模块**
- [x] [plugin-version](./plugins/plugin-version/README.md) 版本号生成插件


## todo
1. json 模块合并到http模块中，因为通常json工具是为了http请求而存在的
2. 短信 邮件 starter
3. 文件服务器 starter
4. 