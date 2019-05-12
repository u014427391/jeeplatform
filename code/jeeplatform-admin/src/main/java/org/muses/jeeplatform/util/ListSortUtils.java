package org.muses.jeeplatform.util;

import java.lang.reflect.Field;
import java.text.Collator;
import java.util.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年05月11日  修改内容:
 * </pre>
 */
public class ListSortUtils {

    public static final String DESC = "desc";
    public static final String ASC = "asc";

    /**
     * 升序排序
     * @param list
     * @param field
     * @return
     */
    public static List<?> sortByAsc(List<?> list, String field){
        return sort(list , field, ASC);
    }

    /**
     * 倒序排序
     * @param list
     * @param field
     * @return
     */
    public static List<?> sortByDesc(List<?> list, String field){
        return sort(list, field, DESC);
    }

    /**
     * 集合排序
     * @param list
     * @param field 排序字段
     * @param sort 排序方式
     * @return
     */
    public static List<?> sort(List<?> list, String field, String sort){
        Collections.sort(list, new Comparator() {
            public int compare(Object a, Object b) {
                int ret = 0;
                try {
                    Field f = a.getClass().getDeclaredField(field);
                    f.setAccessible(true);
                    Class<?> type = f.getType();

                    if (type == int.class) {
                        ret = ((Integer) f.getInt(a)).compareTo((Integer) f.getInt(b));
                    } else if (type == double.class) {
                        ret = ((Double) f.getDouble(a)).compareTo((Double) f.getDouble(b));
                    } else if (type == long.class) {
                        ret = ((Long) f.getLong(a)).compareTo((Long) f.getLong(b));
                    } else if (type == float.class) {
                        ret = ((Float) f.getFloat(a)).compareTo((Float) f.getFloat(b));
                    } else if (type == Date.class) {
                        ret = ((Date) f.get(a)).compareTo((Date) f.get(b));
                    } else if (isImplementsOf(type, Comparable.class)) {
                        ret = ((Comparable) f.get(a)).compareTo((Comparable) f.get(b));
                    } else {
                        Collator instance = Collator.getInstance(Locale.CHINA);
                        ret =instance.compare(String.valueOf(f.get(a)),String.valueOf(f.get(b)));
                    }

                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (sort != null && sort.toLowerCase().equals(DESC)) {
                    return -ret;
                } else {
                    return ret;
                }

            }
        });
        return list;
    }

    /**
     * 判断对象实现的所有接口中是否包含szInterface
     *
     * @param clazz
     * @param szInterface
     * @return
     */
    public static boolean isImplementsOf(Class<?> clazz, Class<?> szInterface) {
        boolean flag = false;

        Class<?>[] face = clazz.getInterfaces();
        for (Class<?> c : face) {
            if (c == szInterface) {
                flag = true;
            } else {
                flag = isImplementsOf(c, szInterface);
            }
        }

        if (!flag && null != clazz.getSuperclass()) {
            return isImplementsOf(clazz.getSuperclass(), szInterface);
        }

        return flag;
    }
}
