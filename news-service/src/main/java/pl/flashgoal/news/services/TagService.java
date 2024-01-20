package pl.flashgoal.news.services;

import pl.flashgoal.news.models.Tag;
import pl.flashgoal.news.repos.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepo tagRepository;

    public Tag getOrCreateTag(String tagName) {
        // Sprawdzamy, czy tag o danej nazwie już istnieje
        Tag existingTag = tagRepository.findByName(tagName);

        if (existingTag != null) {
            return existingTag; // Jeśli istnieje, zwracamy istniejący tag
        } else {
            // Jeśli nie istnieje, tworzymy nowy tag
            Tag newTag = new Tag();
            newTag.setName(tagName);
            return tagRepository.save(newTag);
        }
    }
}