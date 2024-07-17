在异步编程中，我们经常会遇到这种情况，发起请求与接收请求在不同的线程中完成的，而需要在发起请求的线程中等待接收请求的结果,这种场景常于Netty相关的编程中

解决这种问题的方法有很多，这里提供一种简单的解决方案:

首先需要实例化一个全局的Async的实例,然后在发送和接收请求的类中分别调用`async`的`await`和`put`方法:
```java

public class MessageCenter {
    // 实例化一个全局的Async对象
    public static final Async<String, String> async = new Async<>();
}

public class Sender {
    public void send(String message) {
        // 生成一个唯一的requestId
        String requestId = UUID.randomUUID().toString();
        // 发送请求
        NettyClient.send(message, requestId);
        // 等待接收请求
        String result = MessageCenter.async.await(requestId, 30 * 1000);
        // 打印结果
        System.out.println(result);
    }
}

public class Receiver {
    public void receive(String message, String requestId) {
        // 返回结果
        MessageCenter.async.put(requestId, result);
    }
}
```

发送请求的代码中，`MessageCenter.async.await` 会阻塞当前线程，直到`MessageCenter.async.put`被调用，返回结果,前提是二者的requestId是一致的。

这是一个完整的异步消息处理的流程，当然，我们注意到在绝大部分的系统中，`Async`的定义是重复的，而且，消息id是全局唯一的，

所以我们提供了一个全局的`Async`实例`GlobalAsync`，这样就不需要每次都实例化一个`Async`对象了。你可以在任何地方使用`GlobalAsync`,上述的代码可以简化为:

发送请求的类:
```java
public class Sender {
    public void send(String message) {
        // 生成一个唯一的requestId
        String requestId = UUID.randomUUID().toString();
        // 发送请求
        NettyClient.send(message, requestId);
        // 等待接收请求
        String result = GlobalAsync.await(requestId, 30 * 1000);
        // 打印结果
        System.out.println(result);
    }
}
public class Receiver {
    public void receive(String message, String requestId) {
        // 返回结果
        GlobalAsync.put(requestId, result);
    }
}
```

详见测试用例: [AsyncTest](../src/test/java/io/github/xiechanglei/lan/lang/async/AsyncTest.java)
> 详见测试用例: [AsyncTest](../src/test/java/io/github/xiechanglei/lan/lang/async/AsyncTest.java)