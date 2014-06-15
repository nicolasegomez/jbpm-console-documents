package org.jbpm.console.ng.documents.backend.server;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.errai.bus.server.annotations.Service;
import org.jbpm.console.ng.dm.model.DocumentSummary;
import org.jbpm.console.ng.dm.service.DocumentServiceEntryPoint;

@Service
@ApplicationScoped
public class DocumentServiceEntryPointImpl implements DocumentServiceEntryPoint {

	@Override
	public List<DocumentSummary> getDocuments() {
		List<DocumentSummary> mockedDocs = new ArrayList<DocumentSummary>();
		DocumentSummary doc1 = new DocumentSummary("123", "name1");
		DocumentSummary doc2 = new DocumentSummary("456", "name2");
		mockedDocs.add(doc1);
		mockedDocs.add(doc2);
		return mockedDocs;
	}
}
