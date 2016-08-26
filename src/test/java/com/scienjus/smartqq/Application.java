package com.scienjus.smartqq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.listener.ExceptionThreadType;
import com.scienjus.smartqq.listener.SmartqqListener;
import com.scienjus.smartqq.model.Category;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.Face;
import com.scienjus.smartqq.model.Friend;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;
import com.scienjus.smartqq.model.MessageContentElement;
import com.scienjus.smartqq.model.Text;

/**
 * @author ScienJus
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2015/12/18.
 */
public class Application {

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
		// 创建一个新对象时需要扫描二维码登录，并且传一个处理接收到消息的监听器
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
				System.out.println(message.getContent());
			}

			@Override
			public void onQrCodeImage(byte[] imageBytes) {
				// 本地存储二维码图片
				try {
					File imageFile = new File("qrcode.png");
					try (FileOutputStream fos = new FileOutputStream(imageFile)) {
						fos.write(imageBytes);
					}
					String filePath = imageFile.getCanonicalPath();
					System.out.println("二维码已保存在 " + filePath + " 文件中，请打开手机QQ并扫描二维码");
				} catch (IOException e) {
					System.out.println("二维码保存失败：");
					e.printStackTrace();
				}
			}

			@Override
			public void onException(Throwable exception, ExceptionThreadType exceptionThreadType) {
			}
		});
		try {
			// 登录成功后便可以编写你自己的业务逻辑了
			List<Category> categories = client.getFriendListWithCategory();
			for (Category category : categories) {
				System.out.println(category.getName());
				for (Friend friend : category.getFriends()) {
					System.out.println("————" + friend.getNickname());
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
