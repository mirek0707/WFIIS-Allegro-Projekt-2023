package pl.flashgoal.news.repos;


import org.springframework.data.neo4j.repository.query.Query;
import pl.flashgoal.news.models.News;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepo extends Neo4jRepository<News, String> {
    @Query("MATCH (n:News)-[:TAGGED_BY]->(t:Tag) WHERE t.name = $tagName RETURN n") //TODO
    List<News> findByTagsName(String tagName);

//    @Query("MATCH (n:News) WHERE n.id = $id RETURN n")
//    Optional<News> findById(String id);
}

