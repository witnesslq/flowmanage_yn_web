package com.ultrapower.flowmanage.common.utils;

import java.util.Comparator;

import com.ultrapower.flowmanage.common.vo.FlowExitTree;

/**
 * 自定义树
 * subTree 树节点排序
 * @author zhengWei
 *
 */
public class TreeComparator implements Comparator<FlowExitTree> {
	public int compare(FlowExitTree o1, FlowExitTree o2) {
		int order1 = 0, order2 = 0;
		if (o1.getOrderid() != null) order1 = Integer.valueOf(o1.getOrderid());
		if (o2.getOrderid() != null) order2 = Integer.valueOf(o2.getOrderid());
		return (order1 < order2 ? -1 : (order1 ==  order2 ? 0 : 1));
	}


}
