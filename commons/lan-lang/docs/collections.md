collections包下的类主要包含了一些数组，集合相关的工具类

#### ArrayHelper 数组工具类

主要提供了以下方法:
- `concat` 合并数组
- `distinct` 数组元素去重

> 测试用例:
> - [ArrayHelperTest.java](../src/test/java/io/github/xiechanglei/lan/lang/collections/ArrayHelperTest.java)

#### ListFilterHandler 集合过滤处理器
当我们有两个集合，A集合和B集合，期望可以获取以下结果
- 找出所有在B集合中存在，但是在A集合中不存在的元素，即B集合相对于A集合的差集，通常可能是在增量更新下需要被新增的元素
- 找出所有在A集合中存在，但是在B集合中不存在的元素，即A集合相对于B集合的差集，通常可能是在增量更新下需要被删除的元素
- 找出所有在A集合和B集合中都存在的元素，即A集合和B集合的交集，通常可能是需要被保留的元素，并且需要做一些判断逻辑，判断元素是否需要被更新

为了满足以上需求，我么会封装了一个工具类`ListFilterHandler` 以及他的构造器`ListFilterHandlerBuilder`，使用方法如下:
```java
public class ListFilterHandlerTest {

    @Test
    public void test() {
        List<String> a = Arrays.asList("a", "b", "c", "d");
        List<String> b = Arrays.asList("b", "c", "d", "e");
        ListFilterHandler res = ListFilterHandlerBuilder.from(a).filter(b);
        System.out.println(res.getAddList()); // 新增的数据 [e]
        System.out.println(res.getRemoveList()); // 删除的数据 [a]
        System.out.println(res.getUpdateList()); // 需要更新的数据 []
    }
}
```
上述是一个简单的例子，我们定义了两个集合a和b，然后通过`ListFilterHandlerBuilder.from(a).filter(b)`获取到了一个`ListFilterHandler`对象，然后通过`getAddList`、`getRemoveList`、`getUpdateList`获取到了需要新增、删除、更新的数据。

这里我们比较两个元素是否是相等的，默认是比较的元素的本身，如果需要自定义比较逻辑，可以通过`ListFilterHandlerBuilder.keyCompareFunction` 来定义比较逻辑，例如:

```java
public class ListFilterHandlerTest {
    @Data
    @AllArgsConstructor
    public static class Animal {
        private String name;
        private String age;
    }

    @Test
    public void test() {
        List<Animal> a = Arrays.asList(new Animal("a", "1"), new Animal("b", "2"), new Animal("c", "3"), new Animal("d", "4"));
        List<Animal> b = Arrays.asList(new Animal("b", "2"), new Animal("c", "3"), new Animal("d", "4"), new Animal("e", "5"));
        ListFilterHandler res = ListFilterHandlerBuilder.from(a).filter(b).keyCompareFunction(Animal::getName);
        System.out.println(res.getAddList()); // 新增的数据 [Animal(name=e, age=5)]
        System.out.println(res.getRemoveList()); // 删除的数据 [Animal(name=a, age=1)]
        System.out.println(res.getUpdateList()); // 需要更新的数据 []
    }
}
```

另外如果你仔细阅读了上述的代码的话，你会发现两次获取的updateList都是空的，这是因为我们没有定义一个可以判断是否更新的逻辑，默认情况下，工具会认为所有保留的元素都是不需要更新的。

如果需要定义更新逻辑，可以通过`ListFilterHandlerBuilder.updateCompareFunction`来定义，例如:

```java
public class ListFilterHandlerTest {
    @Data
    @AllArgsConstructor
    public static class Animal {
        private String name;
        private String age;
    }

    @Test
    public void test() {
        List<Animal> a = Arrays.asList(new Animal("a", "1"), new Animal("b", "2"), new Animal("c", "3"), new Animal("d", "4"));
        List<Animal> b = Arrays.asList(new Animal("b", "2"), new Animal("c", "3"), new Animal("d", "7"), new Animal("e", "5"));
        ListFilterHandler res = ListFilterHandlerBuilder.from(a).filter(b)
                .keyCompareFunction(Animal::getName)
                .updateCompareFunction((a1, b1) -> {
                    if(!a1.getAge().equals(b1.getAge())){
                        a1.setAge(b1.getAge());
                        return a1;
                    }
                    return null;
                });
        System.out.println(res.getAddList()); // 新增的数据 [Animal(name=e, age=5)]
        System.out.println(res.getRemoveList()); // 删除的数据 [Animal(name=a, age=1)]
        System.out.println(res.getUpdateList()); // 需要更新的数据 [ Animal(name=d, age=7)]
    }
}
```

需要注意的是：
- addList中的元素来源于b集合
- removeList中的元素来源于a集合
- remainList中的元素来源于b集合，因为updateList中的元素是每次获取的时候实时计算的，计算需要将remainList中的新的元素于a集合中的元素进行比较，如果需要更新，则将更新后的元素放入updateList中
- updateList中的元素来源于`updateCompareFunction`的返回值

> 测试用例:
> - [ListFilterHandlerTest.java](../src/test/java/io/github/xiechanglei/lan/lang/collections/ListFilterHandlerTest.java)