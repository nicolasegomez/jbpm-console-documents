/*
 * Copyright 2012 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.console.ng.dm.model;

import java.util.Date;
import java.util.Map;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jbpm.document.Document;

@Portable
public class DocumentSummary extends CMSContentSummary /**implements Document */{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1946063131992320204L;

	public DocumentSummary(String name, String id, String path) {
		super(name, id, path);
	}
	
	public DocumentSummary() {
	}

	@Override
	public ContentType getContentType() {
		return ContentType.DOCUMENT;
	}
//
//	@Override
//	public void setIdentifier(String identifier) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String getIdentifier() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setSize(long size) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public long getSize() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setLastModified(Date lastModified) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Date getLastModified() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setLink(String link) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String getLink() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getAttribute(String attributeName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void addAttribute(String attributeName, String attributeValue) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setAttributes(Map<String, String> attributes) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Map<String, String> getAttributes() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setContent(byte[] content) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public byte[] getContent() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
	
}
