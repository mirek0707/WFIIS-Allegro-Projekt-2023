package pl.flashgoal.news.repos;


import org.springframework.data.neo4j.repository.query.Query;
import pl.flashgoal.news.models.News;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.flashgoal.news.models.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NewsRepo extends Neo4jRepository<News, String> {
    @Query("MATCH (n:News)-[:TAGGED_BY]->(t:Tag) WHERE t.name = $tagName RETURN n")
    List<News> findByTagsName(String tagName);

    @Query("MATCH (n:News)-[:TAGGED_BY]->(tag:Tag) WHERE n.id = $newsId RETURN tag")
    List<Map<String, String>> findTagsForNews(String newsId);
}

