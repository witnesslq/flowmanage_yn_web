package com.ultrapower.flowmanage.common.utils;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ultrapower.flowmanage.common.vo.FlowExitTree;
import com.ultrapower.flowmanage.common.vo.Tree;

/**
 * 树结构相关操作
 * @author zhengWei
 *
 */
public class FlowTree {
	private static Document document;
	private static double totalFlow = 0;
	
	/** 先序遍历树节点，同时创建树结构xml */
	public static String createXml(Tree tree, Element parentNode, String maxLevel) {
		Element currentNode = null;
		int currentLevel = 0;
		if (parentNode == null) {
			document = DocumentHelper.createDocument();
			parentNode = document.addElement("message");
			parentNode.addAttribute("id", tree.getId());
			parentNode.addAttribute("name", tree.getName());
			if (tree.getScore() != null)
				parentNode.addAttribute("score", tree.getScore());
		}
		
		for (Tree tempTree:tree.getSubTree()) {
			String val = "", unit = "";
//			System.out.println("-----------------------"+tempTree.getScore() );
			currentLevel = Integer.valueOf(tempTree.getLev());
			currentNode = parentNode.addElement("message");
			currentNode.addAttribute("id", tempTree.getId());
			currentNode.addAttribute("name", tempTree.getName());
			if (tempTree.getScore() != null)
				currentNode.addAttribute("score", tempTree.getScore());
			
			if (currentLevel == Integer.valueOf(maxLevel)){
				if (tempTree.getVal() != null) val = tempTree.getVal();
				if (tempTree.getUnit() != null) unit = tempTree.getUnit();
				currentNode.addAttribute("val", val + unit);
			}		
			if (tempTree.hasSubTree()) {
				createXml(tempTree, currentNode, maxLevel);
			}	
		}
		return document.asXML();
	}
	/** 先序遍历树节点，同时创建树结构xml */
	public static String createXmlWithLayout(Tree tree, Element parentNode, String layout) {
		Element currentNode = null;
		int currentLevel = 0;
		if (parentNode == null) {
			document = DocumentHelper.createDocument();
			parentNode = document.addElement("message");
			parentNode.addAttribute("id", tree.getId());
			parentNode.addAttribute("name", tree.getName());
			parentNode.addAttribute("layout",layout);
			parentNode.addAttribute("type",tree.getType());
			if (tree.getScore() != null)
				parentNode.addAttribute("score", tree.getScore());
			
		}
		
		if(parentNode.attributeValue("val") == null){
			totalFlow = 0;
			for (Tree temp:tree.getSubTree()) {
				double val = temp.getVal() == null ? 0: Double.parseDouble(temp.getVal());
				totalFlow = totalFlow+ val;
			}
			parentNode.addAttribute("val", Utils.valueOf(totalFlow)+"GB");
		}
		
//System.out.println("totalFlow"+totalFlow);
		for (Tree tempTree:tree.getSubTree()) {
			//System.out.println("name"+tempTree.getName()+","+"val"+tempTree.getVal());
			currentLevel = Integer.valueOf(tempTree.getLev());
			currentNode = parentNode.addElement("message");
			currentNode.addAttribute("id", tempTree.getId());
			currentNode.addAttribute("name", tempTree.getName());
			currentNode.addAttribute("layout",layout);
			currentNode.addAttribute("type",tempTree.getType());
			if (tempTree.getVal() != null){
//				if(tempTree.getName().equals("省内资源")){
//					currentNode.addAttribute("val", "无数据源");
//				} else {
					currentNode.addAttribute("val", Utils.valueOf(tempTree.getVal())+"GB");
//				}
				String parent_val = parentNode.attributeValue("val");
//				if(!tempTree.getName().equals("省内资源")){
					if(parent_val == null) {
						currentNode.addAttribute("zhanbi", Utils.valueOf(Utils.div(Double.parseDouble(tempTree.getVal())*100, totalFlow,2))+"%");
					} else {
						if(parent_val.contains("GB")) {
//							currentNode.addAttribute("zhanbi", Utils.valueOf(Utils.div(Double.parseDouble(tempTree.getVal())*100, Double.parseDouble(parent_val.replace("GB", "")),2))+"%");
							currentNode.addAttribute("zhanbi", Utils.valueOf(Utils.div(Double.parseDouble(tempTree.getVal())*100, totalFlow, 2))+"%");
						}
					}
//				}
				
				
			}
			
			if (tempTree.getScore() != null){
//				if(tempTree.getName().equals("省内资源")){
//					//currentNode.addAttribute("score","无数据源");
//				}else {
					currentNode.addAttribute("score", Utils.valueOf(tempTree.getScore())+"%");
				
//				}
			}
			if (tempTree.hasSubTree()) {
				createXmlWithLayout(tempTree, currentNode, layout);
			}	
		}
//		System.out.println(document.asXML());
		return document.asXML();
	}
	/** 创建树  */
	public static Tree buildTree(Map<String, Tree> treeMap) {
		Tree rootTree = new Tree(), tempTree = null;
		for (Map.Entry<String, Tree> entry : treeMap.entrySet()) {
			tempTree = entry.getValue();
			if ("-1".equals(tempTree.getParentid())) {   
				rootTree = tempTree;   
			} else {   
				treeMap.get(tempTree.getParentid()).addSubTree(tempTree);   
			}   
		}
		return rootTree;
	}
	
	
	/** 创建树  */
	public static FlowExitTree buildFlowExitTree(Map<String, FlowExitTree> treeMap) {
		FlowExitTree rootTree = new FlowExitTree(), tempTree = null;
		for (Map.Entry<String, FlowExitTree> entry : treeMap.entrySet()) {
			tempTree = entry.getValue();
			if ("-1".equals(tempTree.getParentid())) {   
				rootTree = tempTree;   
			} else {   
				treeMap.get(tempTree.getParentid()).addSubTree(tempTree);   
			}   
		}
		
		rootTree.sortTree();
		return rootTree;
	}
	
	/** 获取树的最大层级 */
	public static String getMaxlevel(List<Tree> list) {
		int maxLevel = 0, currentLevel = 0;
		for (Tree tempTree : list) {
			currentLevel = Integer.valueOf(tempTree.getLev());
			if (currentLevel > maxLevel) maxLevel = currentLevel;
				
		}
		return String.valueOf(maxLevel);
	}	
}
