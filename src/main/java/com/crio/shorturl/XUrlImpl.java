package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class XUrlImpl implements XUrl{
    private static final String SHORT_URL_PREFIX = "http://short.url/";
    private static final int SHORT_URL_LENGTH = 9;
    private static final String ALPHANUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ALPHANUMERIC_STRING_LENGTH = ALPHANUMERIC_STRING.length();

    private Map<String, String> shortToLongUrlMap;
    private Map<String, String> longToShortUrlMap;
    private Map<String, Integer> longUrlHitCounMap;

    public XUrlImpl() {
        shortToLongUrlMap = new HashMap<>();
        longToShortUrlMap = new HashMap<>();
        longUrlHitCounMap = new HashMap<>();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            return null;
        }

        if (longToShortUrlMap.containsKey(longUrl)) {
            return longToShortUrlMap.get(longUrl);
        }
        String shortUrl = generateShortUrl();
        shortToLongUrlMap.put(shortUrl, longUrl);
        longToShortUrlMap.put(longUrl, shortUrl);
        return shortUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (longUrl == null || longUrl.isEmpty() || shortUrl == null || shortUrl.isEmpty()) {
            return null;
        }
        if (shortToLongUrlMap.containsKey(shortUrl)) {
            return null;
        }
        longToShortUrlMap.put(longUrl, shortUrl);
        shortToLongUrlMap.put(shortUrl, longUrl);
        longUrlHitCounMap.put(longUrl, 0);

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        if (shortToLongUrlMap.containsKey(shortUrl)) {
            String longUrl = shortToLongUrlMap.get(shortUrl);
            longUrlHitCounMap.put(longUrl, longUrlHitCounMap.getOrDefault(longUrl, 0) + 1);
            return longUrl;
        }
        return null;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        
        if(longUrl == null || longUrl.isEmpty()) {
            return 0;
        }
        if(!longUrlHitCounMap.containsKey(longUrl)) {
            return 0;
        }

        return longUrlHitCounMap.get(longUrl);
    }

    @Override
    public void delete(String longUrl) {
        if(longUrl == null || longUrl.isEmpty()) {
            return;
        }
        if(!longToShortUrlMap.containsKey(longUrl)) {
            return;
        }
        String shortUrl = longToShortUrlMap.get(longUrl);
        shortToLongUrlMap.remove(shortUrl);
        longToShortUrlMap.remove(longUrl);
        longUrlHitCounMap.remove(longUrl);
    }

    private String generateShortUrl(){
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_PREFIX);
        // Random random = new Random();
        for(int i = 0; i < SHORT_URL_LENGTH; i++) {
            shortUrl.append(ALPHANUMERIC_STRING.charAt(new Random().nextInt(ALPHANUMERIC_STRING_LENGTH)));
        }
        return shortUrl.toString();
    } 
}