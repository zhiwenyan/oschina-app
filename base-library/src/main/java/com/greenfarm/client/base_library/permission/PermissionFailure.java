package com.greenfarm.client.base_library.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * Data：3/28/2018-3:34 PM
 *
 * @author: yanzhiwen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionFailure {
    int requestCode();
}
