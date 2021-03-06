package com.uom.las3014.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Defines a number oc caches for different purposes
 */
@Configuration
public class MyCacheManager {
    public final static String TOPIC_CACHE = "TopicCache";
    public final static String DIGESTS_CACHE = "DigestsCache";
    public final static String TOP_STORY_CACHE = "TopStoryCache";

    @Bean
    public CacheManager cacheManager() {
        final SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        final GuavaCache topicCache = new GuavaCache(TOPIC_CACHE, CacheBuilder.newBuilder().build());
        final GuavaCache digestsCache = new GuavaCache(DIGESTS_CACHE, CacheBuilder.newBuilder().build());
        final GuavaCache topStoryCache = new GuavaCache(TOP_STORY_CACHE, CacheBuilder.newBuilder().build());
        simpleCacheManager.setCaches(Arrays.asList(topicCache, digestsCache, topStoryCache));
        return simpleCacheManager;
    }
}
