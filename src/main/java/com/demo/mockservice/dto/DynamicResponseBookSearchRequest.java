package com.demo.mockservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DynamicResponseBookSearchRequest {

    private int maxPage;

    private String bookName;

    @Schema(
            title = "This value will be return as response automatically from mock-server.",
            example = "{ 'customer': 'mehmet', 'book': { 'name': 'davinci code' } }")
    private String mockServerResponseData;
}
