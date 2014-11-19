package com.ubc.cs310.vandelay.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.ubc.cs310.vandelay.shared.Space;
//import com.google.gwt.maps.*;
//import com.google.gwt.maps.client.InfoWindowContent;
//import com.google.gwt.maps.client.MapWidget;
//import com.google.gwt.maps.client.Maps;
//import com.google.gwt.maps.client.control.LargeMapControl;
//import com.google.gwt.maps.client.geom.LatLng;
//import com.google.gwt.maps.client.overlay.Icon;
//import com.google.gwt.maps.client.overlay.Marker;
//import com.google.gwt.maps.client.overlay.MarkerOptions;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Vandelayv2 implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private TabPanel tabPanel = new TabPanel();
	private VerticalPanel searchAndDisplayPanel = new VerticalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private ScrollPanel scrollPanel = new ScrollPanel();
	private TextBox searchBox = new TextBox();
	private ListBox filterBox = new ListBox();
	private Button searchButton = new Button("Search");
	private List<Space> spaces = new ArrayList<Space>();
	private List<Space> filteredSpaces = new ArrayList<Space>();
	private CellTable<Space> table;
	private final CSVParserServiceAsync csvParser = GWT.create(CSVParserService.class);
	private static final Logger LOG = Logger.getLogger(Vandelayv2.class.getName());
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the Cultural Spaces application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	private VerticalPanel favouritesPanel = new VerticalPanel();
	private DialogBox popup = new DialogBox();
//	private Anchor link = new Anchor();
	private VerticalPanel popPanel = new VerticalPanel();
