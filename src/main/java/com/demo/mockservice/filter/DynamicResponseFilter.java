package com.demo.mockservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class DynamicResponseFilter implements Filter {

    public static final String[] EXCLUDE_URL_LIST = new String[]{"csrf", "actuator", "v2/api-docs", "swagger", "springfox", "favicon.ico"};
    private static final String MOCK_RESPONSE_FIELD = "mockServerResponseData";
    private final Logger logger = LoggerFactory.getLogger(DynamicResponseFilter.class);

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String path = httpServletRequest.getRequestURI();
        if (!Arrays.stream(EXCLUDE_URL_LIST).anyMatch(path::contains) && !path.equals("/")) {
            if (request.getContentType() != null &&
                    (request.getContentType().equals(MediaType.APPLICATION_XML_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE))) {
                parseResponseFromRequestAndFlushAsResponse(httpServletRequest, response, request.getContentType(), chain);
                return;
            }
        }
        chain.doFilter(httpServletRequest, response);
    }

    private void parseResponseFromRequestAndFlushAsResponse(
            final HttpServletRequest httpServletRequest,
            final ServletResponse response,
            final String contentType,
            final FilterChain chain)
            throws IOException, ServletException {

        final RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        final String body = requestWrapper.getBody();

        String newResponse = null;
        try {
            if (contentType.equals(MediaType.APPLICATION_XML_VALUE)) {
                newResponse = parseResponseFromXML(body);
            } else if (contentType.equals(MediaType.APPLICATION_JSON_VALUE)) {
                newResponse = parseResponseFromJSON(body);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
        if (newResponse == null) {
            // this case means that the request body does not contain the MOCK_RESPONSE_FIELD.
            chain.doFilter(requestWrapper, response);
        } else {
            sendResponse(newResponse, response, MediaType.APPLICATION_XML_VALUE);
        }
    }

    private String parseResponseFromJSON(final String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootJsonNode = objectMapper.readTree(json);
        JsonNode jsonNode = rootJsonNode.get(MOCK_RESPONSE_FIELD);
        if (jsonNode != null && !jsonNode.isMissingNode()) {
            return jsonNode.toPrettyString();
        }
        return null;
    }

    private String parseResponseFromXML(final String xml) throws JsonProcessingException {
        XmlMapper objectMapper = new XmlMapper();
        TreeNode rootXmlNode = objectMapper.readTree(xml);
        TreeNode xmlNode = rootXmlNode.get(MOCK_RESPONSE_FIELD);
        if (xmlNode != null && !xmlNode.isMissingNode()) {
            return xmlNode.toString();
        }
        return null;
    }

    private void sendResponse(final String responseBodyStr, final ServletResponse servletResponse, final String mediaType)
            throws IOException {

        final byte[] responseBody = responseBodyStr.getBytes(StandardCharsets.UTF_8);
        final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setContentLength(responseBody.length);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        httpServletResponse.setContentType(mediaType);
        final OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(responseBody);
        outputStream.flush();
        outputStream.close();
    }
}