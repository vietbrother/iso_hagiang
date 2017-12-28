package com.iso.dashboard.ui.container;

import com.iso.dashboard.dto.CTask;
import com.iso.dashboard.service.TaskOrgMngService;
import com.iso.dashboard.utils.DateUtil;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.treegrid.container.Measurable;

import com.vaadin.data.Collapsible;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.Resource;
import java.util.List;

public class TreeTaskContainer extends HierarchicalContainer implements Collapsible, Measurable {

    static final String PROPERTY_NAME = "Name";
    static final String PROPERTY_START_TIME = "Start Time";
    static final String PROPERTY_END_TIME = "End Time";
    static final String PROPERTY_BUDGET = "Budget";

    public TreeTaskContainer(List<CTask> lstDataSource) {
        addContainerProperty(PROPERTY_NAME, String.class, "");
        addContainerProperty(PROPERTY_START_TIME, String.class, "");
        addContainerProperty(PROPERTY_END_TIME, String.class, "");
        addContainerProperty(PROPERTY_BUDGET, String.class, null);

        for (CTask task : lstDataSource) {
            Item item = addItem(task);
            item.getItemProperty(PROPERTY_NAME).setValue(task.getTaskName());
            item.getItemProperty(PROPERTY_START_TIME).setValue(DateUtil.date2ddMMyyyyHHMMss(task.getStartTime()));
            item.getItemProperty(PROPERTY_END_TIME).setValue(DateUtil.date2ddMMyyyyHHMMss(task.getEndTime()));
            item.getItemProperty(PROPERTY_BUDGET).setValue(String.valueOf(task.getBudget()));

        }

    }

    private Object addItem(Object[] values) {
        Item item = addItem((Object) values);
        setProperties(item, values);
        return values;
    }

    private Object addChild(Object[] values, Object parentId) {
        Item item = addItemAfter(parentId, values);
        setProperties(item, values);
        setParent(values, parentId);
        return values;
    }

    private Object addChild(CTask values, CTask parentId) {
        Item item = addItemAfter(parentId, values);
        setProperties(item, values);
        setParent(values, parentId);
        return values;
    }

    private void setProperties(Item item, Object[] values) {
        item.getItemProperty(PROPERTY_NAME).setValue(values[0]);
        item.getItemProperty(PROPERTY_START_TIME).setValue(values[1]);
        item.getItemProperty(PROPERTY_END_TIME).setValue(values[2]);
        item.getItemProperty(PROPERTY_BUDGET).setValue(values[3]);
    }

    private void setProperties(Item item, CTask value) {
        item.getItemProperty(PROPERTY_NAME).setValue(value.getTaskName());
        item.getItemProperty(PROPERTY_START_TIME).setValue(DateUtil.date2ddMMyyNoSlashString(value.getStartTime()));
        item.getItemProperty(PROPERTY_END_TIME).setValue(DateUtil.date2ddMMyyNoSlashString(value.getEndTime()));
        item.getItemProperty(PROPERTY_BUDGET).setValue(String.valueOf(value.getBudget()));
    }

    @Override
    public void setCollapsed(Object itemId, boolean collapsed) {
        expandedNodes.put(itemId, !collapsed);

        if (collapsed) {
            // remove children
            removeChildrenRecursively(itemId);
        } else {
            // lazy load children
            addChildren(itemId);
        }
    }

    private void addChildren(Object itemId) {
        CTask parentItem = (CTask) itemId;
        List<CTask> lstChild = TaskOrgMngService.getInstance().listTaskOrg("", String.valueOf(parentItem.getTaskId()));
        for (CTask child : lstChild) {
            Object childId = addChild(child, parentItem);
            if (Boolean.TRUE.equals(expandedNodes.get(childId))) {
                addChildren(childId);
            }
        }
    }

    private boolean removeChildrenRecursively(Object itemId) {
        boolean success = true;
        Collection<?> children2 = getChildren(itemId);
        if (children2 != null) {
            Object[] array = children2.toArray();
            for (int i = 0; i < array.length; i++) {
                boolean removeItemRecursively = removeItemRecursively(
                        this, array[i]);
                if (!removeItemRecursively) {
                    success = false;
                }
            }
        }
        return success;

    }

    @Override
    public boolean hasChildren(Object itemId) {
        return true;
    }

    private Map<Object, Boolean> expandedNodes = new HashMap<>();

    @Override
    public boolean isCollapsed(Object itemId) {
        return !Boolean.TRUE.equals(expandedNodes.get(itemId));
    }

    @Override
    public int getDepth(Object itemId) {
        int depth = 0;
        while (!isRoot(itemId)) {
            depth++;
            itemId = getParent(itemId);
        }
        return depth;
    }
}
