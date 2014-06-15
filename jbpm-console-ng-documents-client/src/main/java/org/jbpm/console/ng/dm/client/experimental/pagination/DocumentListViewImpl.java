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

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jbpm.console.ng.dm.model.DocumentSummary;
import org.jbpm.console.ng.gc.client.util.DataGridUtils;
import org.jbpm.console.ng.gc.client.util.ResizableHeader;
import org.uberfire.client.common.BusyPopup;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.mvp.PlaceStatus;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.security.Identity;
import org.uberfire.workbench.events.NotificationEvent;

import com.github.gwtbootstrap.client.ui.DataGrid;
import com.github.gwtbootstrap.client.ui.SimplePager;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;

@Dependent
@Templated(value = "DocumentListViewImpl.html")
public class DocumentListViewImpl extends Composite
        implements DocumentListPresenter.DocumentListView,
        RequiresResize {


    @Inject
    private Identity identity;

    @Inject
    private PlaceManager placeManager;

    private DocumentListPresenter presenter;

    private String currentFilter = "";

    @Inject
    @DataField
    public DataGrid<DocumentSummary> processdefListGrid;

    @Inject
    @DataField
    public LayoutPanel listContainer;

    @DataField
    public SimplePager pager;


    @Inject
    private Event<NotificationEvent> notification;

    
    private ListHandler<DocumentSummary> sortHandler;

    public DocumentListViewImpl() {
        pager = new SimplePager(SimplePager.TextLocation.LEFT, false, true);
    }

    @Override
    public String getCurrentFilter() {
        return currentFilter;
    }

    @Override
    public void setCurrentFilter(String currentFilter) {
        this.currentFilter = currentFilter;
    }

    @Override
    public void init(final DocumentListPresenter presenter) {
        this.presenter = presenter;

        listContainer.add(processdefListGrid);
        pager.setDisplay(processdefListGrid);
        pager.setPageSize(10);

        // Set the message to display when the table is empty.
        Label emptyTable = new Label("Empty");
        emptyTable.setStyleName("");
        processdefListGrid.setEmptyTableWidget(emptyTable);

        // Attach a column sort handler to the ListDataProvider to sort the list.
        sortHandler = new ListHandler<DocumentSummary>(presenter.getDataProvider().getList());
        processdefListGrid.addColumnSortHandler(sortHandler);

        // Add a selection model so we can select cells.
        final MultiSelectionModel<DocumentSummary> selectionModel = new MultiSelectionModel<DocumentSummary>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
//                selectedProcessDef = selectionModel.getSelectedSet();
//                for (ProcessSummary pd : selectedProcessDef) {
//                    processSelection.fire(new ProcessDefSelectionEvent(pd.getId()));
//                }
            }
        });

        processdefListGrid.setSelectionModel(selectionModel,
                DefaultSelectionEventManager.<DocumentSummary>createCheckboxManager());

        initTableColumns(selectionModel);

        presenter.addDataDisplay(processdefListGrid);

    }

    private void initTableColumns(final SelectionModel<DocumentSummary> selectionModel) {

        processdefListGrid.addCellPreviewHandler(new CellPreviewEvent.Handler<DocumentSummary>() {

            @Override
            public void onCellPreview(final CellPreviewEvent<DocumentSummary> event) {
//            	DocumentSummary process = null;
//                if (BrowserEvents.CLICK.equalsIgnoreCase(event.getNativeEvent().getType())) {
//                    int column = event.getColumn();
//                    int columnCount = processdefListGrid.getColumnCount();
//                    if (column != columnCount - 1) {
//                        PlaceStatus instanceDetailsStatus = placeManager.getStatus(new DefaultPlaceRequest("Process Instance Details"));
//                        if(instanceDetailsStatus == PlaceStatus.OPEN){
//                            placeManager.closePlace("Process Instance Details");
//                        }
//                        process = event.getValue();
//                        placeManager.goTo("Process Definition Details");
//                        processDefSelected.fire(new ProcessDefSelectionEvent(process.getId(), process.getDeploymentId()));
//                    }
//                }
//
//                if (BrowserEvents.FOCUS.equalsIgnoreCase(event.getNativeEvent().getType())) {
//                    if (DataGridUtils.newProcessDefName != null) {
//                        changeRowSelected(new ProcessDefStyleEvent(DataGridUtils.newProcessDefName, DataGridUtils.newProcessDefVersion));
//                    }
//                }

            }
        });

        // Process Name String.
        Column<DocumentSummary, String> processNameColumn = new Column<DocumentSummary, String>(new TextCell()) {
            @Override
            public String getValue(DocumentSummary object) {
                return object.getName();
            }
        };
        processNameColumn.setSortable(true);
        sortHandler.setComparator(processNameColumn, new Comparator<DocumentSummary>() {
            @Override
            public int compare(DocumentSummary o1,
            		DocumentSummary o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        
        processdefListGrid.addColumn(processNameColumn, new ResizableHeader("Name", processdefListGrid,
                processNameColumn));

        // Version Type
        Column<DocumentSummary, String> idColumn = new Column<DocumentSummary, String>(new TextCell()) {
            @Override
            public String getValue(DocumentSummary object) {
                return object.getId();
            }
        };
        
        processdefListGrid.addColumn(idColumn, new ResizableHeader("ID", processdefListGrid,
        		idColumn));
       
    }

    @Override
    public void onResize() {
        if ((getParent().getOffsetHeight() - 120) > 0) {
            listContainer.setHeight(getParent().getOffsetHeight() - 120 + "px");
        }
    }
//
//    public void changeRowSelected(@Observes ProcessDefStyleEvent processDefStyleEvent) {
//        if (processDefStyleEvent.getProcessDefName() != null) {
//            DataGridUtils.paintRowSelected(processdefListGrid,
//                    processDefStyleEvent.getProcessDefName(), processDefStyleEvent.getProcessDefVersion());
//        }
//    }

    @Override
    public void displayNotification(String text) {
        notification.fire(new NotificationEvent(text));
    }

    @Override
    public DataGrid<DocumentSummary> getDataGrid() {
        return processdefListGrid;
    }

    public ListHandler<DocumentSummary> getSortHandler() {
        return sortHandler;
    }

    @Override
    public void showBusyIndicator(final String message) {
        BusyPopup.showMessage(message);
    }

    @Override
    public void hideBusyIndicator() {
        BusyPopup.close();
    }

}
