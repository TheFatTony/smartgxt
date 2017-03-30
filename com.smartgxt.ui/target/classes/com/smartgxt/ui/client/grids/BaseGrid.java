package com.smartgxt.ui.client.grids;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.BodyScrollEvent.BodyScrollHandler;
import com.sencha.gxt.widget.core.client.event.CellClickEvent.CellClickHandler;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.CellMouseDownEvent.CellMouseDownHandler;
import com.sencha.gxt.widget.core.client.event.HeaderClickEvent.HeaderClickHandler;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent.HeaderContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.HeaderDoubleClickEvent.HeaderDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.HeaderMouseDownEvent.HeaderMouseDownHandler;
import com.sencha.gxt.widget.core.client.event.ReconfigureEvent.ReconfigureHandler;
import com.sencha.gxt.widget.core.client.event.RefreshEvent.RefreshHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent.RowDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.RowMouseDownEvent.RowMouseDownHandler;
import com.sencha.gxt.widget.core.client.event.SortChangeEvent.SortChangeHandler;
import com.sencha.gxt.widget.core.client.event.ViewReadyEvent.ViewReadyHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.Callback;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.menu.Menu;

public class BaseGrid<M> extends FramedPanel {

	protected Grid<M> grid;

	protected VerticalLayoutContainer con;

	public BaseGrid(ListStore<M> store, ColumnModel<M> cm, GridView<M> view) {
		initGUI(store, cm, view);

	}

	public BaseGrid(ListStore<M> store, GridView<M> view) {
		initGUI(store, new ModifiableColumnModel<M>(), view);

	}

	/**
	 * Sets the widget's context menu.
	 * 
	 * @param menu
	 *            the context menu
	 */
	public void setContextMenu(Menu menu) {
		grid.setContextMenu(menu);
	}

	private void initGUI(ListStore<M> store, ColumnModel<M> cm, GridView<M> view) {
		grid = new Grid<M>(store, cm, view);

		setCollapsible(false);
		setHeaderVisible(false);
		setBorders(false);
		// addStyleName("margin-10");

		con = new VerticalLayoutContainer();
		con.setBorders(false);
		con.add(grid, new VerticalLayoutData(1, 1));
		setWidget(con);
	}

	public Grid<M> getGrid() {
		return grid;
	}

	public HandlerRegistration addBodyScrollHandler(BodyScrollHandler handler) {
		return grid.addBodyScrollHandler(handler);
	}

	public HandlerRegistration addCellClickHandler(CellClickHandler handler) {
		return grid.addCellClickHandler(handler);
	}

	public HandlerRegistration addCellDoubleClickHandler(
			CellDoubleClickHandler handler) {
		return grid.addCellDoubleClickHandler(handler);
	}

	public HandlerRegistration addCellMouseDownHandler(
			CellMouseDownHandler handler) {
		return grid.addCellMouseDownHandler(handler);
	}

	public HandlerRegistration addHeaderClickHandler(HeaderClickHandler handler) {
		return grid.addHeaderClickHandler(handler);
	}

	public HandlerRegistration addHeaderContextMenuHandler(
			HeaderContextMenuHandler handler) {
		return grid.addHeaderContextMenuHandler(handler);
	}

	public HandlerRegistration addHeaderDoubleClickHandler(
			HeaderDoubleClickHandler handler) {
		return grid.addHeaderDoubleClickHandler(handler);
	}

	public HandlerRegistration addHeaderMouseDownHandler(
			HeaderMouseDownHandler handler) {
		return grid.addHeaderMouseDownHandler(handler);
	}

	public HandlerRegistration addReconfigureHandler(ReconfigureHandler handler) {
		return grid.addReconfigureHandler(handler);
	}

	public HandlerRegistration addRefreshHandler(RefreshHandler handler) {
		return grid.addRefreshHandler(handler);
	}

	public HandlerRegistration addRowClickHandler(RowClickHandler handler) {
		return grid.addRowClickHandler(handler);
	}

	public HandlerRegistration addRowDoubleClickHandler(
			RowDoubleClickHandler handler) {
		return grid.addRowDoubleClickHandler(handler);
	}

	public HandlerRegistration addRowMouseDownHandler(
			RowMouseDownHandler handler) {
		return grid.addRowMouseDownHandler(handler);
	}

	public HandlerRegistration addSortChangeHandler(SortChangeHandler handler) {
		return grid.addSortChangeHandler(handler);
	}

	public HandlerRegistration addViewReadyHandler(ViewReadyHandler handler) {
		return grid.addViewReadyHandler(handler);
	}

	@Override
	public void focus() {
		grid.focus();
	}

	/**
	 * Returns the column model.
	 * 
	 * @return the column model
	 */
	public ColumnModel<M> getColumnModel() {
		return grid.getColumnModel();
	}

	/**
	 * Returns the time in ms after the rows get rendered.
	 * 
	 * @return the lazy row rendering time
	 */
	public int getLazyRowRender() {
		return grid.getLazyRowRender();
	}

	/**
	 * Returns the loader.
	 * 
	 * @return the loader
	 */
	public ListLoader<?, ?> getLoader() {
		return grid.getLoader();
	}

