package com.seasy.commons.freemarker;

public interface TemplateRender {
	public String render(Object dataModel, String template) throws Exception;
}
