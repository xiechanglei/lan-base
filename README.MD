![](https://img.shields.io/badge/license-Apache2.0-blue) ![](https://img.shields.io/badge/version-2.7.18.1-green)
# Lan base
## 项目介绍
java 开发脚手架项目项目主要用于封装一些常用的工具以及功能，方便开发人员快速开发项目。

该项目以女友的名字命名，希望每个开发者都能找到自己的女朋友。

## 项目使用

#### 1.单独引入模块
```xml
<dependency>
    <groupId>io.github.xiechanglei</groupId>
    <artifactId>web-spring-boot-starter</artifactId>
    <version>2.7.18.1</version>
</dependency>
```
#### 2.作为父项目引入
添加parent
```xml
<prent>
    <groupId>io.github.xiechanglei</groupId>
    <artifactId>lan-base</artifactId>
    <version>2.7.18.1</version>
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