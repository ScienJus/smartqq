# Smart QQ Java

基于 Smart QQ（Web QQ） 的 Api 封装，你可以用这个 Api 制作属于自己的 QQ 机器人！

该项目目前（2016年1月）为止还可以正常使用，我也会尽量一直维护这个项目，[Ruby版][ruby]也是如此。

该项目仅提供了最基本的通信协议，你可以在此基础上实现自己的业务逻辑，包括且不限于：

- 拥有 GUI 的 QQ 客户端（Android 或桌面版）
- 自动聊天回复的 QQ 机器人
- 汇总聊天记录并同步在云上
- 通过 QQ 写邮件、发短信、执行远程服务器的命令
- 等等……

注：由于 Smart QQ 不支持收发图片等功能，所以此 Api 也只可以发送文字消息（不包含 @ 命令）。

### Api 列表

如果你想要了解 Web QQ 的通讯协议，并自己实现一个通讯接口。我在博客中详细的记录了抓包获取的请求和对应参数信息。你可以直接点击下面的目录：

[Web QQ协议分析（一）：前言][1]

[Web QQ协议分析（二）：登录][2]

[Web QQ协议分析（三）：收发消息][3]

[Web QQ协议分析（四）：好友相关][4]

[Web QQ协议分析（五）：群和讨论组相关][5]

[Web QQ协议分析（六）：其他][6]

### 联系方式

如果你有什么问题可以在 Issues 中提给我，或是通过邮件联系我：`i@scienjus.com`

[ruby]: https://github.com/ScienJus/qqbot
[1]: http://www.scienjus.com/webqq-analysis-1/
[2]: http://www.scienjus.com/webqq-analysis-1/
[3]: http://www.scienjus.com/webqq-analysis-1/
[4]: http://www.scienjus.com/webqq-analysis-1/
[5]: http://www.scienjus.com/webqq-analysis-1/
[6]: http://www.scienjus.com/webqq-analysis-1/
