package com.demo.mockservice.dto;

import lombok.Data;

@Data
public class BookSearchRequest {
    private int maxPage;
    private String bookName;
}
