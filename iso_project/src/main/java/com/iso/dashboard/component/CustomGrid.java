package com.iso.dashboard.component;

import com.iso.dashboard.component.render.CustomButtonValueRenderer;
import com.iso.dashboard.controller.HandlerActionGrid;
import com.iso.dashboard.controller.HandlerButtonActionGrid;
import com.iso.dashboard.utils.BundleUtils;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.CellReference;
import com.vaadin.ui.Grid.CellStyleGenerator;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Arrays;
import org.vaadin.gridutil.GridUtil;
import org.vaadin.gridutil.cell.GridCellFilter;
import org.vaadin.gridutil.renderer.EditDeleteButtonValueRenderer;
import org.vaadin.gridutil.renderer.ViewButtonValueRenderer;
import org.vaadin.gridutil.renderer.ViewDeleteButtonValueRenderer;
import org.vaadin.gridutil.renderer.ViewEditDeleteButtonValueRenderer;

public class CustomGrid extends Grid {

    private GridCellFilter filter;
    private final String[] PAGE_SIZE_LIST = {"5", "10", "15", "20", "25"};
    private final String PAGE_DEFAULT = "10";
    private final NativeSelect itemsPerPageSelect = new NativeSelect();
    private GeneratedPropertyContainer wrappingContainer;
    private Label totalRecords = new Label("", ContentMode.HTML);

    public CustomGrid() {
        this.setEditorSaveCaption(BundleUtils.getString("common.button.save"));
        this.setEditorCancelCaption(BundleUtils.getString("common.button.cancel"));
        this.setResponsive(true);
    }

    public void genGrid(IndexedContainer container, String prefix,
            String[] columnsVisible, String[] columnsOrder, HandlerButtonActionGrid handler) {
        // init Grid
        this.totalRecords.setStyleName(ValoTheme.LABEL_BOLD);
        this.setSizeFull();
        this.setHeightMode(HeightMode.ROW);
        Grid grid = this;
        this.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                {
                    if (itemClickEvent.getButton() == MouseEventDetails.MouseButton.LEFT && itemClickEvent.isDoubleClick()) {
                        Object itemId = itemClickEvent.getItemId();
                        if (itemId != null) {
                            grid.editItem(itemId);
                        }
                    }

                }
            }
        });

        // wrap the bean item container so we can generated a fake header column
        wrappingContainer = createWrapContainer(container);

        // assign the data source to the grid and set desired column order
        this.setContainerDataSource((Container.Indexed) wrappingContainer);

        // freeze the fake header column to prevent it from scrolling horizontally
//        this.setFrozenColumnCount(1);
        if (columnsOrder != null && columnsOrder.length > 0) {
            this.setColumnOrder(columnsOrder);
        }

        this.setColumns(columnsVisible);
        //set column headername
        String headerName = null;
        for (String columnsVisible1 : columnsVisible) {
            headerName = BundleUtils.getString(prefix + "." + columnsVisible1);
            this.getColumn(columnsVisible1).setHeaderCaption(headerName);
        }

        setColumnRenderes(this, handler, columnsVisible);
//        initFilter(grid);
        initFooterRow(this, container, columnsVisible);
//        initExtraHeaderRow(grid);
        initColumnAlignments(this);
        this.setContainerDataSource(this.getContainerDataSource());
        
        setColumnResizeMode(ColumnResizeMode.SIMPLE);
    }

    public void genGridCustomButton(IndexedContainer container, String prefix,
            String[] columnsVisible, String[] columnsOrder, HandlerActionGrid handler) {
        // init Grid
        this.totalRecords.setStyleName(ValoTheme.LABEL_BOLD);
        this.setSizeFull();
        this.setHeightMode(HeightMode.ROW);
        Grid grid = this;
        this.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                {
                    if (itemClickEvent.getButton() == MouseEventDetails.MouseButton.LEFT && itemClickEvent.isDoubleClick()) {
                        Object itemId = itemClickEvent.getItemId();
                        if (itemId != null) {
                            grid.editItem(itemId);
                        }
                    }

                }
            }
        });

        // wrap the bean item container so we can generated a fake header column
        wrappingContainer = createWrapContainer(container);

        // assign the data source to the grid and set desired column order
        this.setContainerDataSource((Container.Indexed) wrappingContainer);

        // freeze the fake header column to prevent it from scrolling horizontally
