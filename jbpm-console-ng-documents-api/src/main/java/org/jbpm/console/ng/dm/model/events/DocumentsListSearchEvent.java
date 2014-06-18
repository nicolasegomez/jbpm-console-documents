package org.jbpm.console.ng.dm.model.events;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DocumentsListSearchEvent {
    private String filter;
    
    private String type;

    public DocumentsListSearchEvent() {
    }

    public DocumentsListSearchEvent(String filter, String type) {
        this.filter = filter;
        this.type = type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
    
    public void setType(String type) {
		this.type = type;
	}
    
    public String getType() {
		return type;
	}
    
}
