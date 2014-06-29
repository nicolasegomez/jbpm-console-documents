package org.jbpm.console.ng.documents.backend.server;

import java.io.InputStream;
import java.util.List;

import org.jbpm.console.ng.dm.model.CMSContentSummary;

public interface DocumentService {

	public abstract List<CMSContentSummary> getChildren(String id);

	public abstract InputStream getDocumentContent(String id);
	
	public abstract CMSContentSummary getDocument(String id);
	
}
