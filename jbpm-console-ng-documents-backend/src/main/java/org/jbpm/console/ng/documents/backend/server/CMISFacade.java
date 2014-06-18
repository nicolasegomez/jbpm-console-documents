package org.jbpm.console.ng.documents.backend.server;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.commons.data.ContentStream;

public interface CMISFacade {

	public abstract List<CmisObject> getChildren(String id);

	public abstract ContentStream getDocumentContent(String id);

}