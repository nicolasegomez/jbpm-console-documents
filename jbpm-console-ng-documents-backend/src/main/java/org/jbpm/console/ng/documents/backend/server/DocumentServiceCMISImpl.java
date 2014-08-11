package org.jbpm.console.ng.documents.backend.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.bindings.spi.webservices.SunRIPortProvider;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.MimeTypes;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.jboss.errai.bus.server.annotations.Service;
import org.jbpm.console.ng.dm.model.CMSContentSummary;
import org.jbpm.console.ng.dm.model.DocumentSummary;
import org.jbpm.console.ng.dm.model.FolderSummary;

@Service
@ApplicationScoped
public class DocumentServiceCMISImpl implements DocumentService {

	private Session session;

	private Session createSession() {
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, "superuser");
		parameter.put(SessionParameter.PASSWORD, "superuser");

		// connection settings
		parameter.put(SessionParameter.BINDING_TYPE,
				BindingType.WEBSERVICES.value());
		parameter
				.put(SessionParameter.WEBSERVICES_ACL_SERVICE,
						"http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/ACLService?wsdl");
		parameter.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE,
				"http://<host>:<port>/cmis/services/DiscoveryService?wsdl");
		parameter
				.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE,
						"http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/MultiFilingService?wsdl");
		parameter
				.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE,
						"http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/NavigationService?wsdl");
		parameter
				.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE,
						"http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/ObjectService?wsdl");
		parameter
				.put(SessionParameter.WEBSERVICES_POLICY_SERVICE,
						"http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/PolicyService?wsdl");
		parameter
				.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE,
						"http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/RelationshipService?wsdl");
		parameter
				.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE,
						"http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/RepositoryService?wsdl");
		parameter
				.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE,
						"http://localhost:8080/magnoliaAuthor/.magnolia/cmisws/VersioningService?wsdl");
		parameter.put(SessionParameter.REPOSITORY_ID, "dms");
		parameter.put(SessionParameter.WEBSERVICES_PORT_PROVIDER_CLASS,
				SunRIPortProvider.class.getName());

		// create session
		Session session = factory.createSession(parameter);
		return session;
	}

	@Override
	public List<CMSContentSummary> getChildren(String id) {
		if (session == null) {
			session = this.createSession();
		}
		Folder folder = null;
		if (id == null || id.isEmpty()) {
			folder = session.getRootFolder();
		} else {
			folder = (Folder) session.getObject(id);
		}
		ItemIterable<CmisObject> children = folder.getChildren();
		Iterator<CmisObject> childrenItems = children.iterator();
		List<CmisObject> documents = new ArrayList<CmisObject>();
		while (childrenItems.hasNext()) {
			CmisObject item = childrenItems.next();
			documents.add(item);
		}
		return this.transform(documents);
	}

	@Override
	public InputStream getDocumentContent(String id) {
		Session session = this.createSession();
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("No id provided");
		}
		Document document = (Document) session.getObject(id);
		if (document == null) {
			throw new IllegalArgumentException(
					"Document with this id does not exist");
		}
		return document.getContentStream().getStream();
	}

	@Override
	public void removeDocument(final String id) {
		if (session == null) {
			session = this.createSession();
		}
		session.delete(new ObjectId() {
			@Override
			public String getId() {
				return id;
			}
		});
	}

	public List<CMSContentSummary> transform(List<CmisObject> children) {
		List<CMSContentSummary> documents = new ArrayList<CMSContentSummary>();
		for (CmisObject item : children) {
			documents.add(transform(item));
		}
		return documents;
	}

	public CMSContentSummary transform(CmisObject object) {
		CMSContentSummary doc = null;
		if (((ObjectType) object.getType()).getId().equals("cmis:folder")) {
			Folder folder = (Folder) object;
			doc = new FolderSummary(object.getName(), object.getId(),
					folder.getPath());
			Folder parent = ((Folder) object).getParents().get(0); // for now,
																	// assume it
																	// only has
																	// one
																	// parent.
			doc.setParent(new FolderSummary(parent.getName(), parent.getId(),
					parent.getPath()));
		} else {
			doc = new DocumentSummary(object.getName(), object.getId(), null);
			Folder parent = ((Document) object).getParents().get(0); // for now,
																		// assume
																		// it
																		// only
																		// has
																		// one
																		// parent.
			doc.setParent(new FolderSummary(parent.getName(), parent.getId(),
					parent.getPath()));
		}
		return doc;
	}

	@Override
	public CMSContentSummary getDocument(String id) {
		if (session == null) {
			session = this.createSession();
		}

		Document document = null;
		if (id != null && !id.isEmpty()) {
			document = (Document) session.getObject(id);
		}

		return this.transform(document);
	}

	@Override
	public void createDocument(DocumentSummary doc) {
		if (session == null) {
			session = this.createSession();
		}
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		properties.put(PropertyIds.NAME, doc.getName());
		InputStream stream = new ByteArrayInputStream(doc.getContent());
		ContentStream contentStream = new ContentStreamImpl(doc.getName(),
				BigInteger.valueOf(doc.getContent().length), MimeTypes.getMIMEType(doc.getName()),
				stream);
		((Folder)this.session.getObjectByPath(doc.getPath())).createDocument(properties, contentStream, VersioningState.NONE);
	}
}
