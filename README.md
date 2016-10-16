# Smart QQ client for Java

Smart QQ（WebQQ）的API，从https://github.com/ScienJus/smartqq 修改而来

该项目目前（2016年8月26日）为止还可以正常使用，我也会尽量一直维护这个项目。

该项目仅提供了最基本的通信协议，你可以在此基础上实现自己的业务逻辑，包括且不限于：

- 拥有 GUI 的 QQ 客户端（Android 或桌面版）
- 自动聊天回复的 QQ 机器人
- 汇总聊天记录并同步在云上
- 通过 QQ 写邮件、发短信、执行远程服务器的命令
- 等等……

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

如果你只是想要尝试一下，可以直接clone本项目并随便写个Main方法运行。

```
public class Application {
	
	/**
	 * 本地存储二维码图片
	 * 
	 * @param qrCodeImageBytes
	 */
	private static void saveQrCodeImage(byte[] qrCodeImageBytes) {
		try {
			File imageFile = new File("qrcode.png");
			try (FileOutputStream fos = new FileOutputStream(imageFile)) {
				fos.write(qrCodeImageBytes);
			}
			String filePath = imageFile.getCanonicalPath();
			System.out.println("二维码已保存在 " + filePath + " 文件中，请打开手机QQ并扫描二维码");
		} catch (IOException e) {
			System.out.println("二维码保存失败：");
			e.printStackTrace();
		}
	}

	private static void println(List<MessageContentElement> messageContentElements) {
		for (MessageContentElement contentElement : messageContentElements) {
			if (contentElement instanceof Text) {
				System.out.print(((Text) contentElement).getString());
			} else if (contentElement instanceof Face) {
				System.out.print(String.format("[表情(%d)]", ((Face) contentElement).getCode()));
			} else {
				System.out.print("[未知的消息内容元素]");
			}
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		// 创建一个新客户端时需要传一个处理接收到消息的监听器
		@SuppressWarnings("resource")
		SmartQQClient client = new SmartQQClient(new SmartqqListener() {
			@Override
			public void onMessage(Message message) {
				println(message.getContentElements());
			}

			@Override
			public void onGroupMessage(GroupMessage message) {
				println(message.getContentElements());
			}

			@Override
			public void onDiscussMessage(DiscussMessage message) {
				println(message.getContentElements());
			}

			@Override
			public void onException(Throwable exception, ExceptionThreadType exceptionThreadType) {
			}
		});
		try {
			// 扫描二维码登录
			do {
				byte[] qrCodeImageBytes = client.getQRCode();
				saveQrCodeImage(qrCodeImageBytes);
			} while (client.login());
			
			// 登录成功后便可以编写你自己的业务逻辑了
			List<Category> categories = client.getFriendListWithCategory();
			for (Category category : categories) {
				System.out.println(category.getName());
				for (Friend friend : category.getFriends()) {
					System.out.println("————" + friend.getNickname());
				}
			}
			
			{
				// 获取自己的头像并保存在“./tmp/face/self.jpg”
				byte[] faceImageBytes = client.getUserFace(client.getSelfUserId());
				File faceImageFile = new File("tmp/face/self.jpg");
				faceImageFile.getParentFile().mkdirs();
				try (FileOutputStream fos = new FileOutputStream(faceImageFile)) {
					fos.write(faceImageBytes);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 使用后调用close或closeNow方法关闭，你也可以使用try-with-resource创建该对象并自动关闭
			client.closeNow();
		}
	}
}
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

**程序无法控制的错误**

错误码103：这个是由于Smart QQ多点登录，后端校验失败。需要手动进入[官网][7]，检查是否能正常接收消息。如果可以的话点击[设置]->[退出登录]后查看是否恢复正常

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
