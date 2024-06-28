![](https://img.shields.io/badge/license-Apache2.0-blue) ![](https://img.shields.io/badge/version-2.7.18.2-green)
# lan-base-beans

常规的一些bean封装

引入依赖
```xml

<dependency>
    <groupId>io.github.xiechanglei</groupId>
    <artifactId>lan-base-beans</artifactId>
    <version>2.7.18.2</version>
</dependency>
```

### AsyncLock 异步锁
提供了一个异步锁，可以在异步请求中，等待锁的释放。
Examples:
```java
public class Test {
    public static void main(String[] args) {
        AsyncLock asyncLock = new AsyncLock<>();
        new Thread(() -> {
            Thread.sleep(3000);
            asyncLock.unlock();
        }).start();
        asyncLock.lock(1000 * 10);
        System.out.println("end");
    }
}
```

### Async 异步请求返回封装 
Examples:
```java
public class Test {
    public static void main(String args[]) {
        Async<String, String> async = new Async<>();
        String requestId = "testkey"; // 这里是唯一的requestId
        //模拟异步请求
        new Thread(() -> async.put(requestId, "hello world")).start();
        String result = async.await(requestId, 30 * 1000);
        System.out.println(result);
    }
}
```
当你可以做到requestId全局唯一的时候，可以使用默认提供的`GlobalAsync`,

Examples:
```java
ublic class Test {
    public static void main(String args[]) {
        String requestId = "testkey"; // 这里是唯一的requestId
        //模拟异步请求
        new Thread(() -> GlobalAsync.put(requestId, "hello world")).start();
        String result = GlobalAsync.await(requestId, 30 * 1000);
        System.out.println(result);
    }
}
```