	/**
	 * Returns the minimum column width.
	 * 
	 * @return the min width in pixels
	 */
	public int getMinColumnWidth() {
		return grid.getMinColumnWidth();
	}

	/**
	 * Returns the grid's selection model.
	 * 
	 * @return the selection model
	 */
	public GridSelectionModel<M> getSelectionModel() {
		return grid.getSelectionModel();
	}

	/**
	 * Returns the grid's store.
	 * 
	 * @return the store
	 */
	public ListStore<M> getStore() {
		return grid.getStore();
	}

	/**
	 * Returns the grid's view.
	 * 
	 * @return the grid view
	 */
	public GridView<M> getView() {
		return grid.getView();
	}

	/**
	 * Returns true if column reordering is enabled.
	 * 
	 * @return true if enabled
	 */
	public boolean isColumnReordering() {
		return grid.isColumnReordering();
	}

	/**
	 * Returns true if column resizing is enabled.
	 * 
	 * @return true if resizing is enabled
	 */
	public boolean isColumnResize() {
		return grid.isColumnResize();
	}

	/**
	 * Returns true if the header is hidden.
	 * 
	 * @return true for hidden
	 */
	public boolean isHideHeaders() {
		return grid.isHideHeaders();
	}

	/**
	 * Returns true if the load mask in enabled.
	 * 
	 * @return the load mask state
	 */
	public boolean isLoadMask() {
		return grid.isLoadMask();
	}

	/**
	 * Returns true if the view is ready.
	 * 
	 * @return the view ready state
	 */
	public boolean isViewReady() {
		return grid.isViewReady();
	}

	@Override
	public void onBrowserEvent(Event ce) {
		grid.onBrowserEvent(ce);

	}

	/**
	 * Reconfigures the grid to use a different Store and Column Model. The View
	 * will be bound to the new objects and refreshed.
	 * 
	 * @param store
	 *            the new store
	 * @param cm
	 *            the new column model
	 */
	public void reconfigure(ListStore<M> store, ColumnModel<M> cm) {
		grid.reconfigure(store, cm);
	}

	@Override
	public void setAllowTextSelection(boolean enable) {
		grid.setAllowTextSelection(enable);
	}

	/**
	 * True to enable column reordering via drag and drop (defaults to false).
	 * 
	 * @param enableColumnReorder
	 *            true to enable
	 */
	public void setColumnReordering(boolean enableColumnReorder) {
		grid.setColumnReordering(enableColumnReorder);
	}

	/**
	 * Sets whether columns may be resized (defaults to true).
	 * 
	 * @param enableColumnResize
	 *            true to allow column resizing
	 */
	public void setColumnResize(boolean enableColumnResize) {
		grid.setColumnResize(enableColumnResize);
	}

	/**
	 * Sets whether the header should be hidden (defaults to false).
	 * 
	 * @param hideHeaders
	 *            true to hide the header
	 */
	public void setHideHeaders(boolean hideHeaders) {
		grid.setHideHeaders(hideHeaders);
	}

	/**
	 * Sets the time in ms after the row gets rendered (defaults to 10). 0 means
	 * that the rows get rendered as soon as the grid gets rendered.
	 * 
	 * @param lazyRowRender
	 *            the time in ms after the rows get rendered.
	 */
	public void setLazyRowRender(int lazyRowRender) {
		grid.setLazyRowRender(lazyRowRender);
	}

	/**
	 * Sets the loader.
	 * 
	 * @param loader
	 *            the loader
	 */
	public void setLoader(ListLoader<?, ?> loader) {
		grid.setLoader(loader);
	}

	/**
	 * Sets whether a load mask should be displayed during load operations
	 * (defaults to false).
	 * 
	 * @param loadMask
	 *            true to show a mask
	 */
	public void setLoadMask(boolean loadMask) {
		grid.setLoadMask(loadMask);
	}

	/**
	 * The minimum width a column can be resized to (defaults to 25).
	 * 
	 * @param minColumnWidth
	 *            the min column width
	 */
	public void setMinColumnWidth(int minColumnWidth) {
		grid.setMinColumnWidth(minColumnWidth);
	}

	/**
	 * Sets the grid selection model.
	 * 
	 * @param sm
	 *            the selection model
	 */
	public void setSelectionModel(GridSelectionModel<M> sm) {
		grid.setSelectionModel(sm);
	}

	/**
	 * Sets the view's grid (pre-render).
	 * 
	 * @param view
	 *            the view
	 */
	public void setView(GridView<M> view) {
		grid.setView(view);
	}

	/**
	 * Navigate in the requested direction to the next selectable cell, given
	 * the row, column and step.
	 * 
	 * @param row
	 *            the starting row index
	 * @param col
	 *            the starting column index
	 * @param step
	 *            the step size and direction
	 * @param callback
	 *            a callback that determines whether the given cell is
	 *            selectable
	 * @return the next cell or <code>null</code> if no cell matches the
	 *         criteria
	 */
	public GridCell walkCells(int row, int col, int step, Callback callback) {
		return grid.walkCells(row, col, step, callback);
	}

}
