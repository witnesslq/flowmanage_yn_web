package com.ultrapower.flowmanage.common.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ultrapower.flowmanage.common.utils.TreeComparator;
import com.ultrapower.flowmanage.common.utils.Utils;

/**
 * 自定义树
 * subTree 封装的子树
 * @author zhengWei
 *
 */
public class FlowExitTree {
	private String id;
	private String parentid;
	private String name;
	private String lev;
	private String orderid;

	private String number;
	private String collecttime;
	private String exitName;
	private String inflow;
	private String outflow;
	private String totalflow;
	private String totalflow_perc;

	private List<FlowExitTree> subTree = new ArrayList<FlowExitTree>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCollecttime() {
		return collecttime;
	}

	public void setCollecttime(String collecttime) {
		this.collecttime = collecttime;
	}

	public String getExitName() {
		return exitName;
	}

	public void setExitName(String exitName) {
		this.exitName = exitName;
	}

	public String getInflow() {
		return inflow;
	}

	public void setInflow(String inflow) {
		this.inflow = inflow;
	}

	public String getOutflow() {
		return outflow;
	}

	public void setOutflow(String outflow) {
		this.outflow = outflow;
	}

	public String getTotalflow() {
		return totalflow;
	}

	public void setTotalflow(String totalflow) {
		this.totalflow = totalflow;
	}

	public String getTotalflow_perc() {
		return totalflow_perc;
	}

	public void setTotalflow_perc(String totalflow_perc) {
		this.totalflow_perc = Utils.valueOf(totalflow_perc);
	}

	public List<FlowExitTree> getSubTree() {
		return subTree;
	}

	public void setSubTree(List<FlowExitTree> subTree) {
		this.subTree = subTree;
	}

	public void addSubTree(FlowExitTree tree) {
        subTree.add(tree);
    }
    
    public boolean hasSubTree() {
        return null != subTree && !subTree.isEmpty();
    }
    
    public void sortTree() {
		if (hasSubTree()) {
			Collections.sort(subTree, new TreeComparator());
			for (Iterator<FlowExitTree> it = subTree.iterator(); it.hasNext();) {
				it.next().sortTree();
			}
		}
	}


}
