package com.ccsu.feng.test.domain.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-03-15-20:08
 */
@Data
public class DeedsTreeVO {
    private String name;
    private String parent;
    private List<DeedsTreeVO> children =new ArrayList<>();

    public  void add(DeedsTreeVO deedsTreeVO){
        this.children.add(deedsTreeVO);
    }
}
