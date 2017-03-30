package com.smartgxt.ui.client.grids.columns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.ValueProvider;

public class ColumnConfig<M, N> extends
		com.sencha.gxt.widget.core.client.grid.ColumnConfig<M, N> {

	public ColumnConfig(ValueProvider<? super M, N> valueProvider) {
		super(valueProvider);
		// TODO Auto-generated constructor stub
	}

	public ColumnConfig(ValueProvider<? super M, N> valueProvider, int width) {
		super(valueProvider, width);
		// TODO Auto-generated constructor stub
	}

	public ColumnConfig(ValueProvider<? super M, N> valueProvider, int width,
			SafeHtml header) {
		super(valueProvider, width, header);
		// TODO Auto-generated constructor stub
	}

	public ColumnConfig(ValueProvider<? super M, N> valueProvider, int width,
			String header) {
		super(valueProvider, width, header);
		// TODO Auto-generated constructor stub
	}
}
