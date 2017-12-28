package com.iso.dashboard.component;

import com.iso.dashboard.controller.HandlerButtonActionGrid;
import com.iso.dashboard.utils.BundleUtils;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.GeneratedPropertyContainer.GeneratedPropertyItem;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.CellReference;
import com.vaadin.ui.Grid.CellStyleGenerator;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.gridutil.GridUtil;
import org.vaadin.gridutil.cell.GridCellFilter;
import java.util.Arrays;
import java.util.List;
import org.vaadin.gridutil.renderer.DeleteButtonValueRenderer;

public class CustomGridEditRow extends Grid {

    private GridCellFilter filter;
    private final String[] PAGE_SIZE_LIST = {"5", "10", "15", "20", "25"};
    private final String PAGE_DEFAULT = "10";
    private final NativeSelect itemsPerPageSelect = new NativeSelect();
    private GeneratedPropertyContainer wrappingContainer;
    private Label totalRecords = new Label("", ContentMode.HTML);

    public CustomGridEditRow() {

    }

    public <T> void genGrid(BeanItemContainer container, String prefix,
            String[] columnsVisible, String[] columnsOrder, HandlerButtonActionGrid handler) {
        // init Grid
        this.totalRecords.setStyleName(ValoTheme.LABEL_BOLD);
        this.setSizeFull();
        this.setHeightMode(HeightMode.ROW);

        // wrap the bean item container so we can generated a fake header column
        wrappingContainer = createWrapContainer(container);

        // assign the data source to the grid and set desired column order
        this.setContainerDataSource(wrappingContainer);

        // freeze the fake header column to prevent it from scrolling horizontally
//        this.setFrozenColumnCount(1);
        java.lang.reflect.Field[] fields = container.getBeanType().getDeclaredFields();
        List<String> lstValView = Arrays.asList(columnsVisible);
        for (java.lang.reflect.Field f : fields) {
            if (!lstValView.contains(f.getName())) {
                this.removeColumn(f.getName());
            }
        }
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

        this.setEditorEnabled(true);
        this.setEditorSaveCaption(BundleUtils.getString("common.button.save"));
        this.setEditorCancelCaption(BundleUtils.getString("common.button.cancel"));
    }

    public GeneratedPropertyContainer createWrapContainer(BeanItemContainer container) {
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
        return wrappingContainer;
    }

    private void setColumnRenderes(final Grid grid, HandlerButtonActionGrid handler) {

        // tweak it a bit - definitely needs more tweaking
        grid.getColumn("stt").setHeaderCaption(BundleUtils.getString("common.table.index")).setHidable(false)
                .setEditable(false);
        grid.getColumn("stt").setWidth(60);

        grid.getColumn("action").setHeaderCaption(BundleUtils.getString("common.table.action"));

        grid.getColumn("action")
                .setRenderer(new DeleteButtonValueRenderer(new DeleteButtonValueRenderer.RendererClickListener() {

                    @Override
                    public void click(RendererClickEvent event) {
                        handler.actionDelete(event.getItemId());
                    }
                })).setWidth(100);

        grid.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent)
                    throws FieldGroup.CommitException {
                // Do nothing
                Notification.show("preCommit saved");
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent)
                    throws FieldGroup.CommitException {
                GeneratedPropertyItem item = (GeneratedPropertyItem) commitEvent.getFieldBinder().getItemDataSource();
                BeanItem bean = (BeanItem) item.getWrappedItem();
                handler.actionEdit(bean.getBean());
            }
        });
    }

    /**
     * generates a simple totalCount footer
     *
     * @param grid
     * @param container
     */
//    private void initFooterRow(final Grid grid, final BeanItemContainer<Users> container) {
    private void initFooterRow(final Grid grid, BeanItemContainer container, String[] columsVisible) {
        final FooterRow footerRow = grid.appendFooterRow();
//        footerRow.getCell("stt")
//                .setHtml("total:");
        footerRow.join(columsVisible);
        // inital total count
        HorizontalLayout controlBar = initControlBar(grid);
        footerRow.getCell("stt")
                .setComponent(controlBar);

        container.addItemSetChangeListener(new Container.ItemSetChangeListener() {

            @Override
            public void containerItemSetChange(final Container.ItemSetChangeEvent event) {
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
                    grid.setHeightByRows(height);
                }

                grid.setHeightByRows(height);
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

    public GeneratedPropertyContainer getWrappingContainer() {
        return wrappingContainer;
    }

    public void setWrappingContainer(GeneratedPropertyContainer wrappingContainer) {
        this.wrappingContainer = wrappingContainer;
    }

    public void initFilter(final Grid grid) {
        this.filter = new GridCellFilter(grid);
    }

    public GridCellFilter getFilter() {
        return filter;
    }

    public void setFilter(GridCellFilter filter) {
        this.filter = filter;
    }

}