//        this.setFrozenColumnCount(1);
        if (columnsOrder != null && columnsOrder.length > 0) {
            this.setColumnOrder(columnsOrder);
        }
        this.setColumns(columnsVisible);

        //set column headername
        String headerName = null;
        for (String columnsVisible1 : columnsVisible) {
            headerName = BundleUtils.getString(prefix + "." + columnsVisible1);
            this.getColumn(columnsVisible1).setHeaderCaption(headerName);
        }

        setColumnRenderes(this, handler);
//        initFilter(grid);
        initFooterRow(this, container, columnsVisible);
//        initExtraHeaderRow(grid);
        initColumnAlignments(this);
        this.setContainerDataSource(this.getContainerDataSource());
    }

    public void genGridCustomButton1(IndexedContainer container, String prefix,
            String[] columnsVisible, String[] columnsOrder, HandlerButtonActionGrid handler) {
        // init Grid
        this.totalRecords.setStyleName(ValoTheme.LABEL_BOLD);
        this.setSizeFull();
        this.setHeightMode(HeightMode.ROW);
        Grid grid = this;
        this.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                {
                    if (itemClickEvent.getButton() == MouseEventDetails.MouseButton.LEFT && itemClickEvent.isDoubleClick()) {
                        Object itemId = itemClickEvent.getItemId();
                        if (itemId != null) {
                            grid.editItem(itemId);
                        }
                    }

                }
            }
        });

        // wrap the bean item container so we can generated a fake header column
        wrappingContainer = createWrapContainer(container);

        // assign the data source to the grid and set desired column order
        this.setContainerDataSource((Container.Indexed) wrappingContainer);

        // freeze the fake header column to prevent it from scrolling horizontally
//        this.setFrozenColumnCount(1);
        if (columnsOrder != null && columnsOrder.length > 0) {
            this.setColumnOrder(columnsOrder);
        }
        this.setColumns(columnsVisible);

        //set column headername
        String headerName = null;
        for (String columnsVisible1 : columnsVisible) {
            headerName = BundleUtils.getString(prefix + "." + columnsVisible1);
            this.getColumn(columnsVisible1).setHeaderCaption(headerName);
        }

        setSelectDeleteRenders(this, handler);
//        initFilter(grid);
        initFooterRow(this, container, columnsVisible);
