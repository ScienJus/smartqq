package com.scienjus.smartqq.listener;

import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

/**
 * adapter of SmartqqListener
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2016/08/21.
 */
public class SmartqqAdapter implements SmartqqListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.scienjus.smartqq.listener.SmartqqListener#onMessage(com.scienjus.
	 * smartqq.model.Message)
	 */
	@Override
	public void onMessage(Message message) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.scienjus.smartqq.listener.SmartqqListener#onGroupMessage(com.scienjus
	 * .smartqq.model.GroupMessage)
	 */
	@Override
	public void onGroupMessage(GroupMessage message) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scienjus.smartqq.listener.SmartqqListener#onDiscussMessage(com.
	 * scienjus.smartqq.model.DiscussMessage)
	 */
	@Override
	public void onDiscussMessage(DiscussMessage message) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scienjus.smartqq.listener.SmartqqListener#onException(java.lang.
	 * Throwable, com.scienjus.smartqq.listener.ExceptionThreadType)
	 */
	@Override
	public void onException(Throwable exception, ExceptionThreadType exceptionThreadType) {
	}

}
