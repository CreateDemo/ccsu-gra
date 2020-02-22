package com.ccsu.feng.test;

import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import com.ccsu.feng.test.service.IPersonNodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Autowired
    IPersonNodeService iPersonNodeService;

    @Test
    public void contextLoads() {
        PersonNode p =new PersonNode();
        p.setName("孙悟空");
        p.setAlias("齐天大圣");
        p.setCharacter("泼辣顽皮");
        p.setContent("孙悟空是。。。。");
        PersonNode personNode = iPersonNodeService.addPersonNode(p);
        if (personNode!=null){
            System.out.println("添加成功");
        }

    }



}