//        initExtraHeaderRow(grid);
        initColumnAlignments(this);
        this.setContainerDataSource(this.getContainerDataSource());
    }

    public GeneratedPropertyContainer createWrapContainer(IndexedContainer container) {
        totalRecords.setValue(BundleUtils.getString("common.table.totalRecord") + ": "
                + "<b>" + container.getItemIds().size() + "</b>");
        int height = container.size() == 0 ? 1 : container.size();
        if (itemsPerPageSelect != null && itemsPerPageSelect.getValue() != null) {
            if (height > Integer.valueOf(String.valueOf(itemsPerPageSelect.getValue()))) {
                height = Integer.valueOf(String.valueOf(itemsPerPageSelect.getValue()));
            }
        } else {
            if (height > Integer.valueOf(PAGE_DEFAULT)) {
                height = Integer.valueOf(PAGE_DEFAULT);
            }
            this.setHeightByRows(height);
        }

        this.setHeightByRows(height);
        GeneratedPropertyContainer wrappingContainer = new GeneratedPropertyContainer(container);
        wrappingContainer.addGeneratedProperty("stt", new PropertyValueGenerator<Long>() {
            private long index = 1;

            @Override
            public Long getValue(Item item, Object itemId, Object propertyId) {
                return (long) (container.indexOfId(itemId) + 1);
            }

            @Override
            public Class<Long> getType() {
                return Long.class;
            }
        });
        wrappingContainer.addGeneratedProperty("action", new PropertyValueGenerator<String>() {
            private long index = 0;

            @Override
            public String getValue(Item item, Object itemId, Object propertyId) {
                return "";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
        wrappingContainer.addGeneratedProperty("view", new PropertyValueGenerator<String>() {
            private long index = 0;

            @Override
            public String getValue(Item item, Object itemId, Object propertyId) {
                return "";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
        wrappingContainer.addGeneratedProperty("viewEditDelete", new PropertyValueGenerator<String>() {
            private long index = 0;

            @Override
            public String getValue(Item item, Object itemId, Object propertyId) {
                return "";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
        return wrappingContainer;
    }

    private void setColumnRenderes(final Grid grid, HandlerButtonActionGrid handler, String[] columVisible) {

        // tweak it a bit - definitely needs more tweaking
        grid.getColumn("stt").setHeaderCaption(BundleUtils.getString("common.table.index")).setHidable(false)
                .setEditable(false);
        grid.getColumn("stt").setWidth(60);
        grid.getColumn("stt").setEditable(false);

        if (Arrays.asList(columVisible).contains("action")) {
            //        grid.getHeaderRow(0).setStyleName(GridUtil.ALIGN_CELL_CENTER);
            grid.getColumn("action").setHeaderCaption(BundleUtils.getString("common.table.action"));

            grid.getColumn("action")
                    .setRenderer(new EditDeleteButtonValueRenderer(new EditDeleteButtonValueRenderer.EditDeleteButtonClickListener() {

                        @Override
                        public void onEdit(final RendererClickEvent event) {
                            handler.actionEdit(event.getItemId());
                        }

                        @Override
                        public void onDelete(final RendererClickEvent event) {
                            handler.actionDelete(event.getItemId());
                        }
                    }))
                    .setMinimumWidth(100);

            grid.getColumn("action").setEditable(false);
        }
        if (Arrays.asList(columVisible).contains("view")) {
            //        grid.getHeaderRow(0).setStyleName(GridUtil.ALIGN_CELL_CENTER);
            grid.getColumn("view").setHeaderCaption(BundleUtils.getString("common.table.action"));

            grid.getColumn("view")
                    .setRenderer(new ViewButtonValueRenderer(new ClickableRenderer.RendererClickListener() {

                        @Override
                        public void click(RendererClickEvent event) {
                            handler.actionSelect(event.getItemId());
                        }
                    })).setMinimumWidth(100);

            grid.getColumn("view").setEditable(false);
        } else if (Arrays.asList(columVisible).contains("viewEditDelete")) {
            grid.getColumn("viewEditDelete").setHeaderCaption(BundleUtils.getString("common.table.action"));

            grid.getColumn("viewEditDelete")
                    .setRenderer(new ViewEditDeleteButtonValueRenderer(new ViewEditDeleteButtonValueRenderer.ViewEditDeleteButtonClickListener() {

                        @Override
                        public void onView(RendererClickEvent event) {
                            handler.actionSelect(event.getItemId());
                        }

                        @Override
                        public void onEdit(RendererClickEvent event) {
                            handler.actionEdit(event.getItemId());
                        }

                        @Override
                        public void onDelete(RendererClickEvent event) {
                            handler.actionDelete(event.getItemId());
                        }
                    })).setMinimumWidth(100);

            grid.getColumn("viewEditDelete").setEditable(false);
        }
        /*
         * the icon of the editButton will get overwritten below by css styling @see DemoUI.initColumnAlignments
         */
    }

    private void setSelectDeleteRenders(final Grid grid, HandlerButtonActionGrid handler) {

        // tweak it a bit - definitely needs more tweaking
        grid.getColumn("stt").setHeaderCaption(BundleUtils.getString("common.table.index")).setHidable(false)
                .setEditable(false);
        grid.getColumn("stt").setWidth(60);
//        grid.getHeaderRow(0).setStyleName(GridUtil.ALIGN_CELL_CENTER);
        grid.getColumn("action").setHeaderCaption(BundleUtils.getString("common.table.action"));

        grid.getColumn("action")
                .setRenderer(new ViewDeleteButtonValueRenderer(new ViewDeleteButtonValueRenderer.ViewDeleteButtonClickListener() {
                    @Override
                    public void onView(RendererClickEvent event) {
                        handler.actionSelect(event.getItemId());
                    }

                    @Override
                    public void onDelete(RendererClickEvent event) {
                        handler.actionDelete(event.getItemId());
                    }
                })).setMinimumWidth(100);

        grid.getColumn("stt").setEditable(false);
        grid.getColumn("action").setEditable(false);
        /*
         * the icon of the editButton will get overwritten below by css styling @see DemoUI.initColumnAlignments
         */
    }

    public void setColumnRenderes(final Grid grid, HandlerActionGrid handler) {

        // tweak it a bit - definitely needs more tweaking
        grid.getColumn("stt").setHeaderCaption(BundleUtils.getString("common.table.index")).setHidable(false)
                .setEditable(false);
        grid.getColumn("stt").setWidth(60);
//        grid.getHeaderRow(0).setStyleName(GridUtil.ALIGN_CELL_CENTER);
        grid.getColumn("action").setHeaderCaption(BundleUtils.getString("common.table.action"));

        grid.getColumn("action")
                .setRenderer(new CustomButtonValueRenderer(new CustomButtonValueRenderer.CustomButtonClickListener() {
                    @Override
                    public void onEdit(RendererClickEvent event) {
                        handler.actionEdit(event.getItemId());
                    }

                    @Override
                    public void onDelete(RendererClickEvent event) {
                        handler.actionDelete(event.getItemId());
                    }

                    @Override
                    public void onView(RendererClickEvent event) {
                        handler.actionSelect(event.getItemId());
                    }

                    @Override
                    public void onAssign(RendererClickEvent event) {
                        handler.actionAssign(event.getItemId());
                    }

                    @Override
                    public void onReport(RendererClickEvent event) {
                        handler.actionReport(event.getItemId());
                    }
                })).setMinimumWidth(200);

        grid.getColumn("stt").setEditable(false);
        grid.getColumn("action").setEditable(false);
        /*
         * the icon of the editButton will get overwritten below by css styling @see DemoUI.initColumnAlignments
         */
    }

    /**
     * generates a simple totalCount footer
     *
     * @param grid
     * @param container
     */
//    private void initFooterRow(final Grid grid, final BeanItemContainer<Users> container) {
    private void initFooterRow(final Grid grid, IndexedContainer container, String[] columsVisible) {
        final FooterRow footerRow = grid.appendFooterRow();
//        footerRow.getCell("stt")
//                .setHtml("total:");
        footerRow.join(columsVisible);
        // inital total count

        HorizontalLayout controlBar = initControlBar(grid);
        footerRow.getCell("stt")
                .setComponent(controlBar);
        // filter change count recalculate
        container.addItemSetChangeListener(new Container.ItemSetChangeListener() {

            @Override
            public void containerItemSetChange(final Container.ItemSetChangeEvent event) {
                totalRecords.setValue(BundleUtils.getString("common.table.totalRecord") + ": "
                        + "<b>" + container.getItemIds().size() + "</b>");
            }
        });
    }

    public HorizontalLayout initControlBar(Grid grid) {
        HorizontalLayout controlBar = new HorizontalLayout();
        controlBar.setWidth("100%");
//        Label totalRecord = new Label(BundleUtils.getString("common.table.totalRecord") + ": "
//                + "<b>" + re + "</b>", ContentMode.HTML);

        HorizontalLayout totalRecordLayout = new HorizontalLayout();
        totalRecordLayout.addComponent(totalRecords);

        Label itemsPerPageTitle = new Label(BundleUtils.getString("common.table.view") + ":");
        for (String numberSize : PAGE_SIZE_LIST) {
            itemsPerPageSelect.addItem(numberSize);
        }
        itemsPerPageSelect.setImmediate(true);
        itemsPerPageSelect.setNullSelectionAllowed(false);
        itemsPerPageSelect.setWidth("62px");
        itemsPerPageSelect.setHeight("29px");
        itemsPerPageSelect.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -2255853716069800092L;

            @Override
            public void valueChange(
                    com.vaadin.data.Property.ValueChangeEvent event) {
                int height = grid.getContainerDataSource().size() == 0 ? 1 : grid.getContainerDataSource().size();
                if (height > Integer.valueOf(String.valueOf(event.getProperty().getValue()))) {
                    height = Integer.valueOf(String.valueOf(event.getProperty().getValue()));
                }
                grid.setHeightByRows(height);
            }
        });
        itemsPerPageSelect.select(PAGE_DEFAULT);
        HorizontalLayout itemsPerPageLayout = new HorizontalLayout();
        itemsPerPageLayout.addComponent(itemsPerPageTitle);
        itemsPerPageLayout.setComponentAlignment(itemsPerPageTitle, Alignment.TOP_RIGHT);
        itemsPerPageLayout.addComponent(itemsPerPageSelect);
        itemsPerPageLayout.setComponentAlignment(itemsPerPageSelect, Alignment.TOP_LEFT);
        itemsPerPageLayout.setSpacing(true);

        controlBar.addComponent(totalRecordLayout);
        controlBar.setComponentAlignment(totalRecordLayout, Alignment.TOP_LEFT);
        controlBar.addComponent(itemsPerPageLayout);
        controlBar.setComponentAlignment(itemsPerPageLayout, Alignment.TOP_RIGHT);

        return controlBar;
    }

    /**
     * initialize a GridCellFilter
     *
     * @param grid
     */
    public void initFilter(final Grid grid) {
        this.filter = new GridCellFilter(grid);
//        this.filter.setNumberFilter("stt");
//
//        // simple filters
//        this.filter.setTextFilter("username", true, true, "name starts with");
////        this.filter.setCustomFilter("stt", new GridComponent(new Label("STT")));
////        this.filter.setCustomFilter("email", new GridComponent(new Label("Email")));
////        this.filter.setCustomFilter("phone", new GridComponent(new Label("Phone")));
//
//        RangeCellFilterComponent<DateField, HorizontalLayout> dateFilter = this.filter.setDateFilter("birthday", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), true);
//        dateFilter.getSmallestField()
//                .setParseErrorMessage("da ist was schief gegangen :)");

//        this.filter.setBooleanFilter("status",
//                new GridCellFilter.BooleanRepresentation(FontAwesome.THUMBS_UP, "yes"),
//                new GridCellFilter.BooleanRepresentation(FontAwesome.THUMBS_DOWN, "nope"));
        // set country combo with custom caption
    }

    /**
     * uses the inbuild alignments
     *
     * @param grid
     */
    private void initColumnAlignments(final Grid grid) {
        grid.setCellStyleGenerator(new CellStyleGenerator() {
            @Override
            public String getStyle(final CellReference cellReference) {
                if (cellReference.getPropertyId()
                        .equals("stt") || cellReference.getPropertyId()
                        .equals("action")) {
                    return GridUtil.ALIGN_CELL_CENTER;
//                } else if (cellReference.getPropertyId()
//                        .equals("birthday")) {
//                    return GridUtil.ALIGN_CELL_CENTER;
                } else {
                    return null;
                }
            }
        });
    }

    public class GridComponent extends org.vaadin.gridutil.cell.CellFilterComponent {

        private Component component;

        public GridComponent(Component component) {
            this.component = component;
        }

        @Override
        public void triggerUpdate() {
        }

        @Override
        public Component layoutComponent() {
            return component;
        }

        @Override
        public void clearFilter() {
        }

    }

    public GeneratedPropertyContainer getWrappingContainer() {
        return wrappingContainer;
    }

    public void setWrappingContainer(GeneratedPropertyContainer wrappingContainer) {
        this.wrappingContainer = wrappingContainer;
    }

}
