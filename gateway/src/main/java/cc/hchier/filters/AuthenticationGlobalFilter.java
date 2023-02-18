package cc.hchier.filters;

import cc.hchier.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.configuration.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author by Hchier
 * @Date 2023/2/11 23:34
 */
@Component
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered {
    private final RedisTemplate redisTemplate;
    private final Properties properties;
    private final ObjectMapper objectMapper;


    public AuthenticationGlobalFilter(RedisTemplate redisTemplate, Properties properties, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }


    /**
     * 如果访问需要登录访问的路径且没登陆或token无效，则该请求将被拦截
     *
     * @param exchange 交换
     * @param chain    链
     * @return {@link Mono}<{@link Void}>
     */
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        //访问需要登录的接口
        if (!properties.whitePaths.containsKey(path)) {
            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
            HttpCookie httpCookie = cookies.getFirst("token");

            //存在token
            if (httpCookie != null) {
                String username = (String) redisTemplate.opsForHash().get(properties.hashForToken, httpCookie.getValue());
                //token有效
                if (username != null){
                    ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
                    //将username放入请求头中，后续服务直接从请求头中得到当前用户身份
                    mutate.header("username", username);
                    ServerHttpRequest build = mutate.build();
                    exchange.mutate().request(build).build();
                    return chain.filter(exchange);
                }
            }
            //token无效，则直接拦截请求
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            byte[] bytes = objectMapper.writeValueAsString(RestResponse.build(ResponseCode.AUTH_FAIL)).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

