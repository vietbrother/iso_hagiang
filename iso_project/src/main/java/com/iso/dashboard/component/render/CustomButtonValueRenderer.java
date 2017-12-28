package com.iso.dashboard.component.render;


import com.iso.dashboard.client.VCustomButtonValueRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * Add view, edit and delete buttons next to the value (value is rendered as HTML)
 *
 * @author Marten Prieß (http://www.non-rocket-science.com)
 * @version 1.0
 */
public class CustomButtonValueRenderer extends ClickableRenderer<String> {

	/**
	 * specify the {@link RendererClickListener} by a hint which Button is clicked
	 */
	public interface CustomButtonClickListener {

		/**
		 * get fired when editButton is clicked
		 * 
		 * @param event
		 *            clickEvent
		 */
		void onEdit(final RendererClickEvent event);

		/**
		 * get fired when deleteButton is clicked
		 * 
		 * @param event
		 *            clickEvent
		 */
		void onDelete(final RendererClickEvent event);
                void onView(final RendererClickEvent event);
                void onAssign(final RendererClickEvent event);
                void onReport(final RendererClickEvent event);

	}

	private final CustomButtonClickListener listener;

	/**
	 * "injects" a delete button in the cell
	 * 
	 * @param listener
	 *            that get triggered on click on both buttons
	 */
	public CustomButtonValueRenderer(final CustomButtonClickListener listener) {
		super(String.class);
		this.listener = listener;

		addClickListener(new RendererClickListener() {
                    @Override
                    public void click(final com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent event) {
                        switch (event.getRelativeX()) {
                            case VCustomButtonValueRenderer.EDIT_BITM:
                                listener.onEdit(event);
                                break;
                            case VCustomButtonValueRenderer.DELETE_BITM:
                                listener.onDelete(event);
                                break;
                            case VCustomButtonValueRenderer.VIEW_BITM:
                                listener.onView(event);
                                break;
                            case VCustomButtonValueRenderer.ASSIGN_BITM:
                                listener.onAssign(event);
                                break;
                            case VCustomButtonValueRenderer.REPORT_BITM:
                                listener.onReport(event);
                                break;
                            default:
                                break;
                        }
                    }
                });
	}

}
