/*
 * Copyright 2013 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.console.ng.dm.client.experimental.pagination;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jbpm.console.ng.dm.client.i18n.Constants;
import org.jbpm.console.ng.dm.model.DocumentSummary;
import org.uberfire.workbench.events.NotificationEvent;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;

@Dependent
@Templated(value = "NewDocumentViewImpl.html")
public class NewDocumentViewImpl extends Composite implements
		NewDocumentPresenter.NewDocumentView {

	private Constants constants = GWT.create(Constants.class);

	@Inject
	@DataField
	public TextBox documentNameText;

	@Inject
	@DataField
	public Label documentNameLabel;

	@Inject
	@DataField
	public TextBox documentFolderText;

	@Inject
	@DataField
	public Label documentFolderLabel;

	@Inject
	@DataField
	public Label newDocTypeLabel;

	@Inject
	@DataField
	public Button createButton;

	@Inject
	@DataField
	public ListBox newDocType;

	@Inject
	Event<NotificationEvent> notificationEvents;

	private NewDocumentPresenter presenter;

	@Override
	public void init(NewDocumentPresenter p) {
		this.presenter = p;

		createButton.setText("Create");
		createButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String type = newDocType.getValue();
				if ("Text File".equals(type)) {
					DocumentSummary doc = new DocumentSummary(documentNameText
							.getText() + ".txt", null, documentFolderText.getValue());
					doc.setContent("test".getBytes());
					presenter.createDocument(doc);
				}
			}
		});
		newDocTypeLabel.setText("File Type");
		documentNameLabel.setText("Document Name");
		documentFolderLabel.setText("Document Folder");
		newDocType.addItem("Text File");
		newDocType.addItem("PDF");
	}

	@Override
	public void displayNotification(String notification) {
		notificationEvents.fire(new NotificationEvent(notification));
	}

	@Override
	public void setFolder(String folder) {
		documentFolderText.setText(folder);
		documentFolderText.setEnabled(false);
	}
	// @Override
	// public Focusable getJobNameText() {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
