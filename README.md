# Smart QQ Java

基于 Smart QQ（Web QQ） 的 Api 封装，你可以用这个 Api 制作属于自己的 QQ 机器人！

该项目目前（2016年2月）为止还可以正常使用，我也会尽量一直维护这个项目，[Ruby版][ruby]也是如此。

该项目仅提供了最基本的通信协议，你可以在此基础上实现自己的业务逻辑，包括且不限于：

- 拥有 GUI 的 QQ 客户端（Android 或桌面版）
- 自动聊天回复的 QQ 机器人
- 汇总聊天记录并同步在云上
- 通过 QQ 写邮件、发短信、执行远程服务器的命令
- 等等……

注：由于 Smart QQ 不支持收发图片等功能，所以此 Api 也只可以发送文字消息（不包含 @ 命令）。

### 使用方法

如果你需要将此Api嵌入到别的项目，需要Clone后自行通过Maven打成Jar包，然后引用到你的项目中。

如果你只是想要尝试一下，可以直接Clone本项目并随便写个Main方法运行。

```
public class Application {

    public static void main(String[] args) {
        //创建一个新对象时需要扫描二维码登录，并且传一个处理接收到消息的回调，如果你不需要接收消息，可以传null
        SmartQQClient client = new SmartQQClient(new MessageCallback() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message.getContent());
            }

            @Override
            public void onGroupMessage(GroupMessage message) {
                System.out.println(message.getContent());
            }

            @Override
            public void onDiscussMessage(DiscussMessage message) {
                System.out.println(message.getContent());
            }
        });
        //登录成功后便可以编写你自己的业务逻辑了
        List<Category> categories = client.getFriendListWithCategory();
        for (Category category : categories) {
            System.out.println(category.getName());
            for (Friend friend : category.getFriends()) {
                System.out.println("————" + friend.getNickname());
            }
        }
        //使用后调用close方法关闭，你也可以使用try-with-resource创建该对象并自动关闭
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Api 列表

如果你想要了解 Web QQ 的通讯协议，并自己实现一个通讯接口。我在博客中详细的记录了抓包获取的请求和对应参数信息。你可以直接点击下面的目录：

[Web QQ协议分析（一）：前言][1]

[Web QQ协议分析（二）：登录][2]

[Web QQ协议分析（三）：收发消息][3]

[Web QQ协议分析（四）：好友相关][4]

[Web QQ协议分析（五）：群和讨论组相关][5]

[Web QQ协议分析（六）：其他][6]

### 常见错误

**程序无法控制的错误**

错误码103：这个是由于Smart QQ多点登录，后端校验失败。需要手动进入[官网][8]，检查是否能正常接收消息。如果可以的话点击[设置]->[退出登录]后查看是否恢复正常

**正常流程不应该发生的错误**

错误码100001、1000000：基本是由于参数错误或者Cookie错误所引起的，如果遇到这种情况，请提交Issue反馈

#### 更新日志

2016-2-1: 程序无法接收消息，同时登录[官网][8]后也无法接收消息。大约 15:44 左右恢复正常，程序不需要更新。感谢@WiseClock提供信息！

### 感谢

现在使用[requests][7]进行 Http 请求

### 联系方式

由于 Web QQ 协议变更比较频繁，而我也不可能时时都去测试 Api 的可用性，所以如果您在使用途中发现了问题，欢迎给我提 Issue ，或是通过邮件联系我：`i@scienjus.com`，意见和建议也欢迎。

[ruby]: https://github.com/ScienJus/qqbot
[1]: http://www.scienjus.com/webqq-analysis-1/
[2]: http://www.scienjus.com/webqq-analysis-1/
[3]: http://www.scienjus.com/webqq-analysis-1/
[4]: http://www.scienjus.com/webqq-analysis-1/
[5]: http://www.scienjus.com/webqq-analysis-1/
[6]: http://www.scienjus.com/webqq-analysis-1/
[7]: https://github.com/caoqianli/requests
[8]: http://w.qq.com
