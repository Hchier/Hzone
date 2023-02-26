package cc.hchier.talk.protocol;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author by Hchier
 * @Date 2022/10/5 11:19
 */
public interface Serializer {
    /**
     * 序列化
     *
     * @param obj obj
     * @return {@link byte[]}
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     *
     * @param clazz clazz
     * @param bytes 字节
     * @return {@link T}
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 序列化算法，用户可以选择不同的
     */
    @Slf4j
    enum Algorithm implements Serializer {
        /**
         * 使用io流
         */
        Jdk {
            @Override
            public byte[] serialize(Object obj) {
                try (
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos)
                ) {
                    oos.writeObject(obj);
                    return baos.toByteArray();
                } catch (IOException e) {
                    log.info("序列化出错");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try (
                    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bais)
                ) {
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    log.info("序列化失败");
                    e.printStackTrace();
                }
                return null;
            }
        },

        /**
         * 使用gson
         */
        Gson {
            @Override
            public byte[] serialize(Object obj) {
                return new Gson().toJson(obj).getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                return new Gson().fromJson(new String(bytes, StandardCharsets.UTF_8), clazz);
            }
        };
    }
}
