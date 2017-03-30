package com.smartgxt.client.ui.widgets.desktop.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.BoxComponentEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.DragEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.PortalEvent;
import com.extjs.gxt.ui.client.event.ResizeEvent;
import com.extjs.gxt.ui.client.fx.Resizable;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.Element;
import com.smartgxt.client.ui.widgets.custom.Portal;

/**
 * @author Anton Alexeyev
 * 
 */
// TODO refactoring
public class Dashboard extends Portal {

	private List<InformationWidget> panels;

	private HashMap<String, Integer> columns;
	private HashMap<String, Integer> rows;

	public Dashboard() {
		super(0);
		setSpacing(5);
		panels = new ArrayList<InformationWidget>();
		columns = new HashMap<String, Integer>();
		rows = new HashMap<String, Integer>();
		extendState();
		addColumn();

		addListener(Events.Drop, new Listener<PortalEvent>() {

			@Override
			public void handleEvent(PortalEvent be) {
				getState().put(be.getPortlet().getStateId() + ".column",
						be.getColumn());

				dumpRowsState();

				// getState().put(be.getPortlet().getStateId() + ".row",
				// be.getRow());
				saveState();

				// getItem(be.getColumn()).setAutoHeight(true);
				// getItem(be.getColumn()).layout();

				Resizable r = getItem(be.getColumn()).getData("sgxt.resizable");
				if (r != null)
					r.syncHandleHeight();
			}
		});

	}

	private void dumpRowsState() {
		for (LayoutContainer l : getItems()) {
			int i = 0;
			for (Component c : l.getItems()) {
				getState().put(c.getStateId() + ".row", i);
				i++;
			}
		}
	}

	private void extendState() {
		addListener(Events.StateRestore, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				final Object obj = getState().get("numColumns");
				if (obj != null) {
					int num = (Integer) obj;
					for (int i = 0; i < num - 1; i++) {
						addColumn();
						final Object obj_w = getState().get("column" + i);
						if (obj_w != null)
							setColumnWidth(i,
									Double.parseDouble(obj_w.toString()));
					}

					for (int i = 0; i < num; i++) {
						final Object obj_w = getState().get("column" + i);
						if (obj_w != null)
							setColumnWidth(i,
									Double.parseDouble(obj_w.toString()));
					}

					for (InformationWidget panel : panels) {

						Integer column = (Integer) getState().get(
								panel.getStateId() + ".column");
						if (column != null) {
							columns.put(panel.getStateId(), column);
						}

						Integer row = (Integer) getState().get(
								panel.getStateId() + ".row");
						if (row != null) {
							rows.put(panel.getStateId(), row);
						}
					}
				}

			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LinkedHashMap sortHashMapByValuesD(Map passedMap) {
		List mapKeys = new ArrayList(passedMap.keySet());
		List mapValues = new ArrayList(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap sortedMap = new LinkedHashMap();

		Iterator valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((String) key, (Integer) val);
					break;
				}

			}

		}
		return sortedMap;
	}

	@Override
	protected void afterRender() {
		super.afterRender();

		if (rows.size() != panels.size()) {
			defaultSizeColumns();
			for (InformationWidget panel : panels) {
				addPortlet(panel, 0);
			}
		}

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Integer> map = sortHashMapByValuesD(rows);
		for (String s : map.keySet()) {
			Integer col = columns.get(s);
			if (col == null)
				col = 0;
			addPortlet(foundPanleById(s), col);
		}
	}

	private InformationWidget foundPanleById(String id) {
		for (InformationWidget panel : panels) {
			if (panel.getStateId().equals(id))
				return panel;
		}
		return null;
	}

	@Override
	public void render(Element target) {
		super.render(target);
	}

	@Override
	public void add(final Portlet portlet, int column) {
		// TODO assertion for InformationPanel type
		// super.add(portlet, column);
		panels.add((InformationWidget) portlet);
		portlet.addListener(Events.Resize, new Listener<BoxComponentEvent>() {
			@Override
			public void handleEvent(BoxComponentEvent be) {
				Integer i = portlet.getData("sgxt.column");
				if (i != null) {
					Component c = getItem(i);
					if (c != null) {
						Resizable r = getItem(i).getData("sgxt.resizable");
						if (r != null)
							r.syncHandleHeight();
					}
				}

			}
		});

	}

