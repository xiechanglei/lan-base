byte包下的是字节相关的工具类, 包括字节转换, 字节数组转换等等.

#### ByteHelper 字节操作工具类

主要提供了以下方法:
 - `fromHex` 将16进制字符串转换为字节
 - `toHex` 将字节转换为16进制字符串

> 测试用例:
> - [ByteHelperTest.java](../src/test/java/io/github/xiechanglei/lan/lang/bytecode/ByteHelperTest.java)

#### ByteArrayHelper 字节数组操作工具类

主要提供了以下方法:
- `merge` 合并字节数组
- `toList` 将字节数组转换为List
- `fromNumbers` 将多个数字转换为字节数组
- `fromHex` 将16进制字符串转换为字节数组
- `toHex` 将字节数组转换为16进制字符串
- `isSame` 判断两个字节数组是否相等
- `indexOf` 查找字节数组中的子字
- `lastIndexOf` 查找字节数组中的子字
- `toShort` 将字节数组转换为short
- `toUnsignedShort` 将字节数组转换为无符号short
- `fromShort` 将short转换为字节数组
- `fromUnsignedShort` 将无符号short转换为字节数组
- `toInt` 将字节数组转换为int
- `toUnsignedInt` 将字节数组转换为无符号int
- `fromInt` 将int转换为字节数组
- `fromUnsignedInt` 将无符号int转换为字节数组
- `toLong` 将字节数组转换为long
- `fromLong` 将long转换为字节数组
- `toDouble` 将字节数组转换为double
- `fromDouble` 将double转换为字节数组
- `toFloat` 将字节数组转换为float
- `fromFloat` 将float转换为字节数组