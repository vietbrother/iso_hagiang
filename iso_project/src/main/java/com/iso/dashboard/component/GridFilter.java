/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.component;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.TextField;

/**
 *
 * @author TUYENNV1
 */
public class GridFilter extends Grid{
    //Method to add a filter on grid
    public void setFilterGrid(BeanItemContainer<?> beanType) {
        //This create a HeaderRow to add filter fields
        HeaderRow filterRow = this.appendHeaderRow();
        for (Column column : getColumns()) {
            //For each column from the grid
            HeaderCell cellFilter = filterRow.getCell(column.getPropertyId());
            //Add a textfield
            cellFilter.setComponent(createFieldFilter(beanType, column));       
        }       
    } 

   //This create a TextField to filter the information 
   private TextField createFieldFilter(final BeanItemContainer<?> container, final Column column) {
       TextField filter = new TextField();
       filter.setImmediate(true);
       filter.addTextChangeListener(new TextChangeListener(){   
           @Override
           public void textChange(TextChangeEvent event) {
               String newValue = event.getText();
               //Remove the previous filter
               container.removeContainerFilters(column.getPropertyId());
               if (newValue != null && !newValue.isEmpty()) {
                    //Filter the information
                    container.addContainerFilter(new SimpleStringFilter(column.getPropertyId(), newValue, true, false));
                }
                recalculateColumnWidths();
           }
       });     
       return filter;
   }  
}
