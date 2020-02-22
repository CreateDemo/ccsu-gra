package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.node.xinode.DeedsNode;
import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 事迹 DTO
 *
 * @author admin
 * @create 2020-02-11-21:39
 */
@Repository
public interface DeedsNodeRepository extends Neo4jRepository<DeedsNode, Long> {
    DeedsNode getDeedsNodeByName(String name);

    @Query("MATCH (n:DeedsNode) RETURN count(n)")
    Long getDeedsNodeCount();

    @Query("MATCH (n:DeedsNode)  where n.name Contains {name} RETURN n skip {pageIndex} limit {pageSize}")
    List<DeedsNode> getListDeedsNodeByPageAndName(@Param("name") String name,
                                                  @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
}
