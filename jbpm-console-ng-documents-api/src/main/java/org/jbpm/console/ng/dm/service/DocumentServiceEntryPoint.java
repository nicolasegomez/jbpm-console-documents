package org.jbpm.console.ng.dm.service;

import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;
import org.jbpm.console.ng.dm.model.CMSContentSummary;
import org.jbpm.console.ng.dm.model.DocumentSummary;

@Remote
public interface DocumentServiceEntryPoint {

	public List<CMSContentSummary> getDocuments(String path);
	

	public CMSContentSummary getDocument(String id);

	public void removeDocument(String id);
	

	void addDocument(DocumentSummary doc);


}