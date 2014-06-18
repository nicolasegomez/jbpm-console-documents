package org.jbpm.console.ng.dm.model.events;

import java.util.Date;
import java.util.Map;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jbpm.document.Document;

@Portable
public class Folder implements Document {

	private String name;
	
	private String id;

	public Folder(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void setIdentifier(String identifier) {
		this.id = identifier;
	}

	@Override
	public String getIdentifier() {
		return this.id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setSize(long size) {
		
	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastModified(Date lastModified) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getLastModified() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLink(String link) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttribute(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAttribute(String attributeName, String attributeValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttributes(Map<String, String> attributes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContent(byte[] content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getContent() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
