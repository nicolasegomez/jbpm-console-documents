package org.jbpm.console.ng.documents.service;

import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;
import org.jbpm.console.ng.documents.model.DocumentSummary;

@Remote
public interface DocumentServiceEntryPoint {

	public List<DocumentSummary> getDocuments();
    
}