package com.biblioteca.api_publica.domain.dto.google;

import java.util.List;

import lombok.Data;

@Data
public class GoogleBooksResponse {
    private List<Item> items;

    @Data
    public static class Item {
        private VolumeInfo volumeInfo;
    }

    @Data
    public static class VolumeInfo {
        private String title;
        private String description;
        private String publishedDate; 
    }
}