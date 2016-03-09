package com.ultrapower.flowmanage.common.vo;

import com.lowagie.text.Font;

public class TableContent {
	private String content;
	private Font font;
	private int align;
	private int rowSpan = 0;
	private int cosSpan = 0;

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public int getCosSpan() {
		return cosSpan;
	}

	public void setCosSpan(int cosSpan) {
		this.cosSpan = cosSpan;
	}

	public TableContent() {
	}

	public TableContent(String content, Font font, int align) {
		this.content = content;
		this.font = font;
		this.align = align;
	}

	public TableContent(String content, Font font, int align, int rowSpan,
			int cosSpan) {
		super();
		this.content = content;
		this.font = font;
		this.align = align;
		this.rowSpan = rowSpan;
		this.cosSpan = cosSpan;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public int getAlign() {
		return align;
	}
	
	public void setAlign(int align) {
		this.align = align;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + align;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + cosSpan;
		result = prime * result + ((font == null) ? 0 : font.hashCode());
		result = prime * result + rowSpan;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableContent other = (TableContent) obj;
		if (align != other.align)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (cosSpan != other.cosSpan)
			return false;
		if (font == null) {
			if (other.font != null)
				return false;
		} else if (!font.equals(other.font))
			return false;
		if (rowSpan != other.rowSpan)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TableContent [align=" + align + ", content=" + content
				+ ", cosSpan=" + cosSpan + ", font=" + font + ", rowSpan="
				+ rowSpan + "]";
	}
}
