package com.ultrapower.flowmanage.common.utils.PageUtil;
/**
* 数据库方言接口
* @author zhengWei
*
*/
public interface Dialect {
  public static enum Type {
     ORACLE {
         public String getValue() {return "oracle";}
     };
     public abstract String getValue();
  }

  /**
   * 获取分页sql
   * @param sql 原始查询sql
   * @param offset 开始记录索引（从0开始计数）
   * @param limit 每页记录大小
   * @return 数据库相关的分页sql
   */
  public String getPaginationSql(String sql, int offset, int limit);
}

