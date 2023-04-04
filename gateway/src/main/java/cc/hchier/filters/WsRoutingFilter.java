package cc.hchier.filters;

import cc.hchier.RestResponse;
import cc.hchier.configuration.Properties;
import cc.hchier.consts.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.WebsocketRoutingFilter;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/4/4 10:19
 */
@Component
public class WsRoutingFilter extends WebsocketRoutingFilter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final Properties properties;
    private final ObjectMapper objectMapper;

    public WsRoutingFilter(WebSocketClient webSocketClient,
                           WebSocketService webSocketService,
                           ObjectProvider<List<HttpHeadersFilter>> headersFiltersProvider,
                           RedisTemplate<String, Object> redisTemplate, Properties properties,
                           ObjectMapper objectMapper) {
        super(webSocketClient, webSocketService, headersFiltersProvider);

        this.redisTemplate = redisTemplate;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Override

    public int getOrder() {
        return 10001;
    }

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //不管非ws请求
        if (!"ws".equals(exchange.getRequest().getURI().getPath().substring(1, 3))) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getPath().toString().substring(4);
        String username = (String) redisTemplate.opsForHash().get(properties.hashForToken, token);
        //token无效，拦截
        if (username == null) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            byte[] bytes = objectMapper.writeValueAsString(RestResponse.build(ResponseCode.AUTH_FAIL)).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
        //token有效，放行
        return super.filter(exchange,chain);
    }
}
