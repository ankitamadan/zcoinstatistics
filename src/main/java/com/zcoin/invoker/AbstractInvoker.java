package com.zcoin.invoker;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

abstract public class AbstractInvoker {

    public <T> ResponseEntity<T> exchange(Class aClass, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = getHeader();
        return restTemplate.exchange(url, HttpMethod.GET, entity, aClass);
    }

    public HttpEntity<String> getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return new HttpEntity<String>("parameters", headers);
    }

    public String createAddressDetailsUrl(String address){
        return "https://chainz.cryptoid.info/xzc/api.dws?q=multiaddr&active="
                + address
                + "&key=47bad585210f&n=4600";
    }


}
