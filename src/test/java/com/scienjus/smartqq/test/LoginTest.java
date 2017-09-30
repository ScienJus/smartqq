package com.scienjus.smartqq.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import junit.framework.TestCase;

/**
 * @author ScienJus
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2015/12/18.
 */
public class LoginTest extends TestCase {
	private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

	@Test
	public void testLogin() throws Exception {
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
		client.start();
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
		} finally {
			// 使用后调用close或closeNow方法关闭，你也可以使用try-with-resource创建该对象并自动关闭
			client.closeNow();
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

	/**
	 * 本地存储二维码图片
	 * 
	 * @param qrCodeImageBytes
	 * @throws IOException
	 */
	private static void saveQrCodeImage(byte[] qrCodeImageBytes) throws IOException {
		try {
			File imageFile = new File("qrcode.png");
			try (FileOutputStream fos = new FileOutputStream(imageFile)) {
				fos.write(qrCodeImageBytes);
			}
			String filePath = imageFile.getCanonicalPath();
			System.out.println("二维码已保存在 " + filePath + " 文件中，请打开手机QQ并扫描二维码");
		} catch (IOException e) {
			logger.error("二维码保存失败", e);
			throw e;
		}
	}
}
