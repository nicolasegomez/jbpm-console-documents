package org.jbpm.console.ng.documents.backend.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.bindings.spi.webservices.CXFPortProvider;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.jboss.errai.bus.server.annotations.Service;
import org.jbpm.console.ng.dm.model.CMSContentSummary;
import org.jbpm.console.ng.dm.model.CMSContentSummary.ContentType;
import org.jbpm.console.ng.dm.service.DocumentServiceEntryPoint;

@Service
@ApplicationScoped
public class DocumentServiceEntryPointImpl implements DocumentServiceEntryPoint {

	@Override
	public List<CMSContentSummary> getDocuments(String id) {

		Session session = this.createSession();
		Folder folder = null;
		if (id == null || id.isEmpty()) {
			folder = session.getRootFolder();
		}
		else {
			folder = (Folder)session.getObject(id);
		}
		ItemIterable<CmisObject> children = folder.getChildren();
		Iterator<CmisObject> childrenItems = children.iterator();
		List<CMSContentSummary> documents = new ArrayList<CMSContentSummary>();
		while(childrenItems.hasNext()) {
		    CmisObject item = childrenItems.next();
		    CMSContentSummary doc = null;
		    if (((ObjectType)item.getType()).getId().equals("cmis:folder") ){
		    	doc = new CMSContentSummary(item.getName(), item.getId(), ContentType.FOLDER);
		    }
		    else {
		    	doc = new CMSContentSummary(item.getName(), item.getId(), ContentType.DOCUMENT);
		    }
		    documents.add(doc);
		}
		return documents;
	}
	
	private Session createSession() {
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, "superuser");
		parameter.put(SessionParameter.PASSWORD, "superuser");

		// connection settings
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value());
		parameter.put(SessionParameter.WEBSERVICES_ACL_SERVICE, "http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/ACLService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE, "http://<host>:<port>/cmis/services/DiscoveryService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE, "http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/MultiFilingService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE, "http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/NavigationService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE, "http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/ObjectService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_POLICY_SERVICE, "http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/PolicyService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE, "http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/RelationshipService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE, "http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/RepositoryService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE, "http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/VersioningService?wsdl");
		parameter.put(SessionParameter.REPOSITORY_ID, "dms");
		parameter.put(SessionParameter.WEBSERVICES_PORT_PROVIDER_CLASS, CXFPortProvider.class.getName());
		
		// create session
		Session session = factory.createSession(parameter);
		return session;
	}
}
