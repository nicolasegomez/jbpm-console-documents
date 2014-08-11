package org.jbpm.console.ng.documents.backend.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.errai.common.client.api.RemoteCallback;
import org.jbpm.console.ng.dm.model.events.NewDocumentEvent;

public class DocumentViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3950781302033089580L;

	@Inject
	private DocumentService documentService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		InputStream in = this.documentService.getDocumentContent(req.getParameter("documentId"));
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0){
		    out.write(buffer, 0, length);
		}
		String documentName = req.getParameter("documentId");
		response.setHeader("Content-disposition","attachment; filename=" + documentName);

		in.close();
		out.flush();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		documentService.createDocument(doc);
		
		resp.setContentType( "text/html" );
		resp.getWriter().write( "OK" );
	}
	
}
