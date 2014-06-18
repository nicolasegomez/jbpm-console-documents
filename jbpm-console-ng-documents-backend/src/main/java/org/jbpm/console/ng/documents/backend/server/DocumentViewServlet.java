package org.jbpm.console.ng.documents.backend.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.commons.data.ContentStream;

public class DocumentViewServlet extends HttpServlet {

	@Inject
	private CMISFacade cmisService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		ContentStream stream = cmisService.getDocumentContent(req.getParameter("documentId"));
		OutputStream out = response.getOutputStream();
		InputStream in = stream.getStream();
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
}
