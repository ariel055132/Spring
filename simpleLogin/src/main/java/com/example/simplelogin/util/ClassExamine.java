package com.example.simplelogin.util;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;

/**
 * 比對使用者輸入的Object與從資料庫中獲取之Object的欄位資料是否一致
 * 若使用者輸入的Object的欄位中有值為空(empty)，使用從資料庫中獲取之Object的相同欄位資料進行補上
 * 否則使用使用者輸入的Object的欄位值進行替代
 *
 * @param origin 使用者輸入的Object
 * @param intactObject 從資料庫中獲取之Object
 * @param T generics，傳入Object的類型，本次為entity的User
 */

public class ClassExamine {
    public static <T> void objectOverlap(T origin, T intactObject) throws Exception {
        Field[] fields = origin.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                if (StrUtil.isEmpty((String) field.get(origin))) {
                    field.set(origin, field.get(intactObject));
                }
            } else {
                if (field.get(origin) == null) {
                    field.set(origin, field.get(intactObject));
                }
            }
        }
    }
}
