package com.ultrapower.flowmanage.modules.health.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.ultrapower.flowmanage.common.vo.TableContent;

public class WordDocUtil {
	/**
	 * 仿宋, 二号, 黑色, 粗体
	 */
	public static Font f = setFontStyle("仿宋", new Color(0, 0, 0), 22, Font.BOLD);
	/**
	 * 宋体, 11, 黑色
	 */
	public static Font f1 = setFontStyle("宋体", new Color(0, 0, 0), 11, Font.NORMAL);
	/**
	 * 宋体, 11, 红色
	 */
	public static Font f2 = setFontStyle("宋体", new Color(255, 0, 0), 11, Font.NORMAL);
	/**
	 * 仿宋, 四号, 黑色
	 */
	public static Font f3 = setFontStyle("仿宋", new Color(0, 0, 0), 14, Font.NORMAL);
	/**
	 * 仿宋, 10, 黑色, 粗体
	 */
	public static Font f4 = setFontStyle("仿宋", new Color(0, 0, 0), 10, Font.BOLD);
	/**
	 * 仿宋, 10, 黑色
	 */
	public static Font f5 = setFontStyle("仿宋", new Color(0, 0, 0), 10, Font.NORMAL);
	/**
	 * 仿宋, 小四, 黑色
	 */
	public static Font f6 = setFontStyle("仿宋", new Color(0, 0, 0), 12, Font.NORMAL);
	/**
	 * 仿宋, 小四, 黑色, 粗体
	 */
	public static Font f7 = setFontStyle("仿宋", new Color(0, 0, 0), 12, Font.BOLD);
	/**
	 * 仿宋, 小四, 红色
	 */
	public static Font f8 = setFontStyle("仿宋", new Color(255, 0, 0), 12, Font.NORMAL);
	
	
	private static final HashMap<String, BaseFont> fontsMap = new HashMap<String, BaseFont>();
	private static BaseFont fsFont;
	private static BaseFont stFont;
	
