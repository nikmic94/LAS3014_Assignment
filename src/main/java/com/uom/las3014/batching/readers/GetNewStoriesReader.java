package com.uom.las3014.batching.readers;

import com.uom.las3014.dao.Story;
import com.uom.las3014.httpconnection.HackernewsRequester;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Specific {@link ItemReader} which gets new {@link Story} from the {@link HackernewsRequester}. Defined as {@link StepScope} so it
 *     is created for each step execution
 */
@Component
@StepScope
public class GetNewStoriesReader implements ItemReader<String> {
    private Iterator<String> newStoryIds;

    @Autowired
    public GetNewStoriesReader(final HackernewsRequester hackernewsRequester) throws IOException {
        final List<String> newStories = hackernewsRequester.getNewStories().orElse(new ArrayList<>());

        newStoryIds = newStories.iterator();
    }

    @Override
    public String read() throws Exception {
        return newStoryIds.hasNext() ? newStoryIds.next() : null;
    }
}
