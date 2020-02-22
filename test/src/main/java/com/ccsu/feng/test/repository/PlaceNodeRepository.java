package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import com.ccsu.feng.test.domain.node.xinode.PlaceNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 地点 DTO
 *
 * @author admin
 * @create 2020-02-11-21:41
 */
@Repository
public interface PlaceNodeRepository extends Neo4jRepository<PlaceNode, Long> {
    PlaceNode getPlaceNodeByName(String name);

    @Query("MATCH (n:PlaceNode) RETURN count(n)")
    Long getPlaceNodeCount();

    @Query("MATCH (n:PlaceNode)  where n.name Contains {name} RETURN n skip {pageIndex} limit {pageSize}")
    List<PlaceNode> getListPlaceNodeeByPageAndName(@Param("name") String name,
                                                   @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
}
