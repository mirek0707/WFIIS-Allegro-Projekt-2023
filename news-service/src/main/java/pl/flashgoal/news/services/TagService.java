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
        Tag existingTag = tagRepository.findByName(tagName);

        if (existingTag != null) {
            return existingTag;
        } else {
            Tag newTag = new Tag();
            newTag.setName(tagName);
            return tagRepository.save(newTag);
        }
    }
}