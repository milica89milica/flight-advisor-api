package com.htec.fa_api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//country - without delete i evict
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheCleaner {

    private static final Logger log = LoggerFactory.getLogger(CacheCleaner.class);

    private final MessageSource messageSource;

    private final CacheManager cacheManager;

    public CacheCleaner(MessageSource messageSource, CacheManager cacheManager) {
        this.messageSource = messageSource;
        this.cacheManager = cacheManager;
    }

    @Scheduled(cron ="${cacheCleanCron}")
    public void clear() {
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
        log.info(messageSource.getMessage("cache.cleared", null, null));

    }
}

