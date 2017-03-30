package com.smartgxt.showcase.client;

import java.util.Date;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.showcase.shared.Post;
import com.smartgxt.showcase.shared.PostProperties;
import com.smartgxt.ui.client.desktop.ui.taskbar.IsTaskBarWindow;
import com.smartgxt.ui.client.grids.RpcPagingGrid;

@Prototypeble(deffered = true)
public class PagingGridWindow extends Window implements IsTaskBarWindow {

	public PagingGridWindow() {
		setHeadingText("PagingGrid Window");
		setSize("500px", "400px");
		setMaximizable(true);
		setMinimizable(true);

		PostProperties props = GWT.create(PostProperties.class);

		ColumnConfig<Post, String> forumColumn = new ColumnConfig<Post, String>(
				props.forum(), 150, "Forum");
		ColumnConfig<Post, String> usernameColumn = new ColumnConfig<Post, String>(
				props.username(), 150, "Username");
		ColumnConfig<Post, String> subjectColumn = new ColumnConfig<Post, String>(
				props.subject(), 150, "Subject");
		subjectColumn.setCell(new AbstractCell<String>() {

			@Override
			public void render(Context context, String value, SafeHtmlBuilder sb) {
				String style = "style='color: red" + "'";
				String v = value;
				sb.appendHtmlConstant("<span " + style
						+ " qtitle='Change' qtip='" + v + "'>" + v + "</span>");
			}

		});

		ColumnConfig<Post, Date> dateColumn = new ColumnConfig<Post, Date>(
				props.date(), 150, "Date");

		RpcPagingGrid<Post> view = new RpcPagingGrid<Post>(
				"com.smartgxt.showcase.server.GetPostsExecuter", 50);
		view.setLoadMask(true);

		add(view);

		view.setAllowTextSelection(true);

		Menu menu = new Menu();
		menu.add(new MenuItem("text1"));
		menu.add(new MenuItem("text2"));

		view.setContextMenu(menu);

		view.getColumnModel().getColumns().add(forumColumn);
		view.getColumnModel().getColumns().add(usernameColumn);
		view.getColumnModel().getColumns().add(subjectColumn);
		view.getColumnModel().getColumns().add(dateColumn);

		
		StringFilter<Post> nameFilter = new StringFilter<Post>(props.username());
		
		GridFilters<Post> filters = new GridFilters<Post>();
	    filters.initPlugin(view.getGrid());
	    filters.setLocal(false);
	    filters.addFilter(nameFilter);

		// liveGridView.getHeader().addHandler(new HeaderMouseDownHandler(){
		//
		// @Override
		// public void onHeaderMouseDown(HeaderMouseDownEvent event) {
		// System.out.println("HeaderMouseDownEvent");
		//
		// }
		//
		// }, HeaderMouseDownEvent.getType());
	}
}