//	private LatLng vancouver = LatLng.newInstance(45.2500, 123.1000);
//	private MapWidget map = new MapWidget(vancouver, 2);
	private DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);

	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(com.google.gwt.core.client.GWT.getHostPageBaseURL(), 
				new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {

			}
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if(loginInfo.isLoggedIn()) {
					loadVandelay();
				} else {
					loadLogin();
				}
			}

		});

	}

	private void loadLogin() {
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("spaces").add(loginPanel);
	}

	private void loadVandelay() {
		csvParser.parse(new AsyncCallback<ArrayList<Space>>() {
			public void onFailure(Throwable error) {
				LOG.log(Level.WARNING, "Warning parse failed");
				//handleError(error);
			}

			public void onSuccess(ArrayList<Space> result) {
				spaces = result;
				filteredSpaces = spaces;
				LOG.log(Level.INFO, "Number of filteredSpaces before filtering: " + filteredSpaces.size());
				displaySpaces();
			}
		});
	}

	private void displaySpaces() {

		signOutLink.setHref(loginInfo.getLogoutUrl());

		table = makeTable();
		//key = AIzaSyDiDsB0QlBDJzDcE4UybUeEhxM91rM3HDI
		Maps.loadMapsApi("AIzaSyDiDsB0QlBDJzDcE4UybUeEhxM91rM3HDI", "2", false, new Runnable() {
		      public void run() {
		        buildMap();
		      }
		 });

		table.setPageSize(360);

		tabPanel.addStyleName("tabPanel");
		searchAndDisplayPanel.addStyleName("searchAndDisplayPanel");
		searchPanel.addStyleName("searchPanel");
		searchBox.addStyleName("searchBox");
		filterBox.addStyleName("scrollDownBox");
		searchButton.addStyleName("searchButton");
		table.addStyleName("spacesTable");
		favouritesPanel.addStyleName("favouritesPanel");
		scrollPanel.addStyleName("scrollPanel");

		filterBox.addItem("");
		filterBox.addItem("Museum/Gallery");
		filterBox.addItem("Community Space");
		filterBox.addItem("Studio/Rehearsal");
		filterBox.addItem("Theatre/Performance");
		filterBox.addItem("Educational");
		filterBox.addItem("Cafe/Restaurant/Bar");

		searchPanel.add(searchBox);
		searchPanel.add(filterBox);
		searchPanel.add(searchButton);

		scrollPanel.setHeight("300px");
		scrollPanel.add(table);

		searchAndDisplayPanel.add(searchPanel);
		searchAndDisplayPanel.add(scrollPanel);

		tabPanel.add(searchAndDisplayPanel, "Search");
		tabPanel.add(favouritesPanel, "Favourites");

		tabPanel.selectTab(0);

		mainPanel.add(signOutLink);
		mainPanel.add(tabPanel);
		
		

		RootPanel.get("spaces").add(mainPanel);

		searchBox.setFocus(true);

		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(searchBox.getText().trim().equals("")) {
					makeTable3();
					return;
				}
				filteredSpaces = 
						titleSearch(searchBox.getText().trim(), (ArrayList<Space>) filteredSpaces);
				makeTable2();
			}
		});

		filterBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Integer activityInt = filterBox.getSelectedIndex();
				switch(activityInt) {
				case 0:
					filteredSpaces = spaces;
					makeTable3();
					break;
				case 1:
					filteredSpaces = activitySearch("Museum/Gallery", (ArrayList<Space>) spaces);
					makeTable2();
					break;
				case 2:
					filteredSpaces = activitySearch("Community", (ArrayList<Space>) spaces);
					makeTable2();
					break;
				case 3:
					filteredSpaces = activitySearch("Studio/Rehearsal", (ArrayList<Space>) spaces);
					makeTable2();
					break;
				case 4:
					filteredSpaces = activitySearch("Theatre/Performance", (ArrayList<Space>) spaces);
					makeTable2();
					break;
				case 5:
					filteredSpaces = activitySearch("Educational", (ArrayList<Space>) spaces);
					makeTable2();
					break;
				case 6:
					filteredSpaces = activitySearch("Cafe/Restaurant/Bar", (ArrayList<Space>) spaces);
					makeTable2();
					break;

				}
			}
		});

		final SingleSelectionModel<Space> selectionModel = new SingleSelectionModel<Space>();
		table.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Space selected = selectionModel.getSelectedObject();
				if (selected != null) {
					popup.clear();
					popPanel.clear();
					String site = selected.getURL();
					if(!(site.contains("http")))
						site = "http://" + site;
					String linkString = "<a href='" + site + "'> " + site;
					HTML link2 = new HTML(linkString);
					popPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
					popPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
					popPanel.add(link2);
					//popPanel.add(closePopup); // add to favourites
					popup.setAutoHideEnabled(true);
					popup.setAnimationEnabled(true);
					popup.setText("Copy this link to get more info or share to social media!");
					popup.add(popPanel);
					popup.center();
					popup.show();
				}
			}

		});
	}

	public void buildMap() {
		LatLng cawkerCity = LatLng.newInstance(39.509, -98.434);

	    final MapWidget map = new MapWidget(cawkerCity, 2);
	    map.setSize("400px", "400px");
	    map.addControl(new LargeMapControl());
	    map.setScrollWheelZoomEnabled(true);
////	    
////	    Icon icon = Icon.newInstance();
////	    MarkerOptions markerOps = MarkerOptions.newInstance();
////	    String iconString = "";
////
////	    for(Space space : filteredSpaces) {
////	    	if(space.getType() == "Museum/Gallery") {
////	    		iconString = "http://www.google.com/mapfiles/kml/pal4/icon46.png";
////	    	} else if (space.getType() == "Studio/Rehearsal") {
////	    		iconString = "http://www.google.com/mapfiles/kml/pal3/icon21.png";
////	    	} else if (space.getType() == "Community Space") {
////	    		iconString = "http://www.google.com/mapfiles/kml/pal3/icon56.png";
////	    	} else if (space.getType() == "Theatre/Performance") {
////	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon22.png";
////	    	} else if (space.getType() == "Educational") {
////	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon10.png";
////	    	} else {
////	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon40.png";
////	    	}
////	    	
////	    	icon = Icon.newInstance(iconString);
////	    	markerOps = MarkerOptions.newInstance(icon);
////			Latlng latlon = new LatLng.newInstance(space.getLat(), space.getLon());
////	    	Marker spaceMarker = new Marker(space.getLatLon(), markerOps);
////	    	map.addOverlay(spaceMarker);
////	    	
////	    }
//	    
//	    // Add a marker
	    map.addOverlay(new Marker(cawkerCity));

	    // Add an info window to highlight a point of interest
	    map.getInfoWindow().open(map.getCenter(),
	        new InfoWindowContent("World's Largest Ball of Sisal Twine"));

	    dock.addNorth(map, 500);
	    
	    RootPanel.get("map").add(dock);
	}
