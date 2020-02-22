package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.xinode.WeaponNode;
import lombok.Data;
import org.w3c.dom.html.HTMLDOMImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-20-22:16
 */
@Data
public class WeaponVO extends WeaponNode {
    private List<String> pictures =new ArrayList<>(1);
    public WeaponVO(WeaponNode weaponNode){
        this.setId(weaponNode.getId());
        this.setName(weaponNode.getName());
        this.setPicture(weaponNode.getPicture());
        this.setCharacter(weaponNode.getCharacter());
        this.setContent(weaponNode.getContent());
        this.pictures.add(weaponNode.getPicture());
    }
}
