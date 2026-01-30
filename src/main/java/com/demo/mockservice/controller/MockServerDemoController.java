package com.demo.mockservice.controller;

import com.demo.mockservice.dto.BookSearchRequest;
import com.demo.mockservice.dto.BookSearchResponse;
import com.demo.mockservice.dto.DynamicResponseBookSearchRequest;
import com.demo.mockservice.util.FileReader;
import com.demo.mockservice.util.RandomDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
This Controller is using to test the mock server itself.
*/
@RequestMapping("/mockServerDemoController")
@RestController
public class MockServerDemoController {

    private final Logger logger = LoggerFactory.getLogger(MockServerDemoController.class);

    @RequestMapping(value = "/simpleFakeData",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public BookSearchResponse simpleFakeData(@RequestBody BookSearchRequest bookSearchRequest) {

        BookSearchResponse result = RandomDataGenerator.generateRandomData(BookSearchResponse.class);
        // Optional custom hard manipulations for "result" instance.
        result.setResponseId("This is hard manipulation example - from Java controller");
        return result;
    }

    @RequestMapping(value = "/resultFromFile",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public String resultFromFile(@RequestBody BookSearchRequest bookSearchRequest) {

        return FileReader.readFile("response1.xml");
    }

    /*
    You don't need below 2 methods. Below methods added here, so we can see them from Swagger-web-UI.
    */
    @RequestMapping(value = "/dynamicResponseXML",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public BookSearchResponse dynamicResponseXML(@RequestBody DynamicResponseBookSearchRequest bookSearchRequest) {

        return null;
    }

    @RequestMapping(value = "/dynamicResponseJSON",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BookSearchResponse dynamicResponseJSON(@RequestBody DynamicResponseBookSearchRequest bookSearchRequest) {
        
        return null;
    }
}