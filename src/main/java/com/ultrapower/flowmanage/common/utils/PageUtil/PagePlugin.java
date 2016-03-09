package com.ultrapower.flowmanage.common.utils.PageUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.ultrapower.flowmanage.common.vo.Page;

/**
* 分页拦截器
* @author zhengWei
*
*/
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagePlugin implements Interceptor {
	private static Dialect.Type databaseType = null;
    private static String pageSqlId = "";
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation
            		.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
                    .getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper
                    .getValueByFieldName(delegate, "mappedStatement");

            if (mappedStatement.getId().matches(pageSqlId)) {
                BoundSql boundSql = delegate.getBoundSql();
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject error");
                } else {
                    Connection connection = (Connection)invocation.getArgs()[0];
                    String sql = boundSql.getSql();
                    String countSql = "select count(0) from (" + sql + ") myCount";
                    System.out.println("总数sql 语句:" + countSql);
                    PreparedStatement countStmt = connection
                            .prepareStatement(countSql);
                    BoundSql countBS = new BoundSql(
                            mappedStatement.getConfiguration(), countSql,
                            boundSql.getParameterMappings(), parameterObject);
                    setParameters(countStmt, mappedStatement, countBS,
                            parameterObject);
                    ResultSet rs = countStmt.executeQuery();
                    int rowCount = 0;
                    if (rs.next()) {
                    	rowCount = rs.getInt(1);
                    }
                    
                    rs.close();
                    countStmt.close();

                    Page page = null;
                    if (parameterObject instanceof Page) {
                        page = (Page) parameterObject;
                        page.setRowCount(rowCount);
                    } else if(parameterObject instanceof Map){
                        Map<String, Object> map = (Map<String, Object>)parameterObject;
                        page = (Page)map.get("page");
                        if(page == null) page = new Page();
                        page.setRowCount(rowCount);
                    } else {
                        Field pageField = ReflectHelper.getFieldByFieldName(
                                parameterObject, "page");
                        if (pageField != null) {
                            page = (Page) ReflectHelper.getValueByFieldName(
                                    parameterObject, "page");
                            if (page == null)
                                page = new Page();
                            page.setRowCount(rowCount);
                            ReflectHelper.setValueByFieldName(parameterObject,
                                    "page", page);
                        } else {
                            throw new NoSuchFieldException(parameterObject
                                    .getClass().getName());
                        }
                    }
                    
                    Dialect dialect =null;
                    switch(databaseType){          
                        //case MYSQL: dialect =new MySQLDialect(); 
                        case ORACLE : dialect = new OrcaleDialect();

                    }
                    
                    String pageSql = dialect.getPaginationSql(sql, page.getPageNo(), page.getPageSize());

                   // String pageSql = generatePageSql(sql, page);
                    System.out.println("page sql:" + pageSql);
                    ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
                }
            }
        }
        return invocation.proceed();
    }
    
    @SuppressWarnings("unchecked")
    private void setParameters(PreparedStatement ps,
            MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters")
                .object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql
                .getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration
                    .getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null
                    : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry
                            .hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName
                            .startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value)
                                    .getValue(
                                            propertyName.substring(prop
                                                    .getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject
                                .getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException(
                                "There was no TypeHandler found for parameter "
                                        + propertyName + " of statement "
                                        + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value,
                            parameterMapping.getJdbcType());
                }
            }
        }
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties p) {
        if (p.getProperty("dialect") !=null &&  !p.getProperty("dialect").equals("")) {
        	databaseType = Dialect.Type.valueOf(p.getProperty("dialect").toUpperCase());
        } else {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
        
        pageSqlId = p.getProperty("pageSqlId");
        if (pageSqlId ==null || pageSqlId.equals("")) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
    }

}
