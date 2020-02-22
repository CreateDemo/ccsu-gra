/**
 * @Copyright (C) 2019 广州金鹏集团有限公司.
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 * @创建人: 赵力
 * @创建时间: 2019-01-23 10:58
 * @版本: V1.0
 */
package com.ccsu.feng.test.domain.base;


import com.ccsu.feng.test.domain.relation.RelationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.time.LocalDateTime;



/**
 * @类功能说明: 关系实体对象
 * @创建人: 赵力
 * @创建时间: 2019-01-23 10:58
 */
@Data
@AllArgsConstructor
@RelationshipEntity(type= RelationType.REF)
@ToString
public class BaseRelationship<S extends BaseNode, E extends BaseNode> extends AllBaseEntity {

    /**
     * 关系名称; 例如：徒弟、师傅等
     */
    @Property
    private String name;

    /**
     * 创建时间
     */
    @Property
    private String createTime;

    /**
     * 开始节点
     */
    @JsonIgnore
    @StartNode
    private S startNode;

    /**
     * 结束节点
     */
    @JsonIgnore
    @EndNode
    private E endNode;

    public BaseRelationship(String name,S startNode,E endNode){
        this.name =name;
        this.createTime=LocalDateTime.now().toString();
        this.startNode=startNode;
        this.endNode=endNode;
    }
}
