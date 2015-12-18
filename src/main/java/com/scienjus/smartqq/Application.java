package com.scienjus.smartqq;

import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.Group;

import java.util.List;

/**
 * @author XieEnlong
 * @date 2015/12/18.
 */
public class Application {

    public static void main(String[] args) {
        SmartQQClient client = new SmartQQClient();
        client.login();
        List<Group> groups = client.getGroupList();
        for (Group group : groups) {
            System.out.println("name: " + group.getName());
        }
    }
}
