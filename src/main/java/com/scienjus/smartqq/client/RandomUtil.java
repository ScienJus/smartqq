package com.scienjus.smartqq.client;

import java.util.Random;

/**
 * random util
 *
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2016/11/15.
 */
public class RandomUtil {
	private static final Random random = new Random();

	public static String numberString(int length) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(10);
			stringBuilder.append(number);
		}
		return stringBuilder.toString();
	}
}
