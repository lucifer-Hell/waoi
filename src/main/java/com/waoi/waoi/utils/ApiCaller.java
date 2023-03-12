package com.waoi.waoi.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiCaller {

    private final RestTemplate restTemplate;

    public ApiCaller() {
        restTemplate = new RestTemplate();
    }

    public <T> T callApi(String url, HttpMethod method, HttpHeaders headers, Object requestBody, Class<T> responseType) throws RestClientException {
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        return response.getBody();
    }
//
//    public <T> T callApi(String url, HttpMethod method, Object requestBody, Class<T> responseType) throws RestClientException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return callApi(url, method, headers, requestBody, responseType);
//    }
//
//    public <T> T callApi(String url, HttpMethod method, Class<T> responseType) throws RestClientException {
//        return callApi(url, method, null, null, responseType);
//    }

}
