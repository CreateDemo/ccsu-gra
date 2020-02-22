package com.ccsu.feng.test.domain.node.xinode;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.node.xinode.DeedsNode;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author admin
 * @create 2020-01-06-13:50
 * @description 地点
 */
@Data
@ToString
public class PlaceNode extends XiBaseNode {


    /**
     * 地点简介
     */
    private String content;



    /**
     * 图片
     */
    private String picture;






}
