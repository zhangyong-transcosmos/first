package com.lalafafa.first.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.lalafafa.first.core.utils.IdGenerator;
import com.lalafafa.first.core.utils.Utils;

/**
 * 数据库拦截器 插入和更新操作时，自动填充公共字段
 * 
 * @author T9Team
 *
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MyDbInterceptor implements Interceptor {

    private final static String FIELD_ID = "seqId";

    private final static String FIELD_CREATE_USER = "createUser";

    private final static String FIELD_CREATE_TIME = "createTime";

    private final static String FIELD_UPDATE_USER = "updateUser";

    private final static String FIELD_UPDATE_TIME = "updateTime";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (!(SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType))) {
            return invocation.proceed();
        }
        Object parameter = invocation.getArgs()[1];
        Date now = new Date();
        String userId = Utils.getCurrentUserId();
        Field[] fields = this.getAllFields(parameter);
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (FIELD_ID.equalsIgnoreCase(fieldName)) {
                Object value = field.get(parameter);
                if (value == null) {
                    field.set(parameter, IdGenerator.uuid());
                }
            }
            if (FIELD_CREATE_USER.equalsIgnoreCase(fieldName)) {
                Object value = field.get(parameter);
                if (value == null && SqlCommandType.INSERT.equals(sqlCommandType)) {
                    field.set(parameter, userId);
                }
            }
            if (FIELD_CREATE_TIME.equalsIgnoreCase(fieldName)) {
                Object value = field.get(parameter);
                if (value == null && SqlCommandType.INSERT.equals(sqlCommandType)) {
                    field.set(parameter, now);
                }
            }
            if (FIELD_UPDATE_USER.equalsIgnoreCase(fieldName)) {
                Object value = field.get(parameter);
                if (value == null) {
                    field.set(parameter, userId);
                }
            }
            if (FIELD_UPDATE_TIME.equalsIgnoreCase(fieldName)) {
                Object value = field.get(parameter);
                if (value == null) {
                    field.set(parameter, now);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<Field>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}