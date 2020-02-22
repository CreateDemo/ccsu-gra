package com.ccsu.feng.test.domain.node.xinode;

import com.ccsu.feng.test.domain.base.BaseNode;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 武器节点
 * @author admin
 * @create 2020-02-10-15:54
 */
@NodeEntity
@ToString
@Data
public class WeaponNode extends XiBaseNode {

    /**
     * 武器简介
     */
    private String content;

    /**
     * 武器特点
     */
    private String character;

    /**
     * 图片
     */
    private String picture;
}
