package com.gccpod1.bentleybridges;

public class WidgetItem {
	
	String code = null;
	String content = null;
	boolean selected = false;

	public WidgetItem(String code, String content, boolean selected) {
		super();
		this.code = code;
		this.content = content;
		this.selected = selected;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