//	
//	public void updateMapToFiltered() {
//		
//		map.clearOverlays();
//		
//		Icon icon = Icon.newInstance();
//	    MarkerOptions markerOps = MarkerOptions.newInstance();
//	    String iconString = "";
//
//	    for(Space space : filteredSpaces) {
//	    	if(space.getType() == "Museum/Gallery") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal4/icon46.png";
//	    	} else if (space.getType() == "Studio/Rehearsal") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal3/icon21.png";
//	    	} else if (space.getType() == "Community Space") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal3/icon56.png";
//	    	} else if (space.getType() == "Theatre/Performance") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon22.png";
//	    	} else if (space.getType() == "Educational") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon10.png";
//	    	} else {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon40.png";
//	    	}
//	    	
//	    	icon = Icon.newInstance(iconString);
//	    	markerOps = MarkerOptions.newInstance(icon);
//	    	Marker spaceMarker = new Marker(space.getLatLon(), markerOps);
//	    	map.addOverlay(spaceMarker);
//	    	
//	    }
//	}
//	
//	public void resetMapToOriginal() {
//		
//		map.clearOverlays();
//		
//		Icon icon = Icon.newInstance();
//	    MarkerOptions markerOps = MarkerOptions.newInstance();
//	    String iconString = "";
//
//	    for(Space space : spaces) {
//	    	if(space.getType() == "Museum/Gallery") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal4/icon46.png";
//	    	} else if (space.getType() == "Studio/Rehearsal") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal3/icon21.png";
//	    	} else if (space.getType() == "Community Space") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal3/icon56.png";
//	    	} else if (space.getType() == "Theatre/Performance") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon22.png";
//	    	} else if (space.getType() == "Educational") {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon10.png";
//	    	} else {
//	    		iconString = "http://www.google.com/mapfiles/kml/pal2/icon40.png";
//	    	}
//	    	
//	    	icon = Icon.newInstance(iconString);
//	    	markerOps = MarkerOptions.newInstance(icon);
//	    	Marker spaceMarker = new Marker(space.getLatLon(), markerOps);
//	    	map.addOverlay(spaceMarker);
//	    	
//	    }
//	}
	
	public static ArrayList<Space> titleSearch(String parameter, ArrayList<Space> originalList) { 

		ArrayList<Space> titleList = new ArrayList<Space>();

		for(Space space : originalList) {
			String title = space.getName().toLowerCase();
			if(title.contains(parameter.toLowerCase())) {
				titleList.add(space);
			}
		}

		LOG.log(Level.INFO, "Number of filteredSpaces before filtering: " + titleList.size());
		return titleList;
	}

	public static ArrayList<Space> activitySearch(String parameter, ArrayList<Space> originalList) { 

		ArrayList<Space> activityList = new ArrayList<Space>();
		for(Space space : originalList) {
			String activity = space.getType().toLowerCase();
			if(activity.contains(parameter.toLowerCase())) {
				activityList.add(space);
			}
		}
		return activityList;
	}

	private CellTable makeTable() {

		table = new CellTable<Space>();

		TextColumn<Space> nameColumn = new TextColumn<Space>() {
			@Override
			public String getValue(Space space) {
				return space.getName();
			}
		};

		TextColumn<Space> typeColumn = new TextColumn<Space>() {
			@Override
			public String getValue(Space space) {
				return space.getType();
			}
		};

		TextColumn<Space> useColumn = new TextColumn<Space>() {
			@Override
			public String getValue(Space space) {
				return space.getPrimaryUse();
			}
		};

		TextColumn<Space> urlColumn = new TextColumn<Space>() {
			@Override
			public String getValue(Space space) {
				return space.getURL();
			}
		};

		TextColumn<Space> areaColumn = new TextColumn<Space>() {
			@Override
			public String getValue(Space space) {
				return space.getLocalArea();
			}
		};

		TextColumn<Space> addressColumn = new TextColumn<Space>() {
			@Override
			public String getValue(Space space) {
				return space.getAddress();
			}
		};

		TextColumn<Space> ownershipColumn = new TextColumn<Space>() {
			@Override
			public String getValue(Space space) {
				return space.getOwnerShip();
			}
		};


		table.addColumn(nameColumn, "Name");
		table.addColumn(typeColumn, "Type");
		table.addColumn(useColumn, "Use");
		table.addColumn(urlColumn, "URL");
		table.addColumn(areaColumn, "Area");
		table.addColumn(addressColumn, "Address");
		table.addColumn(ownershipColumn, "Ownership");

		table.setRowCount(spaces.size(), true);
		table.setVisibleRange(0, spaces.size());
		table.setRowData(0, spaces);

		return table;
	};

	private void makeTable2() {

		table.setPageSize(filteredSpaces.size()+1);

		table.setRowCount(filteredSpaces.size(), true);
		table.setVisibleRange(0, filteredSpaces.size());
		table.setRowData(0, filteredSpaces);
	};

	private void makeTable3() {

		table.setPageSize(spaces.size()+1);

		table.setRowCount(spaces.size(), true);
		table.setVisibleRange(0, spaces.size());
		table.setRowData(0, spaces);
	};
}
