package pl.flashgoal.news.repos;

import  pl.flashgoal.news.models.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TagRepo extends Neo4jRepository<Tag, String> {
    Tag findByName(String name);
}