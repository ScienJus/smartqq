# Smart QQ client for Java

Smart QQ（WebQQ）的API，从https://github.com/ScienJus/smartqq 修改而来

该项目目前（2017年1月11日）为止还可以正常使用，我也会尽量一直维护这个项目。

该项目仅提供了最基本的通信协议。

注：由于 Smart QQ 不支持收发图片等功能，所以此 API 也只可以发送文字消息（不包含 @ 命令）。

### 使用方法

如果你需要将此API嵌入到别的项目，可以使用Maven依赖：

将项目克隆到本地，并安装到本地的Maven仓库：

```
git clone https://github.com/Xianguang-Zhou/smartqq-client
cd smartqq-client
mvn source:jar javadoc:jar install -Dmaven.test.skip=true
```

更新：

```
cd smartqq-client
git pull
mvn clean source:jar javadoc:jar install -Dmaven.test.skip=true
```

依赖：

```
<dependency>
    <groupId>com.scienjus</groupId>
    <artifactId>smartqq-client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

如果你只是想要尝试一下，可以直接clone本项目并运行：

```
mvn test -Dtest=com.scienjus.smartqq.test.LoginTest#testLogin
```

### API 列表

如果你想要了解 Web QQ 的通讯协议，并自己实现一个通讯接口。ScienJus在博客中详细的记录了抓包获取的请求和对应参数信息。你可以直接点击下面的目录：

[Web QQ协议分析（一）：前言][1]

[Web QQ协议分析（二）：登录][2]

[Web QQ协议分析（三）：收发消息][3]

[Web QQ协议分析（四）：好友相关][4]

[Web QQ协议分析（五）：群和讨论组相关][5]

[Web QQ协议分析（六）：其他][6]

### 常见错误

**正常流程不应该发生的错误**

错误码100001、1000000：基本是由于参数错误或者Cookie错误所引起的，如果遇到这种情况，请提交Issue反馈

错误码6：如果是在`getGroupInfo`方法中出现，可能是误把`group.id`当成`group.code`作为参数了，这里的参数应该是`code`。

### 感谢

感谢ScienJus，他在博客中详细的记录了 Web QQ 抓包获取的请求和对应参数信息，他开发了项目： https://github.com/ScienJus/smartqq

2016-2-2：修改了Jar运行时保存二维码失败的Bug，感谢@oldjunyi的反馈！

2016-2-1：程序无法接收消息，同时登录官网后也无法接收消息。大约 15:44 左右恢复正常，程序不需要更新。感谢@WiseClock提供信息！

### 联系方式

由于 Smart QQ 协议变更比较频繁，而我也不可能时时都去测试 API 的可用性，所以如果您在使用途中发现了问题，欢迎给我提 Issue ，意见和建议也欢迎。

[1]: http://www.scienjus.com/webqq-analysis-1/
[2]: http://www.scienjus.com/webqq-analysis-2/
[3]: http://www.scienjus.com/webqq-analysis-3/
[4]: http://www.scienjus.com/webqq-analysis-4/
[5]: http://www.scienjus.com/webqq-analysis-5/
[6]: http://www.scienjus.com/webqq-analysis-6/
[7]: http://w.qq.com
