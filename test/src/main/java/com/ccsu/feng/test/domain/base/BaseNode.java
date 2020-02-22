package com.ccsu.feng.test.domain.base;


import com.ccsu.feng.test.domain.relation.RelationType;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * @author admin
 * @create 2020-02-10-15:03
 */
@Data
@ToString
public class BaseNode extends AllBaseEntity {
    /**
     * 名称，基本上所有的Node都会有
     * 在此建立了唯一索引
     */
    @Index
    private String name;

    /**
     * 关系列表
     */
    @Relationship(type= RelationType.REF)
    private Set<BaseRelationship> relationships;


}