	static
	{
		try {
			fsFont = BaseFont.createFont("fonts/SIMFANG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			stFont = BaseFont.createFont("fonts/SIMSUN.TTC,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fontsMap.put("宋体", stFont);
		fontsMap.put("仿宋", fsFont);
	}
	 /** 
     * 功能说明：设置字体的样式，颜色为黑色</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param family  字体类型 
     * @param size    字体大小，22f为二号，18f为小二号，16f为三号 
     * @param style   字体样式 
     * @return 
     */  
    public static Font setFontStyle(String family , float size , int style){  
        return setFontStyle(family, Color.BLACK, size, style);
    }

    /** 
     * 功能说明：设置字体的样式</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param family  字体类型 
     * @param color   字体颜色 
     * @param size    字体大小，22f为二号，18f为小二号，16f为三号 
     * @param style   字体样式 
     * @return 
     */  
    public static Font setFontStyle(String family , Color color , float size , int style){  
//    	try {
//			BaseFont bfFont = BaseFont.createFont(fontsMap.get(family), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

	        Font font = new Font(fsFont);
//	        Font font = new Font();
//	        font.setFamily(family);
	        font.setColor(color);
	        font.setSize(size);
	        font.setStyle(style);
	        return font;
	        
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
    }
    
    /** 
     * 功能说明：为文字填充浅灰色背景</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content   需要填充背景颜色的内容 
     * @param appendStr 不需要填充背景颜色的内容 
     * @return 
     */  
    private static Phrase setPhraseStyle(String content , String appendStr){  
        Chunk chunk = new Chunk(content);
        //填充的背景颜色为浅灰色  
        chunk.setBackground(Color.LIGHT_GRAY);
        Phrase phrase = new Phrase(chunk);
        phrase.add(appendStr);
        return phrase;
    }

    /** 
     * 功能说明：设置段落的样式，设置前半截内容和后半截内容格式不一样的段落样式</BR> 
     * 修改日：2011-04-27 
     * @author myclover 
     * @param content  前半截内容 
     * @param font     字体的样式 
     * @param firstLineIndent 首行缩进多少字符，16f约等于一个字符 
     * @param appendStr 后半截内容 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , String appendStr){  
        Paragraph par = setParagraphStyle(content, font, 0f, 12f);
        Phrase phrase = new Phrase();
        phrase.add(par);
        phrase.add(appendStr);
        Paragraph paragraph = new Paragraph(phrase);
        paragraph.setFirstLineIndent(firstLineIndent);
        //设置对齐方式为两端对齐  
        paragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);
        return paragraph;
    }

    /** 
     * 功能说明：设置段落的样式，设置前半截内容填充了浅灰色的背景颜色，后半截内容没有背景颜色的段落样式</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content  前半截有背景颜色的内容 
     * @param font     字体的样式 
     * @param firstLineIndent 首行缩进的字符，16f约等于一个字符 
     * @param leading  行间距12f表示单倍行距 
     * @param appendStr 后半截内容 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading , String appendStr){  
        Phrase phrase = setPhraseStyle(content , appendStr);
        Paragraph par = new Paragraph(phrase);
        par.setFont(font);
        par.setFirstLineIndent(firstLineIndent);
        par.setLeading(leading);
        //设置对齐方式为两端对齐  
        par.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);
        return par;
    }

    /** 
     * 功能说明：设置段落的样式，一般用于设置标题</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content  段落的内容 
     * @param font     字体样式 
     * @param leading  行间距 
     * @param alignment 对齐方式 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float leading , int alignment){  
        return setParagraphStyle(content, font, 0f, leading, 0f, alignment);
    }

    /** 
     * 功能说明：设置段落的样式，对齐方式为两端对齐，缩进样式是文本之后0.2毫米</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content  段落的内容 
     * @param font     字体的样式 
     * @param firstLineIndent 首行缩进多少字符，16f约等于一个字符 
     * @param leading  行间距 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading){  
        return setParagraphStyle(content, font, firstLineIndent, leading, 0.6f, Paragraph.ALIGN_JUSTIFIED_ALL);
    }

    /** 
     * 功能说明：设置段落的样式</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content  段落的内容 
     * @param font     字体的样式 
     * @param firstLineIndent  首行缩进多少字符，16f约等于一个字符 
     * @param leading  行间距 
     * @param indentationRight 缩进样式中的文本之后多少毫米，0.6f相当于0.2毫米 
     * @param alignment 对齐方式 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading , float indentationRight , int alignment){  
        Paragraph par = new Paragraph(content, font);
        par.setFirstLineIndent(firstLineIndent);
        par.setLeading(leading);
        par.setIndentationRight(indentationRight);
        par.setAlignment(alignment);
        return par;
    }
    

    /** 
     * 功能说明：设置段落的样式</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param par  段落
     * @param firstLineIndent  首行缩进多少字符，16f约等于一个字符 
     * @param leading  行间距 
     * @param indentationRight 缩进样式中的文本之后多少毫米，0.6f相当于0.2毫米 
     * @param alignment 对齐方式 
     * @return 
     */  
    public static Paragraph setParagraphStyle(Paragraph par , float firstLineIndent , float leading , float indentationRight , int alignment){  
        par.setFirstLineIndent(firstLineIndent);
        par.setLeading(leading);
        par.setIndentationRight(indentationRight);
        par.setAlignment(alignment);
        return par;
    }
    

	/** 
     * 功能说明：WORD文档增加表格通用方法</BR> 
     * 修改日期：2014-07-17 
     * @author zouhaibo 
     * @param document       文档 
     * @param tableWidth     表格宽度 
     * @param totalRow       总列数 
     * @param contents       内容列表（含内容、样式、对齐方式、列合并单元格数、行合并单元格数） 
     * @return 
     */  
	public static void addTable(Document document, int tableWidth, int totalRow, List<TableContent> contents)
	{
		if(document == null)
		{
			throw new RuntimeException("文档未生成");
		}
		if(totalRow <=0)
		{
			throw new RuntimeException("表格列数错误");
		}
		
		int rowNum = 0;
		
		try {
			Table table = new Table(totalRow);
			table.setBorderColor(Color.BLACK);
			table.setPadding(0);
			table.setSpacing(0);
			if(tableWidth > 0)
			{
				table.setWidth(tableWidth);
			}
			
			for(TableContent content: contents)
			{
//				System.out.println("content: " + content.toString());
				//代表生成第一行
				if(rowNum == 0 && rowNum < totalRow)
				{
//					System.out.println("In header");
					Cell header = new Cell(WordDocUtil.setParagraphStyle(content.getContent(), content.getFont(),
							0, content.getAlign()));// 单元格
					header.setHeader(true);
					header.setHorizontalAlignment(1);
					table.addCell(header);
				}
				else
				{
//					System.out.println("In content");
					if(content.getCosSpan() == 0 && content.getRowSpan() == 0)
					{
						table.addCell(WordDocUtil.setParagraphStyle(content.getContent(), content.getFont(),
								0, content.getAlign()));
					}else
					{
//						System.out.println("In span");
						Cell cell = new Cell(WordDocUtil.setParagraphStyle(content.getContent(),
								content.getFont(), 0, content.getAlign()));
						if(content.getRowSpan() > 0)
						{
							cell.setRowspan(content.getRowSpan());
						}
						if(content.getCosSpan() >0)
						{
							cell.setColspan(content.getCosSpan());
						}
						cell.setBorderWidth(0);
						cell.setHorizontalAlignment(1);
						cell.setVerticalAlignment(1);
						table.addCell(cell);
					}
				}
				rowNum++;
			}
			
			document.add(table);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("1");
		WordDocUtil.setFontStyle("仿宋", new Color(0, 0, 0), 22, Font.BOLD);
		System.out.println("2");
	}
}
