package org.jbpm.console.ng.documents.backend.server;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.jboss.errai.bus.server.annotations.Service;
import org.jbpm.console.ng.dm.model.CMSContentSummary;
import org.jbpm.console.ng.dm.model.CMSContentSummary.ContentType;
import org.jbpm.console.ng.dm.service.DocumentServiceEntryPoint;

@Service
@ApplicationScoped
public class DocumentServiceEntryPointImpl implements DocumentServiceEntryPoint {

	@Inject
	private CMISFacade cmisService;
	
	@Override
	public List<CMSContentSummary> getDocuments(String id) {
		List<CmisObject> children = this.cmisService.getChildren(id);
		List<CMSContentSummary> documents = new ArrayList<CMSContentSummary>();
		for (CmisObject item : children) {
			 CMSContentSummary doc = null;
			if (((ObjectType)item.getType()).getId().equals("cmis:folder") ){
		    	doc = new CMSContentSummary(item.getName(), item.getId(), ContentType.FOLDER);
		    }
		    else {
		    	doc = new CMSContentSummary(item.getName(), item.getId(), ContentType.DOCUMENT);
		    	Folder parent = ((Document)item).getParents().get(0); //for now, assume it only has one parent.
		    	doc.setParent(new CMSContentSummary(parent.getName(), parent.getId(), ContentType.FOLDER));
		    }
		    documents.add(doc);
		}
		return documents;
	}
}
