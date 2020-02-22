package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-11-20:51
 */
@Data
public class PersonVO extends PersonNode {

    private List<String> pictures =new ArrayList<>(1);

    public PersonVO(PersonNode personNode){
        this.setId(personNode.getId());
        this.setAlias(personNode.getAlias());
        this.setName(personNode.getName());
        this.setCharacter(personNode.getCharacter());
        this.setContent(personNode.getContent());
        this.setPicture(personNode.getPicture());
        this.pictures.add(personNode.getPicture());
    }
}
