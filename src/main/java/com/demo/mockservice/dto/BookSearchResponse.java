package com.demo.mockservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookSearchResponse {
    private int maxPage;
    private List<Book> results;
    private String responseId;
}
