package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-10-15:17
 */
@Repository
public interface BaseRelationshipRepository extends Neo4jRepository<BaseRelationship, Long> {

    BaseRelationship getBaseRelationshipByName(String name);

    @Query("MATCH p=(n:BaseNode)-[r:Ref]->(m:BaseNode) WHERE r.name={name}  and n.name={startName} and m.name={endName} RETURN r")
    List<BaseRelationship> findRelationshipByStarNameAndEndName(@Param("name") String name, @Param("startName") String startName, @Param("endName") String endName);
}
