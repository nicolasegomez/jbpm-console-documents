/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.console.ng.dm.client.experimental.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jbpm.console.ng.dm.model.DocumentSummary;
import org.jbpm.console.ng.dm.service.DocumentServiceEntryPoint;
import org.jbpm.console.ng.gc.client.i18n.Constants;
import org.uberfire.client.annotations.WorkbenchMenu;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.UberView;
import org.uberfire.lifecycle.OnFocus;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.Menus;

import com.github.gwtbootstrap.client.ui.DataGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

@Dependent
@WorkbenchScreen(identifier = "Documents Presenter")
public class DocumentListPresenter {

	public interface DocumentListView extends UberView<DocumentListPresenter> {

		void displayNotification(String text);

		String getCurrentFilter();

		void setCurrentFilter(String filter);

		DataGrid<DocumentSummary> getDataGrid();

		void showBusyIndicator(String message);

		void hideBusyIndicator();
	}

	private Menus menus;

	@Inject
	private DocumentListView view;

	@Inject
	private Caller<DocumentServiceEntryPoint> dataServices;

	List<DocumentSummary> currentDocuments = null;

	private ListDataProvider<DocumentSummary> dataProvider = new ListDataProvider<DocumentSummary>();

	private Constants constants = GWT.create(Constants.class);

	@WorkbenchPartTitle
	public String getTitle() {
		return "Documents";
	}

	@WorkbenchPartView
	public UberView<DocumentListPresenter> getView() {
		return view;
	}

	public DocumentListPresenter() {
		makeMenuBar();
	}

	public void refreshDocumentList() {
		dataServices.call(new RemoteCallback<List<DocumentSummary>>() {
			@Override
			public void callback(List<DocumentSummary> documents) {
				currentDocuments = documents;
				filterProcessList(view.getCurrentFilter());
			}
		}).getDocuments();
	}

	public void filterProcessList(String filter) {

		dataProvider.getList().clear();
		dataProvider.getList().addAll(
				new ArrayList<DocumentSummary>(currentDocuments));
		dataProvider.refresh();

	}

	public void addDataDisplay(HasData<DocumentSummary> display) {
		dataProvider.addDataDisplay(display);
	}

	public ListDataProvider<DocumentSummary> getDataProvider() {
		return dataProvider;
	}

	public void refreshData() {
		dataProvider.refresh();
	}

	@OnOpen
	public void onOpen() {
		refreshDocumentList();
	}

	@OnFocus
	public void onFocus() {
		refreshDocumentList();
	}

	@WorkbenchMenu
	public Menus getMenus() {
		return menus;
	}

	private void makeMenuBar() {
		menus = MenuFactory.newTopLevelMenu(constants.Refresh())
				.respondsWith(new Command() {
					@Override
					public void execute() {
						refreshDocumentList();
						view.setCurrentFilter("");
						view.displayNotification("Refresh complete.");
					}
				}).endMenu().build();

	}

}
