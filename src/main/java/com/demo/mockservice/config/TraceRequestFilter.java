package com.demo.mockservice.config;

import com.demo.mockservice.filter.DynamicResponseFilter;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.Include;
import org.springframework.boot.actuate.web.exchanges.servlet.HttpExchangesFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
// HTTP Trace actuator filter (excludes the "swagger", "actuator" requests)
public class TraceRequestFilter extends HttpExchangesFilter {

    public TraceRequestFilter(HttpExchangeRepository repository) {
        super(repository, Include.defaultIncludes());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (request.getServletPath().equals("/")) {
            return true;
        }
        return Arrays.stream(DynamicResponseFilter.EXCLUDE_URL_LIST).anyMatch(request.getServletPath()::contains);
    }
}