	public void addColumn() {
		LayoutContainer l = new LayoutContainer();
		l.addStyleName("x-portal x-portal-column");
		l.setStyleAttribute("minHeight", "20px");
		l.setStyleAttribute("padding", getSpacing() + "px 0 0 " + getSpacing()
				+ "px");
		l.setLayout(new RowLayout());
		l.setLayoutOnChange(true);
		add(l);

		if (getItemCount() > 1)
			addResizer(getItem(getItemCount() - 2));

		setNumColumns(getNumColumns() + 1);

		getState().put("numColumns", getNumColumns());
		saveState();

		// defaultSizeColumns();

		if (rendered)
			layout();
	}

	public void addResizer(final LayoutContainer l) {
		final Resizable resizableComponent = new Resizable(l, "e");
		resizableComponent.addListener(Events.ResizeEnd,
				new Listener<ResizeEvent>() {

					@Override
					public void handleEvent(ResizeEvent be) {
						double size = (double) l.getWidth() / getWidth();
						double sizeOthers = ((double) (1 - size))
								/ (getItemCount() - 1);
						int i = 0;
						for (LayoutContainer c : getItems()) {
							if (c.equals(l)) {
								setColumnWidth(i, size);
							} else {
								setColumnWidth(i, sizeOthers);
							}
							i++;

						}
						l.setAutoHeight(true);
						((LayoutContainer) getParent()).layout();

					}
				});
		l.setData("sgxt.resizable", resizableComponent);
	}

	@Override
	public void setColumnWidth(int colIndex, double width) {
		super.setColumnWidth(colIndex, width);

		getState().put("column" + colIndex, width);
		saveState();
	}

	@Override
	protected void onDragEnd(DragEvent de) {
		super.onDragEnd(de);
		layout();
	}

	public void removeColumn() {

		List<InformationWidget> panelsTodelete = new ArrayList<InformationWidget>();

		final int index = getItemCount() - 1;
		LayoutContainer lc = getItem(index);

		for (Component c : lc.getItems()) {
			if (c instanceof InformationWidget) {
				final InformationWidget p = (InformationWidget) c;
				panelsTodelete.add(p);
			}
		}

		for (InformationWidget p : panelsTodelete) {
			reparentPortlet(p, index, index - 1);
		}

		LayoutContainer lcPrev = getItem(index - 1);
		Resizable r = lcPrev.getData("sgxt.resizable");
		r.release();
		r = null;
		remove(lc);

		setNumColumns(getNumColumns() - 1);

		getState().put("numColumns", getNumColumns());

		dumpRowsState();

		saveState();

		// defaultSizeColumns();

		if (rendered)
			layout();
	}

	public void addPortlet(Portlet portlet, int column) {
		super.add(portlet, column);
		portlet.setData("sgxt.column", column);
		getState().put(portlet.getStateId() + ".column", column);
		saveState();
	}

	public void reparentPortlet(Portlet portlet, int colFrom, int colTo) {
		super.remove(portlet, colFrom);
		super.add(portlet, colTo);

		getState().put(portlet.getStateId() + ".column", colTo);
		saveState();
	}

	public void defaultSizeColumns() {
		double columnWidth = 100.0 / (double) getNumColumns() / 100.0;
		for (int i = 0; i < getNumColumns(); i++) {
			setColumnWidth(i, columnWidth);
		}
		if (rendered)
			layout();
	}

	public native int getNumColumns() /*-{
		return this.@com.extjs.gxt.ui.client.widget.custom.Portal::numColumns;
	}-*/;

	public native void setNumColumns(int i) /*-{
		this.@com.extjs.gxt.ui.client.widget.custom.Portal::numColumns = i;
	}-*/;

	public List<InformationWidget> getPanels() {
		return panels;
	}
}
