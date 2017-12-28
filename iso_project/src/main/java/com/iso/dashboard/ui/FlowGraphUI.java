package com.iso.dashboard.ui;

import com.iso.dashboard.dto.CStep;
import com.vaadin.annotations.Theme;
import com.vaadin.graph.GraphExplorer;
import com.vaadin.graph.layout.JungCircleLayoutEngine;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

@Theme("codeGraph")
public class FlowGraphUI extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private SimpleGraphRepositoryImpl graphRepo;
    private GraphExplorer<?, ?> graph;

    public FlowGraphUI(List<CStep> cSteps, int width, int height) {
        setSizeFull();
        graphRepo = createGraphRepository(cSteps);
        graph = new GraphExplorer<NodeImpl, ArcImpl>(graphRepo);
        graph.setSizeFull();
        addComponent(graph);
    }

    private SimpleGraphRepositoryImpl createGraphRepository(List<CStep> cSteps) {
        SimpleGraphRepositoryImpl repo = new SimpleGraphRepositoryImpl();
        int index = 0;
        for (CStep cStep : cSteps) {
            if (cSteps.indexOf(cStep) == 0) {
                repo.addNode("node" + cStep.getId(), cStep.getCode() + "-" + cStep.getName());
                repo.setHomeNodeId("node" + cStep.getId());
            } else if (cStep.getBanchId() != null) {
                repo.addNode("node" + cStep.getId(), cStep.getCode() + "-" + cStep.getName());
                repo.joinNodes("node" + cStep.getId(), "node" + cStep.getBanchId(), "edge" + cStep.getId() + cStep.getBanchId(), "").setStyle("thin-blue");
            } else {
                index = cSteps.indexOf(cStep);
                repo.addNode("node" + cStep.getId(), cStep.getCode() + "-" + cStep.getName());
                if (index > 0) {
                    CStep cs = cSteps.get(index - 1);
                    repo.joinNodes("node" + cStep.getId(), "node" + cs.getId(), "edge" + cStep.getId() + cs.getId(), "").setStyle("thin-blue");
                }
            }
        }
        return repo;
    }
}
