package xyz.hchier.hzone.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author by Hchier
 * @Date 2022/6/23 12:56
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface TestPass {
}
