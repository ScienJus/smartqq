package com.scienjus.smartqq.callback;

import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

/**
 * 收到消息的回调
 * @author ScienJus
 * @date 2015/12/18.
 */
public interface MessageCallback {

    /**
     * 收到私聊消息后的回调
     * @param client
     * @param message
     */
    void onMessage(SmartQQClient client, Message message);

    /**
     * 收到群消息后的回调
     * @param client
     * @param message
     */
    void onGroupMessage(SmartQQClient client, GroupMessage message);

    /**
     * 收到讨论组消息后的回调
     * @param client
     * @param message
     */
    void onDiscussMessage(SmartQQClient client, DiscussMessage message);
}
