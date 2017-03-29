# Smart QQ Kotlin

### 这是啥？

Smart QQ Java的进行了大量激进更改的Kotlin版本（Java也能用）。

### Kotlin是啥？

由[JetBrains](https://jetbrains.com)开发的一个JVM语言。更多信息请参见[官方网站](https://kotlinlang.org)。

### Java明明很好用，为什么要用Kotlin？

+ ~~爽~~
+ ~~Java反人类~~
+ Java的设计太过低糖，代码会变得过于冗长

### 我在用IntelliJ IDEA/Eclipse/Maven/Ant/Gradle/etc.，可以编译这玩意儿吗？

出门右转[官方网站](https://kotlinlang.org)

### Java项目用这个会导致代码变得诡异吗？

此项目面向Java写成（在牺牲了一部分Kotlin语法糖的前提下），应该不会……

### 支持Java 7吗？

不知道，大概不支持

### Kotlin稳定吗？

是

### 这个分支现在稳定吗？

不

### 这个分支除了语言和master分支有什么区别？
1. （激进更改）易懂、一致而没有语法错误（但愿）的命名
2. （激进更改）去掉了已知无用或过时的数据条目
3. （激进更改）采用自己实现的类C#的事件系统
4. （激进更改）自带数据缓存
5. （激进更改）压缩了各种莫名其妙的代码段，整理了杂乱的内部结构~~并引入了更多Bug~~
6. 数据类互相引用
7. 新增导入导出cookie功能，在会话失效前免去重新登录的麻烦
8. （意义不明）换了下UA标头

### 有示例程序吗？
[TestApplication.java](src/test/java/com/scienjus/smartqqkotlin/TestApplication.java)