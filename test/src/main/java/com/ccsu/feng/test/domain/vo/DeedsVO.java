package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.xinode.DeedsNode;
import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-21-12:17
 */
@Data
public class DeedsVO extends DeedsNode {
    private List<String> pictures =new ArrayList<>(1);

    public DeedsVO(DeedsNode deedsNode){
        this.setId(deedsNode.getId());
        this.setContent(deedsNode.getContent());
        this.setName(deedsNode.getName());
        this.setOrigin(deedsNode.getOrigin());
        this.setUrl(deedsNode.getUrl());
        this.setResult(deedsNode.getResult());
        this.setPicture(deedsNode.getPicture());
        this.pictures.add(deedsNode.getPicture());
    }
}
