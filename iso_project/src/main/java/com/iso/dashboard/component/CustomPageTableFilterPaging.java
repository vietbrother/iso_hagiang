///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.iso.dashboard.component;
//
///**
// *
// * @author TamdX
// */
//
//import com.iso.dashboard.component.event.PagedPagingTableChangeEvent;
//import com.iso.dashboard.utils.BundleUtils;
//import com.iso.dashboard.utils.Constants;
//import com.iso.dashboard.utils.PrintLog;
//import com.vaadin.addon.responsive.Responsive;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.tepi.filtertable.FilterTable;
//
//import com.vaadin.data.Container;
//import com.vaadin.data.Property;
//import com.vaadin.server.Sizeable;
//import com.vaadin.shared.ui.label.ContentMode;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.CssLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.NativeSelect;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.themes.Reindeer;
//import org.tepi.filtertable.paged.PagedFilterTableContainer;
//
//@SuppressWarnings("serial")
//public class CustomPageTableFilterPaging<T extends Container.Indexed & Container.Filterable & Container.ItemSetChangeNotifier>
//        extends FilterTable {
//
//    public interface PagePagingChangeListener extends Serializable {
//
//        public void pageChanged(PagedPagingTableChangeEvent event);
//    }
//
//    public interface PageSelectListener extends Serializable {
//
//        public void pageSelected();
//    }
//
//    private List<PagePagingChangeListener> listeners = null;
//    private List<PageSelectListener> selectListeners = null;
//
//    private PagedFilterTableContainer<T> container;
//    boolean resizePage = true;
//    private String pageSizeDefault;
//    Label separatorTotal = new Label(Constants.EMPTY_CHARACTER, ContentMode.HTML);
////    int currPageLength = 20;
//    int currPageLength = 10;
//    int currPage = 1;
//    int startRow = 0;
//    int totalRow = 0;
//    boolean isFirstTime = true;
//    boolean isInit = true;
//
//    public int getCurrPageLength() {
//        return currPageLength;
//    }
//
//    public void setCurrPageLength(int currPageLength) {
//        this.currPageLength = currPageLength;
//    }
//
//    public CustomPageTableFilterPaging() {
//        this(null);
//    }
//
//    public CustomPageTableFilterPaging(String caption) {
//        super(caption);
//        setPageLength(currPageLength);
//        addStyleName("pagedtable");
//    }
//
//    /**
//     *
//     * @param pageSize yeu cau: Chudv tao bang khong co paging truongbx3
//     * 06/05/2015
//     */
//    public void createControls(int pageSize) {
//        container.setPageLength(pageSize);
//        super.setPageLength(pageSize);
//    }
//
//    public HorizontalLayout createControls(PagedFilterControlConfigDefaul config, final String pageSizeDefaul) {
//        pageSizeDefault = pageSizeDefaul;
//        separatorTotal.setImmediate(true);
//        Label itemsPerPagePagingLabel = new Label(config.getItemsPerPage(),
//                ContentMode.HTML);
//        itemsPerPagePagingLabel.setSizeUndefined();
//
//        final Label totalPagesLabel = new Label(
//                String.valueOf(getTotalAmountOfPages()), ContentMode.HTML);
//
//        final NativeSelect itemsPerPagePagingSelect = new NativeSelect();
//        itemsPerPagePagingSelect.setId("itemsPerPageSelect");
//
//        for (Integer i : config.getPageLengths()) {
//            itemsPerPagePagingSelect.addItem(i);
//            itemsPerPagePagingSelect.setItemCaption(i, String.valueOf(i));
//        }
//        itemsPerPagePagingSelect.setImmediate(true);
//        itemsPerPagePagingSelect.setNullSelectionAllowed(false);
//        itemsPerPagePagingSelect.setWidth("50px");
//        itemsPerPagePagingSelect.addValueChangeListener(new Property.ValueChangeListener() {
//            private static final long serialVersionUID = -2255853716069800092L;
//
//            @Override
//            public void valueChange(
//                    com.vaadin.data.Property.ValueChangeEvent event) {
//                if ((Integer) event.getProperty().getValue() > totalRow) {
//                    container.setPageLength(totalRow == 0 ? 3 : totalRow);
//                    setPageLength(totalRow == 0 ? 3 : totalRow);
//                    resizePage = false;
//                } else {
//                    container.setPageLength((Integer) event.getProperty().getValue());
//                    setPageLength((Integer) event.getProperty().getValue());
//                    
//                }
//                currPageLength = (Integer) event.getProperty().getValue();
//                startRow = currPage * getPageLength() - getPageLength();
////                container.setPageLength(currPageLength);
//                
//                
//                int totalPage = getTotalAmountOfPages();
//                totalPagesLabel.setValue(Constants.EMPTY_CHARACTER + totalPage);
//            }
//        });
//        itemsPerPagePagingSelect.select(Integer.parseInt(pageSizeDefaul));
//
//        Label pagePagingLabel = new Label(config.getPage(), ContentMode.HTML);
//        final TextField currentPagePagingTextField = new TextField();
//        currentPagePagingTextField.setValue(String.valueOf(getCurrentPage()));
//
//        Label separatorLabel = new Label("&nbsp;/&nbsp;", ContentMode.HTML);
//
//        currentPagePagingTextField.setStyleName(Reindeer.TEXTFIELD_SMALL);
//        currentPagePagingTextField.setImmediate(true);
//        currentPagePagingTextField.setId("currentPageTextField");
//        currentPagePagingTextField.addValueChangeListener(new Property.ValueChangeListener() {
//            private static final long serialVersionUID = -2255853716069800092L;
//
//            @Override
//            public void valueChange(
//                    com.vaadin.data.Property.ValueChangeEvent event) {
//                if (currentPagePagingTextField.isValid()
//                        && currentPagePagingTextField.getValue() != null) {
//                    int totalPage = getTotalAmountOfPages();
//                    totalPagesLabel.setValue(Constants.EMPTY_CHARACTER + totalPage);
//                    int page;
//                    try {
//                        page = Integer.valueOf(String.valueOf(currentPagePagingTextField.getValue().trim()));
//                    } catch (Exception e) {
//                        page = 1;
//                        PrintLog.printLog(e);
//                    }
//                    if (page > totalPage || page <= 0) {
////                        page = 1;
//                        currentPagePagingTextField.setValue(Constants.EMPTY_CHARACTER + 1);
//                    } else {
//                        currPage = page;
//                        startRow = page * getPageLength() - getPageLength();
//                        if (!isFirstTime) {
//                            firePagedSelectedEvent();
//                        }
//                    }
//                }
//            }
//        });
//        pagePagingLabel.setWidth(null);
//        currentPagePagingTextField.setColumns(3);
//        separatorLabel.setWidth(null);
//        totalPagesLabel.setWidth(null);
//
//        totalPagesLabel.addListener(new Property.ValueChangeListener() {
//
//            @Override
//            public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
//                String textField = currentPagePagingTextField.getValue();
//                String textLabel = event.getProperty().getValue().toString();
//                try {
//                    if (Integer.valueOf(textLabel) < Integer.valueOf(textField)) {
//                        currentPagePagingTextField.setValue(textLabel);
//                    }
//                } catch (Exception e) {
//                    PrintLog.printLog(e);
//                    currentPagePagingTextField.setValue("1");
//                }
//                if (!isFirstTime && !isInit) {
//                    firePagedSelectedEvent();
//                }
//                if (!isInit) {
//                    isFirstTime = false;
//                }
//                isInit = false;
//
//            }
//        });
//
//        HorizontalLayout controlBar = new HorizontalLayout();
//        VerticalLayout pageSize = new VerticalLayout();
//        HorizontalLayout pageManagement = new HorizontalLayout();
//        final Button firstPaging = new Button(config.getFirst(), new ClickListener() {
//            private static final long serialVersionUID = -355520120491283992L;
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                startRow = 0;
//                currPage = 1;
//                isFirstTime = false;
//                currentPagePagingTextField.setValue(Constants.EMPTY_CHARACTER + currPage);
////                firePagedSelectedEvent();
//            }
//        });
//        final Button previousPaging = new Button(config.getPrevious(),
//                new ClickListener() {
//                    private static final long serialVersionUID = -355520120491283992L;
//
//                    @Override
//                    public void buttonClick(ClickEvent event) {
//                        startRow = startRow - getPageLength() >= 0 ? startRow - getPageLength() : 0;
//                        currPage -= 1;
//                        isFirstTime = false;
//                        currentPagePagingTextField.setValue(Constants.EMPTY_CHARACTER + currPage);
////                        firePagedSelectedEvent();
//
//                    }
//                });
//        final Button nextPaging = new Button(config.getNext(), new ClickListener() {
//            private static final long serialVersionUID = -1927138212640638452L;
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                startRow = startRow + getPageLength() <= totalRow ? startRow + getPageLength() : totalRow;
//                currPage += 1;
//                isFirstTime = false;
//                currentPagePagingTextField.setValue(Constants.EMPTY_CHARACTER + currPage);
////                firePagedSelectedEvent();
//
//            }
//        });
//        final Button lastPaging = new Button(config.getLast(), new ClickListener() {
//            private static final long serialVersionUID = -355520120491283992L;
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                startRow = totalRow - getPageLength() > 0 ? totalRow - getPageLength() : 0;
//                currPage = totalRow / getPageLength() + 1;
//                isFirstTime = false;
//                currentPagePagingTextField.setValue(Constants.EMPTY_CHARACTER + currPage);
////                firePagedSelectedEvent();
//
//            }
//        });
//        firstPaging.setStyleName(Reindeer.BUTTON_LINK);
//        previousPaging.setStyleName(Reindeer.BUTTON_LINK);
//        nextPaging.setStyleName(Reindeer.BUTTON_LINK);
//        lastPaging.setStyleName(Reindeer.BUTTON_LINK);
//
//        itemsPerPagePagingLabel.addStyleName("pagedtable-itemsperpagecaption");
//        itemsPerPagePagingSelect.addStyleName("pagedtable-itemsperpagecombobox");
//        pagePagingLabel.addStyleName("pagedtable-pagecaption");
//        currentPagePagingTextField.addStyleName("pagedtable-pagefield");
//        separatorLabel.addStyleName("pagedtable-separator");
//        separatorTotal.addStyleName("pagedtable-separator");
//        totalPagesLabel.addStyleName("pagedtable-total");
//        firstPaging.addStyleName("pagedtable-first");
//        previousPaging.addStyleName("pagedtable-previous");
//        nextPaging.addStyleName("pagedtable-next");
//        lastPaging.addStyleName("pagedtable-last");
//
//        itemsPerPagePagingLabel.addStyleName("pagedtable-label");
//        itemsPerPagePagingSelect.addStyleName("pagedtable-combobox");
//        pagePagingLabel.addStyleName("pagedtable-label");
//        currentPagePagingTextField.addStyleName("pagedtable-label");
//        separatorLabel.addStyleName("pagedtable-label");
//        separatorTotal.addStyleName("pagedtable-label");
//        totalPagesLabel.addStyleName("pagedtable-label");
//        firstPaging.addStyleName("pagedtable-button");
//        previousPaging.addStyleName("pagedtable-button");
//        nextPaging.addStyleName("pagedtable-button");
//        lastPaging.addStyleName("pagedtable-button");
//
//        Label separator = new Label("&nbsp;/&nbsp;", ContentMode.HTML);
//        pageSize.setSpacing(true);
//
//        //tuanpv14_start
//        CssLayout clSearchInfo = new CssLayout();
//        clSearchInfo.setStyleName("layout-warpping-31");
////        clSearchInfo.setWidth("100.0%");
//        HorizontalLayout hl0 = new HorizontalLayout();
//        HorizontalLayout hl1 = new HorizontalLayout();
//        HorizontalLayout hl2 = new HorizontalLayout();
//        hl2.addComponent(separator);
//        hl2.addComponent(separatorTotal);
//        hl2.setComponentAlignment(separator, Alignment.MIDDLE_LEFT);
//        hl2.setComponentAlignment(separatorTotal, Alignment.MIDDLE_LEFT);
//        new Responsive(clSearchInfo);
////        Responsive.makeResponsive(clSearchInfo);
//        CssLayout cl1 = new CssLayout();
//        cl1.setImmediate(false);
//        cl1.setWidthUndefined();
//        cl1.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        cl1.addStyleName("warpping-item3");
//        itemsPerPagePagingLabel.setWidth("100%");
//        hl0.addComponent(itemsPerPagePagingLabel);
//        hl0.setComponentAlignment(itemsPerPagePagingLabel, Alignment.MIDDLE_LEFT);
//        cl1.addComponent(hl0);
//
//        CssLayout cl2 = new CssLayout();
//        cl2.setImmediate(false);
//        cl2.setWidthUndefined();
//        cl2.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        cl2.addStyleName("warpping-item1");
//        itemsPerPagePagingSelect.setWidth("100%");
//        hl1.addComponent(itemsPerPagePagingSelect);
//        hl1.setComponentAlignment(itemsPerPagePagingSelect, Alignment.MIDDLE_LEFT);
//        cl2.addComponent(hl1);
//
//        CssLayout cl3 = new CssLayout();
//        cl3.setImmediate(false);
//        cl3.setWidthUndefined();
//        cl3.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        cl3.addStyleName("warpping-item2");
//        cl3.addComponent(hl2);
//
//        clSearchInfo.addComponent(cl1);
//        clSearchInfo.addComponent(cl2);
//        clSearchInfo.addComponent(cl3);
//
//        pageSize.addComponent(clSearchInfo);
//        pageSize.setComponentAlignment(clSearchInfo, Alignment.MIDDLE_LEFT);
//        //tuanpv14_end
//
//        firstPaging.setId("firstPage");
//        previousPaging.setId("previousPage");
//        nextPaging.setId("nextPage");
//        lastPaging.setId("lastPage");
//        pageManagement.addComponent(firstPaging);
//        pageManagement.addComponent(previousPaging);
//        pageManagement.addComponent(pagePagingLabel);
//        pageManagement.addComponent(currentPagePagingTextField);
//        pageManagement.addComponent(separatorLabel);
//        pageManagement.addComponent(totalPagesLabel);
//        pageManagement.addComponent(nextPaging);
//        pageManagement.addComponent(lastPaging);
//        pageManagement.setComponentAlignment(firstPaging, Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(previousPaging, Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(pagePagingLabel, Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(currentPagePagingTextField,
//                Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(separatorLabel,
//                Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(totalPagesLabel,
//                Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(nextPaging, Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(lastPaging, Alignment.MIDDLE_LEFT);
//        pageManagement.setWidth(null);
//        pageManagement.setSpacing(true);
//        controlBar.addComponent(pageSize);
//        controlBar.addComponent(pageManagement);
//        controlBar.setComponentAlignment(pageManagement,
//                Alignment.MIDDLE_CENTER);
//        controlBar.setWidth(100, Sizeable.Unit.PERCENTAGE);
//        controlBar.setExpandRatio(pageSize, 1);
//
//        firstPaging.setEnabled(startRow > 0);
//        previousPaging.setEnabled(startRow > 0);
//        nextPaging.setEnabled(startRow < totalRow - getPageLength());
//        lastPaging.setEnabled(startRow < totalRow - getPageLength());
//
//        addListener(new PagePagingChangeListener() {
//            private boolean inMiddleOfValueChange;
//
//            @Override
//            public void pageChanged(PagedPagingTableChangeEvent event) {
//                if (!inMiddleOfValueChange) {
//                    inMiddleOfValueChange = true;
//
//                    firstPaging.setEnabled(startRow > 0);
//                    previousPaging.setEnabled(startRow > 0);
//                    nextPaging.setEnabled(startRow < totalRow - getPageLength());
//                    lastPaging.setEnabled(startRow < totalRow - getPageLength());
////                    totalPagesLabel.setValue(Integer.toString(getTotalAmountOfPages()));
//
//                    totalPagesLabel.setValue(Integer.toString((totalRow - 1) / currPageLength + 1));
//                    if (resizePage) {
//                        itemsPerPagePagingSelect.setValue(getPageLength());
//                    }
//
//                    inMiddleOfValueChange = false;
//                    separatorTotal.setValue(BundleUtils.getString("common.table.totalRecord") + String.valueOf(totalRow));
//                }
//            }
//        });
//        return controlBar;
//    }
//
//    @Override
//    public PagedFilterTableContainer<T> getContainerDataSource() {
//        return container;
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public void setContainerDataSource(Container newDataSource) {
//        if (!(newDataSource instanceof Container.Indexed)
//                || !(newDataSource instanceof Container.Filterable)) {
//            throw new IllegalArgumentException(
//                    "PagedFilteringTable can only use containers that implement Container.Indexed AND Container.Filterable");
//        }
//        PagedFilterTableContainer<T> pagedFilteringTableContainer = new PagedFilterTableContainer<T>(
//                (T) newDataSource);
//        container = pagedFilteringTableContainer;
//        if (newDataSource.size() < getPageLength()) {
//            setPageLength(newDataSource.size() == 0 ? 3 : newDataSource.size());
//            resizePage = false;
//        } else if (newDataSource.size() <= getPageLength()) {
//            setPageLength(newDataSource.size());
//            resizePage = false;
//        } else if (newDataSource.size() > getPageLength()) {
//            setPageLength(currPageLength);
//            resizePage = true;
//        } else {
//            setPageLength(5);
//            resizePage = true;
//        }
//        //todo change size here
//        if (separatorTotal != null && container != null) {
//            separatorTotal.setValue(BundleUtils.getString("common.table.totalRecord") + String.valueOf(totalRow));
//        }
//
//        super.setContainerDataSource(pagedFilteringTableContainer);
//        firePagedChangedEvent();
//    }
//
//    private void firePagedChangedEvent() {
//        if (listeners != null) {
//            PagedPagingTableChangeEvent event = new PagedPagingTableChangeEvent(this);
//            for (PagePagingChangeListener listener : listeners) {
//                listener.pageChanged(event);
//            }
//        }
//    }
//
//    private void firePagedSelectedEvent() {
//        if (selectListeners != null) {
//            for (PageSelectListener listener : selectListeners) {
//                listener.pageSelected();
//            }
//        }
//    }
//
//    public void resetPage() {
//        resizePage = true;
//        firePagedChangedEvent();
//    }
//
//    @Override
//    public final void setPageLength(int pageLength) {
//        if (pageLength == 1) {
//            pageLength++;
//        }
//        if (pageLength >= 0) {
//            container.setPageLength(pageLength);
//            super.setPageLength(pageLength);
//            firePagedChangedEvent();
//        }
//    }
//
//    public int getCurrentPage() {
//        return currPage;
//    }
//
//    public void setCurrentPage(int page) {
//        currPage = page;
//    }
//
//    public int getTotalAmountOfPages() {
////        int size = container.getContainer().size();
//        int size = totalRow;
////        double pageLength = getPageLength();
//        int pageCount = (int) Math.ceil(size / currPageLength) + 1;
//        if (pageCount < 1) {
//            pageCount = 1;
//        }
//        return pageCount;
//    }
//
//    public void addListener(PagePagingChangeListener listener) {
//        if (listeners == null) {
//            listeners = new ArrayList<PagePagingChangeListener>();
//        }
//        listeners.add(listener);
//    }
//
//    public void removeListener(PagePagingChangeListener listener) {
//        if (listeners == null) {
//            listeners = new ArrayList<PagePagingChangeListener>();
//        }
//        listeners.remove(listener);
//    }
//
//    public void addListener(PageSelectListener listener) {
//        if (selectListeners == null) {
//            selectListeners = new ArrayList<PageSelectListener>();
//        }
//        selectListeners.add(listener);
//    }
//
//    public void removeListener(PageSelectListener listener) {
//        if (selectListeners == null) {
//            selectListeners = new ArrayList<PageSelectListener>();
//        }
//        selectListeners.remove(listener);
//    }
//
//    @Override
//    public void resetFilters() {
//        super.resetFilters();
////        setCurrentPage(1);
//    }
//
//    public int getStartRow() {
//        return startRow;
//    }
//
//    public void setStartRow(int startRow) {
//        this.startRow = startRow;
//    }
//
//    public int getTotalRow() {
//        return totalRow;
//    }
//
//    public void setTotalRow(int totalRow) {
//        this.totalRow = totalRow;
//    }
//
//    public PagedFilterTableContainer<T> getContainer() {
//        return container;
//    }
//
//    public void setContainer(PagedFilterTableContainer<T> container) {
//        this.container = container;
//    }
//
//}
//
