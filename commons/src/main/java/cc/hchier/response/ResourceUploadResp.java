package cc.hchier.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author by Hchier
 * @Date 2023/4/13 9:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUploadResp {
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Data {
        private String url;

        private String alt;

        private String href;

        private String poster;

        public Data(String url, String alt, String href) {
            this.url = url;
            this.alt = alt;
            this.href = href;
        }

        public Data(String url, String poster) {
            this.url = url;
            this.poster = poster;
        }
    }

    private Integer errno;

    private Data data;

    private String message;

    public ResourceUploadResp(Integer errno, Data data) {
        this.errno = errno;
        this.data = data;
    }

    public ResourceUploadResp(Integer errno, String message) {
        this.errno = errno;
        this.message = message;
    }

    public static ResourceUploadResp picOk(String url, String alt, String href) {
        return new ResourceUploadResp(0, new Data(url, alt, href));
    }

    public static ResourceUploadResp videoOk(String url, String poster) {
        return new ResourceUploadResp(0, new Data(url, poster));
    }

    public static ResourceUploadResp fail(String message) {
        return new ResourceUploadResp(1, message);
    }

    public static void main(String[] args) {
        System.out.println(ResourceUploadResp.picOk("1", "2", "3").toString());
    }
}
