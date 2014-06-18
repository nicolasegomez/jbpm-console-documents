package org.jbpm.console.ng.dm.service;

import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;
import org.jbpm.console.ng.dm.model.CMSContentSummary;

@Remote
public interface DocumentServiceEntryPoint {

	public List<CMSContentSummary> getDocuments(String path);
    
}