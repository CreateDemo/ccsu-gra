package com.ccsu.feng.test.domain.node.xinode;

import com.ccsu.feng.test.domain.base.BaseNode;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author admin
 * @create 2020-01-06-13:50
 * @description   人物节点
 */
@Data
@NodeEntity
@ToString
public class PersonNode extends XiBaseNode {

    /**.
     *别名称号
     */
    private String alias;

    /**
     * 人物性格特点
     */
    private String character;

    /**
     * 人物简介
     */
    private String content;

    /**
     * 图片
     */
    private String picture;


}
