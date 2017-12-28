package com.iso.dashboard.component;

import com.iso.dashboard.dto.User_;
import com.iso.dashboard.service.UserMngService;
import com.iso.dashboard.utils.DateUtil;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.CellReference;
import com.vaadin.ui.Grid.CellStyleGenerator;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.Renderer;
import org.vaadin.gridutil.GridUtil;
import org.vaadin.gridutil.cell.GridCellFilter;
import org.vaadin.gridutil.cell.RangeCellFilterComponent;
import org.vaadin.gridutil.renderer.EditDeleteButtonValueRenderer;
import java.text.SimpleDateFormat;
import java.util.List;

public class CustomPageGrid extends VerticalLayout {

    private GridCellFilter filter;
    private String[] PAGE_SIZE_LIST = {"5", "10", "15", "20", "25"};
    private String PAGE_DEFAULT = "5";
    private NativeSelect itemsPerPageSelect = new NativeSelect();

    public CustomPageGrid() {

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        Grid grid = genGrid();
        layout.addComponent(grid);
        layout.setExpandRatio(grid, 1);

        addComponent(layout);

    }

    private Grid genGrid() {
        // init Grid
        final Grid grid = new Grid();
        grid.setSizeFull();

        // init Container
//        BeanItemContainer<Users> container = new BeanItemContainer<Users>(User_.class);
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("username", String.class, null);
        container.addContainerProperty("email", String.class, null);
        container.addContainerProperty("phone", String.class, null);
        container.addContainerProperty("birthday", String.class, null);
        List<User_> lstUser = UserMngService.getInstance().listUsers(null);

        // wrap the bean item container so we can generated a fake header column
        GeneratedPropertyContainer wrappingContainer = new GeneratedPropertyContainer(container);
        wrappingContainer.addGeneratedProperty("stt", new PropertyValueGenerator<Long>() {
            private long index = 1;

            @Override
            public Long getValue(Item item, Object itemId, Object propertyId) {
                return index++;
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

//        wrappingContainer.addGeneratedProperty("action", new PropertyValueGenerator<Object>() {
//
//            @Override
//            public Object getValue(Item item, Object itemId, Object propertyId) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public Class<Object> getType() {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
        // assign the data source to the grid and set desired column order
        grid.setContainerDataSource(wrappingContainer);

        // freeze the fake header column to prevent it from scrolling horizontally
//        grid.setFrozenColumnCount(1);

        // add dummy data
        for (User_ u : lstUser) {
            Item itemId = container.addItem(u);
            itemId.getItemProperty("username").setValue(u.getUserName());
            itemId.getItemProperty("email").setValue(u.getEmail());
            itemId.getItemProperty("phone").setValue(u.getPhone());
            itemId.getItemProperty("birthday").setValue(DateUtil.date2ddMMyyyyHHMMss(u.getBirthDay()));
        }

//        container.addAll(lstUser);
//        grid.setContainerDataSource(container);
        grid.setColumnOrder("stt", "username", "email", "phone");

        setColumnRenderes(grid);
        grid.setColumns("stt", "action", "username", "email", "phone", "birthday");

        initFilter(grid);
        initFooterRow(grid, container);
//        initExtraHeaderRow(grid);

        initColumnAlignments(grid);
        grid.setHeightMode(HeightMode.ROW);
        int heigh = container.size() == 0 ? 1 : container.size();
        if (heigh > Integer.valueOf(PAGE_DEFAULT)) {
            heigh = Integer.valueOf(PAGE_DEFAULT);
        }
        grid.setHeightByRows(heigh);
//        grid.setHeaderVisible(true);
        return grid;
    }

    private void setColumnRenderes(final Grid grid) {

        // tweak it a bit - definitely needs more tweaking
        grid.getColumn("stt").setHeaderCaption("STT").setHidable(false)
                .setEditable(false).setResizable(false);
        grid.getColumn("stt").setWidth(45);
        grid.getHeaderRow(0).setStyleName(GridUtil.ALIGN_CELL_CENTER);
        grid.getColumn("username").setHeaderCaption("Ten dang nhap");

        grid.getColumn("action")
                .setRenderer(new EditDeleteButtonValueRenderer(new EditDeleteButtonValueRenderer.EditDeleteButtonClickListener() {

                    @Override
                    public void onEdit(final RendererClickEvent event) {
                        Notification.show(event.getItemId()
                                .toString() + " want's to get edited", Type.HUMANIZED_MESSAGE);
                        User_ u = (User_) event.getItemId();
                    }

                    @Override
                    public void onDelete(final RendererClickEvent event) {
                        Notification.show(event.getItemId()
                                .toString() + " want's to get deleted", Type.WARNING_MESSAGE);
                    }
                }))
                .setWidth(100);


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
    private void initFooterRow(final Grid grid, final IndexedContainer container) {
        final FooterRow footerRow = grid.appendFooterRow();
        footerRow.getCell("stt")
                .setHtml("total:");
        footerRow.join("username", "email", "phone");
        // inital total count
        footerRow.getCell("username")
                .setHtml("<b>" + container.getItemIds()
                        .size() + "</b>");

        for (String numberSize : PAGE_SIZE_LIST) {
            itemsPerPageSelect.addItem(numberSize);
        }
        itemsPerPageSelect.setImmediate(true);
        itemsPerPageSelect.setNullSelectionAllowed(false);
        itemsPerPageSelect.setWidth("80px");
        itemsPerPageSelect.setHeight("34px");
        itemsPerPageSelect.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -2255853716069800092L;

            @Override
            public void valueChange(
                    com.vaadin.data.Property.ValueChangeEvent event) {
                int height = grid.getContainerDataSource().size();
                if (height > Integer.valueOf(String.valueOf(event.getProperty().getValue()))) {
                    height = Integer.valueOf(String.valueOf(event.getProperty().getValue()));
                }
                grid.setHeightByRows(height);
            }
        });
        itemsPerPageSelect.select(PAGE_DEFAULT);

        footerRow.getCell("birthday")
                .setComponent(itemsPerPageSelect);
        // filter change count recalculate
        container.addItemSetChangeListener(new ItemSetChangeListener() {

            @Override
            public void containerItemSetChange(final ItemSetChangeEvent event) {
                footerRow.getCell("username")
                        .setHtml("<b>" + event.getContainer()
                                .getItemIds()
                                .size() + "</b>");
            }
        });
    }

    /**
     * initialize a GridCellFilter
     *
     * @param grid
     */
    private void initFilter(final Grid grid) {
        this.filter = new GridCellFilter(grid);
//        this.filter.setNumberFilter("stt");

        // simple filters
        this.filter.setTextFilter("username", true, true, "name starts with");
//        this.filter.setCustomFilter("stt", new GridComponent(new Label("STT")));
//        this.filter.setCustomFilter("email", new GridComponent(new Label("Email")));
//        this.filter.setCustomFilter("phone", new GridComponent(new Label("Phone")));

        RangeCellFilterComponent<DateField, HorizontalLayout> dateFilter = this.filter.setDateFilter("birthday", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), true);
        dateFilter.getSmallestField()
                .setParseErrorMessage("da ist was schief gegangen :)");

//        this.filter.setBooleanFilter("status",
//                new GridCellFilter.BooleanRepresentation(FontAwesome.THUMBS_UP, "yes"),
//                new GridCellFilter.BooleanRepresentation(FontAwesome.THUMBS_DOWN, "nope"));
        // set country combo with custom caption
    }

    /**
     * interacts with the GridCellFilter
     *
     * @param grid
     */
//    private void initExtraHeaderRow(final Grid grid) {
//        HeaderRow fistHeaderRow = grid.prependHeaderRow();
//        fistHeaderRow.join("id", "gender", "name", "bodySize");
//        fistHeaderRow.getCell("id")
//                .setHtml("GridCellFilter simplify the filter settings for a grid");
//        fistHeaderRow.join("birthday", "onFacebook", "country");
//        HorizontalLayout buttonLayout = new HorizontalLayout();
//        buttonLayout.setSpacing(true);
//        fistHeaderRow.getCell("birthday")
//                     .setComponent(buttonLayout);
//        Button clearAllFilters = new Button("clearAllFilters", new Button.ClickListener() {
//
//            @Override
//            public void buttonClick(final ClickEvent event) {
//                CustomPageGrid.this.filter.clearAllFilters();
//            }
//        });
//        clearAllFilters.setIcon(FontAwesome.TIMES);
//        clearAllFilters.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//        buttonLayout.addComponent(clearAllFilters);
//
//        final Button changeVisibility = new Button("changeVisibility");
//        changeVisibility.addClickListener(new Button.ClickListener() {
//
//            private boolean visibile = true;
//
//            @Override
//            public void buttonClick(final ClickEvent event) {
//                this.visibile = !this.visibile;
//                changeVisibility.setIcon(this.visibile ? FontAwesome.EYE_SLASH : FontAwesome.EYE);
//                CustomPageGrid.this.filter.setVisible(this.visibile);
//                Notification.show("changed visibility to: " + this.visibile + "! Sometimes it's working sometimes not - it's deprecated!", Type.ERROR_MESSAGE);
//            }
//        });
//        changeVisibility.setIcon(FontAwesome.EYE_SLASH);
//        changeVisibility.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//        buttonLayout.addComponent(changeVisibility);
//
//
//        final Button presetFilter = new Button("presetFilter");
//        presetFilter.addClickListener(new Button.ClickListener() {
//
//
//            @Override
//            public void buttonClick(final ClickEvent event) {
//                CellFilterComponent<TextField> filter = CustomPageGrid.this.filter.getCellFilter("name");
//                filter.getComponent()
//                      .setValue("eth");
//                filter.triggerUpdate();
//            }
//        });
//        presetFilter.setIcon(FontAwesome.PENCIL);
//        presetFilter.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//        buttonLayout.addComponent(presetFilter);
//
//        // listener's on filter
//        this.filter.addCellFilterChangedListener(new CellFilterChangedListener() {
//
//            @Override
//            public void changedFilter(final GridCellFilter cellFilter) {
//                Notification.show("cellFilter changed " + new Date().toLocaleString(), Type.TRAY_NOTIFICATION);
//            }
//        });
//    }
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
                } else if (cellReference.getPropertyId()
                        .equals("birthday")) {
                    return GridUtil.ALIGN_CELL_CENTER;
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

}
