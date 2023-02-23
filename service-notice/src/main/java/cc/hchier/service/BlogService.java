package cc.hchier.service;

import cc.hchier.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hchier
 */
@FeignClient(value = "service-blog")
public interface BlogService {
    /**
     * 通过id获取作者
     *
     * @param id id
     * @return {@link RestResponse}<{@link String}>
     */
    @PostMapping("/blog/getAuthorById/{id}")
    RestResponse<String> getAuthorById(@PathVariable("id") Integer id);
}
