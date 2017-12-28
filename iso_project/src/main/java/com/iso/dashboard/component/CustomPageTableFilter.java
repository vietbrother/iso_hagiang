//
//package com.iso.dashboard.component;
//
//import com.iso.dashboard.component.event.PagedTableChangeEvent;
//import com.iso.dashboard.utils.BundleUtils;
//import com.iso.dashboard.utils.Constants;
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
////import org.tepi.filtertable.paged.PagedFilterTableContainer;
//
//@SuppressWarnings("serial")
//public class CustomPageTableFilter<T extends Container.Indexed & Container.Filterable & Container.ItemSetChangeNotifier>
//        extends FilterTable {
//
//    public interface PageChangeListener extends Serializable {
//
//        public void pageChanged(PagedTableChangeEvent event);
//    }
//
//    private List<PageChangeListener> listeners = null;
//
//    private PagedFilterTableContainer<T> container;
//    boolean resizePage = true;
//    private String pageSizeDefault;
//    Label separatorTotal = new Label(Constants.EMPTY_CHARACTER, ContentMode.HTML);
////    int currPageLength = 20;
//    int currPageLength = 10;
//
//
//    Label itemsPerPageLabel;
//    VerticalLayout pageSize;
//    HorizontalLayout pageManagement;
//
//    public int getCurrPageLength() {
//        return currPageLength;
//    }
//
//    public void setCurrPageLength(int currPageLength) {
//        this.currPageLength = currPageLength;
//    }
//
//    public CustomPageTableFilter() {
//        this(null);
//    }
//
//    public CustomPageTableFilter(String caption) {
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
////        Label itemsPerPageLabel = new Label(config.getItemsPerPage(),
//        itemsPerPageLabel = new Label(config.getItemsPerPage(),
//                ContentMode.HTML);
//        itemsPerPageLabel.setSizeUndefined();
//        final NativeSelect itemsPerPageSelect = new NativeSelect();
//        itemsPerPageSelect.setId("itemsPerPageSelect");
//
//        for (Integer i : config.getPageLengths()) {
//            itemsPerPageSelect.addItem(i);
//            itemsPerPageSelect.setItemCaption(i, String.valueOf(i));
//        }
//        itemsPerPageSelect.setImmediate(true);
//        itemsPerPageSelect.setNullSelectionAllowed(false);
//        itemsPerPageSelect.setWidth("50px");
//        itemsPerPageSelect.addValueChangeListener(new Property.ValueChangeListener() {
//            private static final long serialVersionUID = -2255853716069800092L;
//
//            @Override
//            public void valueChange(
//                    com.vaadin.data.Property.ValueChangeEvent event) {
//                if ((Integer) event.getProperty().getValue() > container.getRealSize()) {
//                    setPageLength(container.getRealSize() == 0 ? 3 : container.getRealSize());
//                    resizePage = false;
//                } else {
//                    setPageLength((Integer) event.getProperty().getValue());
//                    currPageLength = (Integer) event.getProperty().getValue();
//                }
//
//            }
//        });
//        itemsPerPageSelect.select(Integer.parseInt(pageSizeDefaul));
////        if (itemsPerPageSelect.containsId(getPageLength())) {
////            itemsPerPageSelect.select(getPageLength());
////        } else {
////            itemsPerPageSelect.select(itemsPerPageSelect.getItemIds()
////                    .iterator().next());
////        }
//        Label pageLabel = new Label(config.getPage(), ContentMode.HTML);
//        final TextField currentPageTextField = new TextField();
//        currentPageTextField.setValue(String.valueOf(getCurrentPage()));
////        currentPageTextField.setConverter(new StringToIntegerConverter() {
////            @Override
////            protected NumberFormat getFormat(Locale locale) {
////                NumberFormat result = super.getFormat(UI.getCurrent()
////                        .getLocale());
////                result.setGroupingUsed(false);
////                return result;
////            }
////        });
//        Label separatorLabel = new Label("&nbsp;/&nbsp;", ContentMode.HTML);
//        final Label totalPagesLabel = new Label(
//                String.valueOf(getTotalAmountOfPages()), ContentMode.HTML);
//        currentPageTextField.setStyleName(Reindeer.TEXTFIELD_SMALL);
//        currentPageTextField.setImmediate(true);
//        currentPageTextField.setId("currentPageTextField");
//        currentPageTextField.addValueChangeListener(new Property.ValueChangeListener() {
//            private static final long serialVersionUID = -2255853716069800092L;
//
//            @Override
//            public void valueChange(
//                    com.vaadin.data.Property.ValueChangeEvent event) {
//                if (currentPageTextField.isValid()
//                        && currentPageTextField.getValue() != null) {
//                    int page = getCurrentPage();
//                    try {
//                        page = Integer.valueOf(String.valueOf(currentPageTextField.getValue().trim()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        page = getCurrentPage();
//                    }
//                    setCurrentPage(page);
//                }
//            }
//        });
//        pageLabel.setWidth(null);
//        currentPageTextField.setColumns(3);
//        separatorLabel.setWidth(null);
//        totalPagesLabel.setWidth(null);
//
//        HorizontalLayout controlBar = new HorizontalLayout();
//		//anhmv6_start
//        pageSize = new VerticalLayout();
//        pageManagement = new HorizontalLayout();
//		//anhmv6_end
////        VerticalLayout pageSize = new VerticalLayout();
////        HorizontalLayout pageManagement = new HorizontalLayout();
//        final Button first = new Button(config.getFirst(), new ClickListener() {
//            private static final long serialVersionUID = -355520120491283992L;
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                setCurrentPage(0);
//            }
//        });
//        final Button previous = new Button(config.getPrevious(),
//                new ClickListener() {
//                    private static final long serialVersionUID = -355520120491283992L;
//
//                    @Override
//                    public void buttonClick(ClickEvent event) {
//                        previousPage();
//                    }
//                });
//        final Button next = new Button(config.getNext(), new ClickListener() {
//            private static final long serialVersionUID = -1927138212640638452L;
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                nextPage();
//            }
//        });
//        final Button last = new Button(config.getLast(), new ClickListener() {
//            private static final long serialVersionUID = -355520120491283992L;
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                setCurrentPage(getTotalAmountOfPages());
//            }
//        });
//        first.setStyleName(Reindeer.BUTTON_LINK);
//        previous.setStyleName(Reindeer.BUTTON_LINK);
//        next.setStyleName(Reindeer.BUTTON_LINK);
//        last.setStyleName(Reindeer.BUTTON_LINK);
//
//        itemsPerPageLabel.addStyleName("pagedtable-itemsperpagecaption");
//        itemsPerPageSelect.addStyleName("pagedtable-itemsperpagecombobox");
//        pageLabel.addStyleName("pagedtable-pagecaption");
//        currentPageTextField.addStyleName("pagedtable-pagefield");
//        separatorLabel.addStyleName("pagedtable-separator");
//        separatorTotal.addStyleName("pagedtable-separator");
//        totalPagesLabel.addStyleName("pagedtable-total");
//        first.addStyleName("pagedtable-first");
//        previous.addStyleName("pagedtable-previous");
//        next.addStyleName("pagedtable-next");
//        last.addStyleName("pagedtable-last");
//
//        itemsPerPageLabel.addStyleName("pagedtable-label");
//        itemsPerPageSelect.addStyleName("pagedtable-combobox");
//        pageLabel.addStyleName("pagedtable-label");
//        currentPageTextField.addStyleName("pagedtable-label");
//        separatorLabel.addStyleName("pagedtable-label");
//        separatorTotal.addStyleName("pagedtable-label");
//        totalPagesLabel.addStyleName("pagedtable-label");
//        first.addStyleName("pagedtable-button");
//        previous.addStyleName("pagedtable-button");
//        next.addStyleName("pagedtable-button");
//        last.addStyleName("pagedtable-button");
//
////        pageSize.addComponent(itemsPerPageLabel);
////        pageSize.addComponent(itemsPerPageSelect);
//        Label separator = new Label("&nbsp;/&nbsp;", ContentMode.HTML);
////        pageSize.addComponent(separator);
////        pageSize.addComponent(separatorTotal);
//
////        pageSize.setComponentAlignment(itemsPerPageLabel, Alignment.MIDDLE_LEFT);
////        pageSize.setComponentAlignment(itemsPerPageSelect,
////                Alignment.MIDDLE_LEFT);
////        pageSize.setComponentAlignment(separator, Alignment.MIDDLE_LEFT);
////        pageSize.setComponentAlignment(separatorTotal, Alignment.MIDDLE_LEFT);
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
//        CssLayout cl1 = new CssLayout();
//        cl1.setImmediate(false);
//        cl1.setWidthUndefined();
//        cl1.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        cl1.addStyleName("warpping-item3");
//        itemsPerPageLabel.setWidth("100%");
//        hl0.addComponent(itemsPerPageLabel);
//        hl0.setComponentAlignment(itemsPerPageLabel, Alignment.MIDDLE_LEFT);
//        cl1.addComponent(hl0);
//
//        CssLayout cl2 = new CssLayout();
//        cl2.setImmediate(false);
//        cl2.setWidthUndefined();
//        cl2.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
//        cl2.addStyleName("warpping-item1");
//        itemsPerPageSelect.setWidth("100%");
//        hl1.addComponent(itemsPerPageSelect);
//        hl1.setComponentAlignment(itemsPerPageSelect, Alignment.MIDDLE_LEFT);
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
//        first.setId("firstPage");
//        previous.setId("previousPage");
//        next.setId("nextPage");
//        last.setId("lastPage");
//        pageManagement.addComponent(first);
//        pageManagement.addComponent(previous);
//        pageManagement.addComponent(pageLabel);
//        pageManagement.addComponent(currentPageTextField);
//        pageManagement.addComponent(separatorLabel);
//        pageManagement.addComponent(totalPagesLabel);
//        pageManagement.addComponent(next);
//        pageManagement.addComponent(last);
//        pageManagement.setComponentAlignment(first, Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(previous, Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(pageLabel, Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(currentPageTextField,
//                Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(separatorLabel,
//                Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(totalPagesLabel,
//                Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(next, Alignment.MIDDLE_LEFT);
//        pageManagement.setComponentAlignment(last, Alignment.MIDDLE_LEFT);
//        pageManagement.setWidth(null);
//        pageManagement.setSpacing(true);
//        controlBar.addComponent(pageSize);
//        controlBar.addComponent(pageManagement);
//        controlBar.setComponentAlignment(pageManagement,
//                Alignment.MIDDLE_CENTER);
//        controlBar.setWidth(100, Sizeable.Unit.PERCENTAGE);
//        controlBar.setExpandRatio(pageSize, 1);
//
//        if (container != null) {
//            first.setEnabled(container.getStartIndex() > 0);
//            previous.setEnabled(container.getStartIndex() > 0);
//            next.setEnabled(container.getStartIndex() < container.getRealSize()
//                    - getPageLength());
//            last.setEnabled(container.getStartIndex() < container.getRealSize()
//                    - getPageLength());
//        }
//
//        addListener(new PageChangeListener() {
//            private boolean inMiddleOfValueChange;
//
//            @Override
//            public void pageChanged(PagedTableChangeEvent event) {
//                if (!inMiddleOfValueChange) {
//                    inMiddleOfValueChange = true;
//                    first.setEnabled(container.getStartIndex() > 0);
//                    previous.setEnabled(container.getStartIndex() > 0);
//                    next.setEnabled(container.getStartIndex() < container
//                            .getRealSize() - getPageLength());
//                    last.setEnabled(container.getStartIndex() < container
//                            .getRealSize() - getPageLength());
//                    currentPageTextField.setValue(String
//                            .valueOf(getCurrentPage()));
//                    totalPagesLabel.setValue(Integer
//                            .toString(getTotalAmountOfPages()));
//                    if (resizePage) {
//                        itemsPerPageSelect
//                                .setValue(getPageLength());
//                    }
//
//                    inMiddleOfValueChange = false;
//                    separatorTotal.setValue(BundleUtils.getString("common.table.totalRecord") + String.valueOf(container.getRealSize()));
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
////            setPageLength(getPageLength() == 3 ? 20 : getPageLength());
//            setPageLength(currPageLength);
//            resizePage = true;
//        } else {
//            setPageLength(5);
//            resizePage = true;
//        }
//        if (separatorTotal != null && container != null) {
//            separatorTotal.setValue(BundleUtils.getString("common.table.totalRecord") + String.valueOf(container.getRealSize()));
//        }
//
//        super.setContainerDataSource(pagedFilteringTableContainer);
//        firePagedChangedEvent();
//    }
//
//    private void setPageFirstIndex(int firstIndex) {
//        if (container != null) {
//            if (firstIndex <= 0) {
//                firstIndex = 0;
//            }
//            if (firstIndex > container.getRealSize() - 1) {
//                int size = container.getRealSize() - 1;
//                int pages = 0;
//                if (getPageLength() != 0) {
//                    pages = (int) Math.floor(0.0 + size / getPageLength());
//                }
//                firstIndex = pages * getPageLength();
//            }
//            container.setStartIndex(firstIndex);
//            containerItemSetChange(new Container.ItemSetChangeEvent() {
//                private static final long serialVersionUID = -5083660879306951876L;
//
//                @Override
//                public Container getContainer() {
//                    return container;
//                }
//            });
//            if (alwaysRecalculateColumnWidths) {
//                for (Object columnId : container.getContainerPropertyIds()) {
//                    setColumnWidth(columnId, -1);
//                }
//            }
//            firePagedChangedEvent();
//        }
//    }
//
//    private void firePagedChangedEvent() {
//        if (listeners != null) {
//            PagedTableChangeEvent event = new PagedTableChangeEvent(this);
//            for (PageChangeListener listener : listeners) {
//                listener.pageChanged(event);
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
//    public void nextPage() {
//        setPageFirstIndex(container.getStartIndex() + getPageLength());
//    }
//
//    public void previousPage() {
//        setPageFirstIndex(container.getStartIndex() - getPageLength());
//    }
//
//    public int getCurrentPage() {
//        double pageLength = getPageLength();
//        int page = (int) Math.floor(container.getStartIndex() / pageLength) + 1;
//        if (page < 1) {
//            page = 1;
//        }
//        return page;
//    }
//
//    public void setCurrentPage(int page) {
//        int newIndex = (page - 1) * getPageLength();
//        if (newIndex < 0) {
//            newIndex = 0;
//        }
//        setPageFirstIndex(newIndex);
//    }
//
//    public int getTotalAmountOfPages() {
//        int size = container.getContainer().size();
//        double pageLength = getPageLength();
//        int pageCount = (int) Math.ceil(size / pageLength);
//        if (pageCount < 1) {
//            pageCount = 1;
//        }
//        return pageCount;
//    }
//
//    public void addListener(PageChangeListener listener) {
//        if (listeners == null) {
//            listeners = new ArrayList<PageChangeListener>();
//        }
//        listeners.add(listener);
//    }
//
//    public void removeListener(PageChangeListener listener) {
//        if (listeners == null) {
//            listeners = new ArrayList<PageChangeListener>();
//        }
//        listeners.remove(listener);
//    }
//
//    @Override
//    public void resetFilters() {
//        super.resetFilters();
//        setCurrentPage(1);
//    }
//
//
//    public List<PageChangeListener> getListeners() {
//        return listeners;
//    }
//
//    public void setListeners(List<PageChangeListener> listeners) {
//        this.listeners = listeners;
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
//    public boolean isResizePage() {
//        return resizePage;
//    }
//
//    public void setResizePage(boolean resizePage) {
//        this.resizePage = resizePage;
//    }
//
//    public String getPageSizeDefault() {
//        return pageSizeDefault;
//    }
//
//    public void setPageSizeDefault(String pageSizeDefault) {
//        this.pageSizeDefault = pageSizeDefault;
//    }
//
//    public Label getSeparatorTotal() {
//        return separatorTotal;
//    }
//
//    public void setSeparatorTotal(Label separatorTotal) {
//        this.separatorTotal = separatorTotal;
//    }
//
//    public Label getItemsPerPageLabel() {
//        return itemsPerPageLabel;
//    }
//
//    public void setItemsPerPageLabel(Label itemsPerPageLabel) {
//        this.itemsPerPageLabel = itemsPerPageLabel;
//    }
//
//    public VerticalLayout getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(VerticalLayout pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public HorizontalLayout getPageManagement() {
//        return pageManagement;
//    }
//
//    public void setPageManagement(HorizontalLayout pageManagement) {
//        this.pageManagement = pageManagement;
//    }
//}
