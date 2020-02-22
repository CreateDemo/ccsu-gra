package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.xinode.PlaceNode;
import com.ccsu.feng.test.domain.node.xinode.WeaponNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @create 2020-02-12-18:32
 */
@Data
public class PlaceVO extends PlaceNode{

    private List<String> pictures =new ArrayList<>(1);

    public PlaceVO(PlaceNode placeNode){
        this.setId(placeNode.getId());
        this.setName(placeNode.getName());
        this.setPicture(placeNode.getPicture());
        this.setContent(placeNode.getContent());
        this.pictures.add(placeNode.getPicture());
    }
}
