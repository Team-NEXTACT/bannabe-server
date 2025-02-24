package site.bannabe.server.global.type;

import org.springframework.http.HttpMethod;

public record EndPoint(
    HttpMethod method,
    String pattern
) {

}