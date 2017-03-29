package com.scienjus.smartqqkotlin;

import com.scienjus.smartqqkotlin.client.SmartQqClient;
import com.scienjus.smartqqkotlin.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.cookie.Cookie;
import org.junit.*;

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

    @Before
    public void assumeActive() {
        Assume.assumeTrue(client.getStatus() == SmartQqClient.ClientStatus.ACTIVE);
    }

    @Test
    public void cookieDumpingAndRestoring() {
        List<Cookie> cookies = client.dumpCookies();
        client.close();
        Assert.assertTrue(client.start(cookies) == SmartQqClient.LoginResult.SUCCEEDED);
    }

    @Test
    public void fetchingLists() {
        try {
            System.out.println("好友数: " + client.getFriends().size());
            for (Friend friend : client.getFriends()) {
                System.out.println(friend);
            }
            Thread.sleep(500);
            System.out.println("分组数: " + client.getFriendCategories().size());
            for (FriendCategory category : client.getFriendCategories()) {
                System.out.println(category);
            }
            Thread.sleep(500);
            System.out.println("群数: " + client.getGroups().size());
            for (Group group : client.getGroups()) {
                System.out.println(group);
            }
            Thread.sleep(500);
            System.out.println("讨论组数: " + client.getDiscussions().size());
            for (Discussion discussion : client.getDiscussions()) {
                System.out.println(discussion);
            }
            Thread.sleep(500);
            System.out.println("最近会话数: " + client.getChatHistories().size());
            for (ChatHistory history : client.getChatHistories()) {
                try {
                    System.out.println(history.getTarget().getName());
                } catch (Exception ex) {
                    System.out.println("[出错了]");
                }
            }
        } catch (InterruptedException ex) {
            Assert.fail();
        }
    }

    @Test
    public void friendMessage() {
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

    @Test
    public void myInfo() {
        System.out.println(String.join(", ",
                Long.toString(client.getId()),
                client.getNickname(),
                client.getBio(),
                client.getGender(),
                client.getPhone(),
                client.getCellphone(),
                client.getEmail(),
                client.getHomepage(),
                client.getBirthday().toString(),
                client.getSchool(),
                client.getJob(),
                Integer.toString(client.getBloodType()),
                client.getCountry(),
                client.getProvince(),
                client.getCity(),
                client.getPersonal(),
                Integer.toString(client.getShengxiao()),
                client.getAccount(),
                Integer.toString(client.getVipInfo()),
                Long.toString(client.getQqNumber())));
    }

    @Test
    public void friendInfo() {
        List<Friend> friends = client.getFriends();
        Assume.assumeFalse(friends.isEmpty());
        Friend friend = friends.get(0);
        System.out.println(String.join(", ",
                Long.toString(friend.getId()),
                friend.getNickname(),
                friend.getBio(),
                friend.getGender(),
                friend.getPhone(),
                friend.getCellphone(),
                friend.getEmail(),
                friend.getHomepage(),
                friend.getBirthday().toString(),
                friend.getSchool(),
                friend.getJob(),
                Integer.toString(friend.getBloodType()),
                friend.getCountry(),
                friend.getProvince(),
                friend.getCity(),
                friend.getPersonal(),
                Integer.toString(friend.getShengxiao()),
                friend.getAccount(),
                Integer.toString(friend.getVipInfo()),
                Long.toString(friend.getQqNumber())));
    }

    @Test
    public void groupInfo() {
        List<Group> groups = client.getGroups();
        Assume.assumeFalse(groups.isEmpty());
        Group group = groups.get(0);
        System.out.println(String.join(", ",
                group.getName(),
                Long.toString(group.getCreateTime()),
                group.getAnnouncement(),
                Integer.toString(group.getMembers().size()),
                group.getMembers().get(0).toString(),
                Long.toString(group.getOwnerId())));
    }

    @Test
    public void discussionInfo() {
        List<Discussion> discussions = client.getDiscussions();
        Assume.assumeFalse(discussions.isEmpty());
        Discussion discussion = discussions.get(0);
        for (DiscussionMember member : discussion.getMembers()) {
            System.out.println(member);
        }
    }
}
