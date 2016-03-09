package com.ultrapower.flowmanage.common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义树
 * subTree 封装的子树
 * @author zhengWei
 *
 */
public class Tree {
	private String id;
	private String parentid;
	private String name;
	private String val;
	private String lev;
	private String score;
	private String unit;
	private String  remark;
	private String orderid;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	private List<Tree> subTree = new ArrayList<Tree>();

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

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<Tree> getSubTree() {
		return subTree;
	}

	public void setSubTree(List<Tree> subTree) {
		this.subTree = subTree;
	}

	public void addSubTree(Tree tree) {
        subTree.add(tree);
    }
    
    public boolean hasSubTree() {
        return null != subTree && !subTree.isEmpty();
    }

}
