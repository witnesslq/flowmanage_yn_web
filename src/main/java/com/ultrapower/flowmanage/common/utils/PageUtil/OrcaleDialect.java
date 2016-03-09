package com.ultrapower.flowmanage.common.utils.PageUtil;

/**
* oracle分页实现类
* @author zhengWei
*
*/
public class OrcaleDialect implements Dialect {
    @Override
    public String getPaginationSql(String sql, int pageNo, int pageSize) {
    	StringBuilder pageSql = new StringBuilder();
    	pageSql.append("SELECT * FROM (");
		pageSql.append("SELECT ROWNUM RN, TEMP.* FROM (");
		pageSql.append(sql);
		pageSql.append(") TEMP WHERE ROWNUM <= ");
		pageSql.append(pageNo* pageSize);
		pageSql.append(") WHERE RN > ");
		pageSql.append((pageNo- 1) * pageSize);
	
		return pageSql.toString();
    }
}


