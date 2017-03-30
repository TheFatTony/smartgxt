package com.smartgxt.incubator.client;

//import com.extjs.gxt.ui.client.event.ButtonEvent;
//import com.extjs.gxt.ui.client.event.SelectionListener;
//import com.extjs.gxt.ui.client.widget.ContentPanel;
//import com.extjs.gxt.ui.client.widget.Window;
//import com.extjs.gxt.ui.client.widget.button.Button;
//import com.extjs.gxt.ui.client.widget.form.TextField;
//import com.google.gwt.core.client.Scheduler;
//import com.google.gwt.core.client.Scheduler.ScheduledCommand;
//import com.google.gwt.user.client.DeferredCommand;

public class TestCase {

    //	public TestCase(final ContentPanel parent) {
    //		WindowManagerHelper.replaceWindowManager(WindowManager.get());
    //
    //		final Window window11 = new Window();
    //		window11.setPlain(false);
    //		window11.setHeading("window11");
    //		window11.add(new TextField<String>());
    //		window11.setSize(500, 300);
    //
    //		Window window12 = new Window();
    //		window12.setPlain(false);
    //		window12.setHeading("window12");
    //		window12.add(new TextField<String>());
    //
    //		Window window121 = new Window();
    //		window121.setPlain(false);
    //		window121.setHeading("window121");
    //		window121.add(new TextField<String>());
    //
    //		final Window window21 = new Window();
    //		window21.setPlain(false);
    //		window21.setHeading("window21");
    //		window21.add(new TextField<String>());
    //		window21.setSize(500, 300);
    //
    //		Window window22 = new Window();
    //		window22.setPlain(false);
    //		window22.setHeading("window22");
    //		window22.add(new TextField<String>());
    //
    //		Window window23 = new Window();
    //		window23.setPlain(false);
    //		window23.setHeading("window23");
    //		window23.add(new TextField<String>());
    //
    //		WindowManagerHelper.attachChildToParent(window12, window11, true);
    //		WindowManagerHelper.attachChildToParent(window121, window12, true);
    //		WindowManagerHelper.attachChildToParent(window22, window21, true);
    //		WindowManagerHelper.attachChildToParent(window23, window21, true);
    //
    //		window11.show();
    //		window12.show();
    //		window121.show();
    //		window21.show();
    //		window22.show();
    //		window23.show();
    //
    //		final Button b1M = new Button();
    //		b1M.setText("window1 minimize");
    //		b1M.addSelectionListener(new SelectionListener<ButtonEvent>() {
    //
    //			@Override
    //			public void componentSelected(ButtonEvent ce) {
    //				window11.setData("minimaze", true);
    //				window11.hide();
    //			}
    //		});
    //
    //		Button b1R = new Button();
    //		b1R.setText("window1 restore");
    //		b1R.addSelectionListener(new SelectionListener<ButtonEvent>() {
    //
    //			@Override
    //			public void componentSelected(ButtonEvent ce) {
    //				window11.show();
    //			}
    //		});
    //
    //		Button b2M = new Button();
    //		b2M.setText("window2 minimize");
    //		b2M.addSelectionListener(new SelectionListener<ButtonEvent>() {
    //			@Override
    //			public void componentSelected(ButtonEvent ce) {
    //				window21.setData("minimaze", true);
    //				window21.hide();
    //			}
    //		});
    //
    //		Button b2R = new Button();
    //		b2R.setText("window2 restore");
    //		b2R.addSelectionListener(new SelectionListener<ButtonEvent>() {
    //
    //			@Override
    //			public void componentSelected(ButtonEvent ce) {
    //				window21.setData("minimaze", null);
    //				window21.show();
    //			}
    //		});
    //
    //		final Button bDN = new Button() {
    //			@Override
    //			protected void onLoad() {
    //				super.onLoad();
    //				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
    //
    //					@Override
    //					public void execute() {
    //						removeFromParent();
    //					}
    //				});
    //
    //			}
    //		};
    //		bDN.setText("do nothing");
    //
    //		Button bSV = new Button();
    //		bSV.setText("b1M show");
    //		bSV.addSelectionListener(new SelectionListener<ButtonEvent>() {
    //
    //			@Override
    //			public void componentSelected(ButtonEvent ce) {
    //				bDN.show();
    //				parent.layout();
    //			}
    //		});
    //
    //		parent.add(b1M);
    //		parent.add(b1R);
    //		parent.add(b2M);
    //		parent.add(b2R);
    //		parent.add(bSV);
    //		parent.add(bDN);
    //		parent.layout();
    //	}

}
