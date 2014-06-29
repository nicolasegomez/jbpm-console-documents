package org.jbpm.console.ng.documents.backend.server;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.jbpm.console.ng.dm.model.CMSContentSummary;
import org.jbpm.console.ng.dm.service.DocumentServiceEntryPoint;

@Service
@ApplicationScoped
public class DocumentServiceEntryPointImpl implements DocumentServiceEntryPoint {

	@Inject
	private DocumentService documentService;
	
	@Override
	public List<CMSContentSummary> getDocuments(String id) {
		return this.documentService.getChildren(id);
	}
}
