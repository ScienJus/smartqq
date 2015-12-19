package com.scienjus.smartqq.callback;

import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

/**
 * 收到消息的回调
 * @author ScienJus
 * @date 2015/12/18.
 */
public interface MessageCallback {

    void onMessage(Message message);

    void onGroupMessage(GroupMessage message);
}
