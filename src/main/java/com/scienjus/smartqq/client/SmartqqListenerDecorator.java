package com.scienjus.smartqq.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scienjus.smartqq.listener.ExceptionThreadType;
import com.scienjus.smartqq.listener.SmartqqListener;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

/**
 * decorator of SmartqqListener
 *
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2016/08/21.
 */
class SmartqqListenerDecorator implements SmartqqListener {

	private static final Logger logger = LoggerFactory.getLogger(SmartqqListenerDecorator.class);

	private final SmartqqListener decoratedListener;

	public SmartqqListenerDecorator(SmartqqListener decoratedListener) {
		this.decoratedListener = decoratedListener;
	}

	@Override
	public void onMessage(Message message) {
		try {
			decoratedListener.onMessage(message);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void onGroupMessage(GroupMessage message) {
		try {
			decoratedListener.onGroupMessage(message);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void onDiscussMessage(DiscussMessage message) {
		try {
			decoratedListener.onDiscussMessage(message);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void onException(Throwable exception, ExceptionThreadType exceptionThreadType) {
		try {
			decoratedListener.onException(exception, exceptionThreadType);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

}
