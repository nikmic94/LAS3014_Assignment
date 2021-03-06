package com.uom.las3014.batching.processors;

import com.uom.las3014.dao.Story;
import com.uom.las3014.dao.Topic;
import com.uom.las3014.service.StoriesService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Specific {@link ItemProcessor} which given a {@link Topic} finds the top {@link Story} from the last 24 hours and sets
 *     {@link Topic#topStoryId} to the identified {@link Story}
 */
@Component
public class TopStoryPerTopicProcessor implements ItemProcessor<Topic, Topic> {
    @Autowired
    private StoriesService storiesService;

    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public Topic process(final Topic topic) throws Exception {
        final Timestamp createdAfter = new Timestamp(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24));
        final List<Story> topStoryContainingKeyword = storiesService
                .getUndeletedStoriesContainingKeywordAndAfterTimestamp(topic.getTopicName(), createdAfter);

        final Optional<Story> topStoryOpt = topStoryContainingKeyword.stream().max(Comparator.comparing(Story::getScore));

        final Story topStory = topStoryOpt.orElse(null);

        if(topStory != null) {
            logger.debug(topic.getTopicName() + " has top story score " + topStory.getScore() + " and ID " + topStory.getStoryId());
            topic.setTopStoryId(topStory);
        } else {
            logger.debug(topic.getTopicName() + " has no top story");
            topic.setTopStoryId(null);
        }

        return topic;
    }
}
