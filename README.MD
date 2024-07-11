![](https://img.shields.io/badge/license-Apache2.0-blue) ![](https://img.shields.io/badge/version-2.7.18.3-green)
# Lan base
## 项目介绍
java 开发脚手架项目项目主要用于封装一些常用的工具以及功能，方便开发人员快速开发项目。

## 项目使用

#### 1.单独引入模块
```xml
<dependency>
    <groupId>io.github.xiechanglei</groupId>
    <artifactId>web-spring-boot-starter</artifactId>
    <version>2.7.18.3</version>
</dependency>
```
#### 2.作为父项目引入
添加parent
```xml
<prent>
    <groupId>io.github.xiechanglei</groupId>
    <artifactId>lan-base</artifactId>
    <version>2.7.18.3</version>
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
- [x] [lan-utils](./commons/lan-utils/README.md) 通用工具模块

**starter 模块**
- [x] [web-spring-boot-starter](starters/web-spring-boot-starter/README.md) web模块
- [x] [jpa-spring-boot-starter](starters/jpa-spring-boot-starter/README.md) jpa模块
- [x] [rbac-spring-boot-starter](starters/rbac-spring-boot-starter/README.md) rbac模块

**plugin 模块**
- [x] [plugin-version](./plugins/plugin-version/README.md) 版本号生成插件


## todo
1. json 模块合并到http模块中，因为通称json工具是为了http请求而存在的
2. lan-utils 重命名为 lan-lang 或者 lan-common，表示通用工具