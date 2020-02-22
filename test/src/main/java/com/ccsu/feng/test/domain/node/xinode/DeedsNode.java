package com.ccsu.feng.test.domain.node.xinode;



import lombok.Data;


/**
 * @author admin
 * @create 2020-01-06-13:53
 * @description 事件
 */
@Data
public class DeedsNode extends XiBaseNode {


    /**
     * 事件起因
     */
    private String origin;

    /**
     * 事迹内容简介
     */
    private String content;

    /**
     * 事件结果
     */
    private String result;

    /**
     * 事件的具体连接
     */
    private String url;

    /**
     * 圖片
     */
    private  String picture;



}
