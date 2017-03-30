package com.smartgxt.showcase.client;

import java.util.Date;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.showcase.shared.Post;
import com.smartgxt.showcase.shared.PostProperties;
import com.smartgxt.ui.client.data.RpcPagingLoader;
import com.smartgxt.ui.client.desktop.ui.taskbar.IsTaskBarWindow;
import com.smartgxt.ui.client.grids.LiveGrid;

@Prototypeble(deffered = true)
public class LiveGridWindow extends Window implements IsTaskBarWindow {

	public LiveGridWindow() {
		setHeadingText("LiveGrid Window");
		setSize("500px", "400px");
		setMaximizable(true);
		setMinimizable(true);
		ListStore<Post> store = new ListStore<Post>(
				new ModelKeyProvider<Post>() {
					@Override
					public String getKey(Post item) {
						return "" + item.getId();
					}
				});

		final RpcPagingLoader<Post> gridLoader = new RpcPagingLoader<Post>(
				"com.smartgxt.showcase.server.GetPostsExecuter");
		gridLoader.setRemoteSort(true);

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
		dateColumn.setCell(new DateCell(DateTimeFormat
				.getFormat(PredefinedFormat.DATE_SHORT)) {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					Date value, SafeHtmlBuilder sb) {
				String style = "style='color: red" + "'";
				sb.appendHtmlConstant("<span " + style + ">");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</span>");
			}
		});

		LiveGrid<Post> view = new LiveGrid<Post>(store) {
			@Override
			protected void onAfterFirstAttach() {
				super.onAfterFirstAttach();
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						gridLoader.load(0, getView().getCacheSize());
					}
				});
			}
		};
		view.setLoadMask(true);
		view.setLoader(gridLoader);

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

	}
}
