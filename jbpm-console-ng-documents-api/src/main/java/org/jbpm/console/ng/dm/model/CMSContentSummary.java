package org.jbpm.console.ng.dm.model;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class CMSContentSummary {

	private String name;
	
	private String id;
	
	private CMSContentSummary parent;
	
	private ContentType contentType;
	
	public CMSContentSummary(String name, String id, ContentType contentType) {
		super();
		this.name = name;
		this.id = id;
		this.contentType = contentType;
	}

	public CMSContentSummary() {
	}
	
	public ContentType getContentType() {
		return contentType;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setParent(CMSContentSummary parent) {
		this.parent = parent;
	}
	
	public CMSContentSummary getParent() {
		return parent;
	}
	
	public static enum ContentType {
		DOCUMENT,
		FOLDER;
	}
}
