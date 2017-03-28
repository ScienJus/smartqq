package com.scienjus.smartqqkotlin;

import com.scienjus.smartqqkotlin.client.SmartQqClient;
import com.scienjus.smartqqkotlin.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.cookie.Cookie;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 对功能的手动测试。
 */

@SuppressWarnings("StatementWithEmptyBody")
public class TestApplication {
    private static final SmartQqClient client = new SmartQqClient();

    private static volatile boolean done = false;

    @BeforeClass
    public static void qrCodeLogin() {
        loginLoop:
        while (true) {
            SmartQqClient.LoginResult result =
                    client.start(bytes -> {
                        File qrcode = new File("qrcode.png");
                        try {
                            FileUtils.writeByteArrayToFile(qrcode, bytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Assert.fail();
                        }
                        System.out.println("二维码已保存");
                        Desktop desk = Desktop.getDesktop();
                        try {
                            desk.open(qrcode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            switch (result) {
                case SUCCEEDED:
                    break loginLoop;
                case QR_CODE_EXPIRED:
                    continue loginLoop;
                case FAILED:
                    Assert.fail();
            }
        }
    }

    @Test
    public void cookieDumpingAndRestoring() {
        Assert.assertTrue(client.getStatus() == SmartQqClient.ClientStatus.ACTIVE);
        List<Cookie> cookies = client.dumpCookies();
        client.close();
        Assert.assertTrue(client.start(cookies) == SmartQqClient.LoginResult.SUCCEEDED);
    }

    @Test
    public void fetchingLists() {
        Assert.assertTrue(client.getStatus() == SmartQqClient.ClientStatus.ACTIVE);
        try {
            System.out.println("好友数: " + client.getFriends().size());
            for (Friend friend : client.getFriends()) {
                System.out.println(friend.getNickname());
            }
            Thread.sleep(1);
            System.out.println("分组数: " + client.getFriendCategories().size());
            for (FriendCategory category : client.getFriendCategories()) {
                System.out.println(category.getName());
            }
            Thread.sleep(1);
            System.out.println("群数: " + client.getGroups().size());
            for (Group group : client.getGroups()) {
                System.out.println(group.getName());
            }
            Thread.sleep(1);
            System.out.println("讨论组数: " + client.getDiscussions().size());
            for (Discussion discussion : client.getDiscussions()) {
                System.out.println(discussion.getName());
            }
            Thread.sleep(1);
            System.out.println("最近会话数: " + client.getChatHistories().size());
            for (ChatHistory history : client.getChatHistories()) {
                System.out.println(history.getTarget().getName());
            }
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Assert.fail();
        }
    }

    @Test
    public void friendMessage() {
        Assert.assertTrue(client.getStatus() == SmartQqClient.ClientStatus.ACTIVE);
        done = false;
        client.getFriendMessageReceived().plusAssign(msg -> {
            if (Objects.equals(msg.getContent(), "测试")) {
                msg.reply("成功");
                done = true;
            }
        });
        System.out.println("请向此账号发送一条私聊消息“测试”以完成测试");
        while (!done) {
            // 等待测试完成
        }
    }

    @Test
    public void groupMessage() {
        Assert.assertTrue(client.getStatus() == SmartQqClient.ClientStatus.ACTIVE);
        done = false;
        client.getGroupMessageReceived().plusAssign(msg -> {
            if (Objects.equals(msg.getContent(), "测试")) {
                msg.reply("成功");
                done = true;
            }
        });
        System.out.println("请向此账号发送一条群消息“测试”以完成测试");
        while (!done) {
            // 等待测试完成
        }
    }

    @Test
    public void discussionMessage() {
        Assert.assertTrue(client.getStatus() == SmartQqClient.ClientStatus.ACTIVE);
        done = false;
        client.getDiscussionMessageReceived().plusAssign(msg -> {
            if (Objects.equals(msg.getContent(), "测试")) {
                msg.reply("成功");
                done = true;
            }
        });
        System.out.println("请向此账号发送一条讨论组消息“测试”以完成测试");
        while (!done) {
            // 等待测试完成
        }
    }
}
