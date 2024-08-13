package com.kosa.gallerygather.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BlockListManager {

    private Map<String, Boolean> blockList = new ConcurrentHashMap<>();

    public void addBlockList(String accessToken) {
        blockList.put(accessToken, true);
    }
}
