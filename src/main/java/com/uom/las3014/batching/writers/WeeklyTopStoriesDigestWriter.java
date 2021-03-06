package com.uom.las3014.batching.writers;

import com.uom.las3014.dao.Digest;
import com.uom.las3014.service.DigestsService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Specific {@link ItemWriter} which saves all {@link Digest}
 */
@Component
public class WeeklyTopStoriesDigestWriter implements ItemWriter<Digest> {
    @Autowired
    private DigestsService digestsService;

    @Override
    public void write(final List<? extends Digest> list) throws Exception {
        digestsService.saveAll(list);
    }
}
