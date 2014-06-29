package org.jbpm.console.ng.documents.backend.server;

import java.io.InputStream;
import java.util.List;

import org.jbpm.console.ng.dm.model.CMSContentSummary;

public interface DocumentService {

	List<CMSContentSummary> getChildren(String id);
	
	CMSContentSummary getDocument(String id);
	
	InputStream getDocumentContent(String id);

	void removeDocument(String id);

}
