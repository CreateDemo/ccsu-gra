package com.ccsu.feng.test.service;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.base.BaseRelationship;

import java.util.Optional;

/**
 * @author admin
 * @description 关系服务类接口
 * @create 2020-02-10-15:24
 */
public interface IBaseRelationshipService {

    /**
     * @param relationship
     * @return BaseRelationship
     * @description 添加关系
     */
    BaseRelationship addRelationship(BaseRelationship relationship);

    /**
     * 为两个节点添加指向关系，例如 name:徒弟，startNode:唐僧 ， endNode:孙悟空，
     * 那么意思是：唐僧的徒弟是孙悟空
     *
     * @param name
     * @param startNode 开始节点
     * @param endNode   结束节点
     * @return
     */
    BaseRelationship addRelationship(String name, BaseNode startNode, BaseNode endNode);

    /**
     * @param id
     * @return
     * @description 根据Id删除关系
     */
    boolean deleteRelationshipById(Long id);


    /**
     * @param name
     * @param startName
     * @param endName
     * @return
     * @description 获取关系 根据节点名称和关系名称
     */
    BaseRelationship findRelationshipByStarNameAndEndName(String name, String startName, String endName);

    /**
     * @param id
     * @return
     */
    Optional<BaseRelationship> findRelationshipById(Long id);

}
