package com.ubc.cs310.vandelay.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Vandelayv2 implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private TabPanel tabPanel = new TabPanel();
	private VerticalPanel mapPanel = new VerticalPanel();
	private VerticalPanel searchAndDisplayPanel = new VerticalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private ScrollPanel scrollPanel = new ScrollPanel();
	private TextBox searchBox = new TextBox();
	private ListBox filterBox = new ListBox();
	private ListBox areaBox = new ListBox();
	private Button searchButton = new Button("Search");
	private List<Space> spaces = new ArrayList<Space>();
	private List<Space> filteredSpaces = new ArrayList<Space>();
	private List<Space> favouriteSpaces = new ArrayList<Space>();
	private List<Space> filteredFavourites = new ArrayList<Space>();
	private CellTable<Space> table;
	private final CSVParserServiceAsync csvParser = GWT.create(CSVParserService.class);
	private static final Logger LOG = Logger.getLogger(Vandelayv2.class.getName());
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the Cultural Spaces application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	private DialogBox popup = new DialogBox();
	private VerticalPanel popPanel = new VerticalPanel();
	private DialogBox popupFav = new DialogBox();
	private VerticalPanel popPanelFav = new VerticalPanel();
	private Button resetButton = new Button("Reset");
	private Button addFavourite = new Button("Add To Favourites");
	private Button removeFavourite = new Button("Remove From Favourites");
	private VerticalPanel favouritesPanel = new VerticalPanel();
	private HorizontalPanel favouritesSearchPanel = new HorizontalPanel();
	private TextBox favSearchBox = new TextBox();
	private Button favSearchButton = new Button("Search");
	private Button favResetButton = new Button("Reset");
	private CellTable<Space> favTable;
	private ScrollPanel scrollFavPanel = new ScrollPanel();
	private HorizontalPanel adminPanel = new HorizontalPanel();
	private TextBox adminData = new TextBox();
	private Button adminButton = new Button("Load");
	private Boolean found = false;

	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(com.google.gwt.core.client.GWT.getHostPageBaseURL(), 
				new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {

			}
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				//loadAdminPanel();
				if(loginInfo.getEmailAddress() == "rutigliano.nick@gmail.com") {
					LOG.log(Level.INFO, "attempting to loadAdminPanel()");
					loadAdminPanel();
				} else if(loginInfo.isLoggedIn()) {
					//parseAndPersistData();
					LOG.log(Level.INFO, "attempting to loadVandelay()");
					loadVandelay();
				} else {
					loadLogin();
				}
			}

		});

	}

	private void loadAdminPanel() {
		adminPanel.add(adminData);
		adminPanel.add(adminButton);
		signOutLink.setHref(loginInfo.getLogoutUrl());
		adminPanel.add(signOutLink);
		RootPanel.get("spaces").add(adminPanel);
		adminButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(adminData.getText().trim().equals("")) {
					Window.alert("No data is being loaded");
					loadVandelay();
				} else {
					LOG.log(Level.INFO, "The url where the data is located is: " + adminData.getText().trim());
					parseAndPersistData(adminData.getText().trim());
				}
			}

		});
	}

	private void parseAndPersistData(String url) {
		csvParser.parse(url, new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {}
			public void onSuccess(Void result) {
				loadVandelay();
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
		csvParser.getSpaces(new AsyncCallback<ArrayList<Space>>() {
			public void onFailure(Throwable error) {}
			public void onSuccess(ArrayList<Space> result) {
				if (result.size() > 0) {
					spaces = result;
					filteredSpaces = spaces;
					displaySpaces();
				} else {
					Window.alert("The data has not been loaded");
				}
			}
		});
	}

	private void displaySpaces() {

		RootPanel.get("spaces").clear();

		signOutLink.setHref(loginInfo.getLogoutUrl());

		table = makeTable((ArrayList<Space>) spaces);
		//key = AIzaSyDiDsB0QlBDJzDcE4UybUeEhxM91rM3HDI
		Maps.loadMapsApi("", "2", false, new Runnable() {
			public void run() {
				buildMap();
			}
		});

		table.setPageSize(360);

		tabPanel.addStyleName("tabPanel");
		searchAndDisplayPanel.addStyleName("searchAndDisplayPanel");
		searchPanel.addStyleName("searchPanel");
		searchBox.addStyleName("searchBox");
		areaBox.addStyleName("scrollDownBox");
		filterBox.addStyleName("scrollDownBox");
		searchButton.addStyleName("searchButton");
		resetButton.addStyleName("searchButton");
		table.addStyleName("spacesTable");
		scrollPanel.addStyleName("scrollPanel");
		mapPanel.addStyleName("mapPanel");
		addFavourite.addStyleName("addFavouriteButton");
		removeFavourite.addStyleName("addFavouriteButton");
		scrollFavPanel.addStyleName("scrollPanel");
		popPanel.addStyleName("popPanel");
		popup.addStyleName("popup");
		favouritesPanel.addStyleName("searchAndDisplayPanel");
		favouritesSearchPanel.addStyleName("searchPanel");
		favSearchBox.addStyleName("searchBox");
		favSearchButton.addStyleName("searchButton");
		favResetButton.addStyleName("searchButton");

		filterBox.addItem("Select Type");
		filterBox.addItem("Museum/Gallery");
		filterBox.addItem("Community Space");
		filterBox.addItem("Studio/Rehearsal");
		filterBox.addItem("Theatre/Performance");
		filterBox.addItem("Educational");
		filterBox.addItem("Cafe/Restaurant/Bar");

		areaBox.addItem("Select Local Area");
		areaBox.addItem(""); 
		areaBox.addItem("Kitsilano");
		areaBox.addItem("Strathcona");
		areaBox.addItem("West Point Grey");
		areaBox.addItem("Downtown");
		areaBox.addItem("Oakridge");
		areaBox.addItem("Fairview");
		areaBox.addItem("Mount Pleasant");
		areaBox.addItem("Grandview-Woodland");
		areaBox.addItem("Riley Park");
		areaBox.addItem("Hastings-Sunrise");
		areaBox.addItem("Killarney");
		areaBox.addItem("Marpole");
		areaBox.addItem("Renfrew-Collingwood");
		areaBox.addItem("Kensington-Cedar Cottage");
		areaBox.addItem("South Cambie");
		areaBox.addItem("Dunbar-Southlands");
		areaBox.addItem("Kerrisdale");
		areaBox.addItem("West End");
		areaBox.addItem("Sunset");
		areaBox.addItem("Shaughnessy");

		searchPanel.add(searchBox);
		searchPanel.add(filterBox);
		searchPanel.add(areaBox);
		searchPanel.add(searchButton);
		searchPanel.add(resetButton);

		scrollPanel.setHeight("400px");
		scrollPanel.add(table);

		searchAndDisplayPanel.add(searchPanel);
		searchAndDisplayPanel.add(scrollPanel);

		tabPanel.add(searchAndDisplayPanel, "Search");
		//tabPanel.add(favouritesPanel, "Favourites");

		tabPanel.selectTab(0);

		mainPanel.add(mapPanel);
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

		resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				filteredSpaces = spaces;
				makeTable2();
				filterBox.setSelectedIndex(0);
				areaBox.setSelectedIndex(0);
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

		areaBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Integer areaInt = areaBox.getSelectedIndex();
				switch(areaInt) {
				case 0:
					filteredSpaces = spaces;
					makeTable2();
					break;
				case 1:
					filteredSpaces = areaSearch("", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 2:
					filteredSpaces = areaSearch("Kitsilano", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 3:
					filteredSpaces = areaSearch("Strathcona", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 4:
					filteredSpaces = areaSearch("West Point Grey", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 5:
					filteredSpaces = areaSearch("Downtown", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 6:
					filteredSpaces = areaSearch("Oakridge", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 7:
					filteredSpaces = areaSearch("Fairview", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 8:
					filteredSpaces = areaSearch("Mount Pleasant", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 9:
					filteredSpaces = areaSearch("Grandview-Woodland", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 10:
					filteredSpaces = areaSearch("Riley Park", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 11:
					filteredSpaces = areaSearch("Hastings-Sunrise", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 12:
					filteredSpaces = areaSearch("Killarney", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 13:
					filteredSpaces = areaSearch("Marpole", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 14:
					filteredSpaces = areaSearch("Renfrew-Collingwood", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 15:
					filteredSpaces = areaSearch("Kensington-Cedar Cottage", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 16:
					filteredSpaces = areaSearch("South Cambie", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 17:
					filteredSpaces = areaSearch("Dunbar-Southlands", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 18:
					filteredSpaces = areaSearch("Kerrisdale", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 19:
					filteredSpaces = areaSearch("West End", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 20:
					filteredSpaces = areaSearch("Sunset", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				case 21:
					filteredSpaces = areaSearch("Shaughnessy", (ArrayList<Space>) filteredSpaces);
					// call function to make map from filteredSpaces
					makeTable2();
					break;
				}
			}
		});

		final SingleSelectionModel<Space> selectionModel = new SingleSelectionModel<Space>();
		table.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				final Space selected = selectionModel.getSelectedObject();
				if (selected != null) {
					//LatLng spaceLocn = LatLng.newInstance(selected.getLat(), selected.getLon());
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
					popPanel.add(addFavourite);

					addFavourite.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							if(!checkFavourites(selected)) {
								csvParser.addFavourite(selected, new AsyncCallback<Void>() {
									public void onFailure(Throwable error) {
										//handleError(error);
									}
									public void onSuccess(Void ignore) {
										updateFavourites();
									}	
								});
							} else {
								Window.alert("This is already a favourite!");
							}
						}
					});

					popup.setAutoHideEnabled(true);
					popup.setAnimationEnabled(true);
					popup.setText("Copy this link to get more info or share to social media!");
					popup.add(popPanel);
					//map.setCenter(spaceLocn, 12); also add window popup 
					// might require making map accessible by creating MapWidget before buildMap();
					popup.center();
					popup.show();
				}
			}

		});

		buildFavourites();

	}

	public void buildFavourites() {
		csvParser.getFavourites(new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable error) {
				LOG.log(Level.WARNING, "Warning get favourites failed");
				//handleError(error);
			}

			public void onSuccess(ArrayList<String> result) {
				favouriteSpaces = getFavouritesAsSpaces(result);
				filteredFavourites = favouriteSpaces;
				LOG.log(Level.INFO, "Number of filteredFavourites before filtering: " + filteredSpaces.size());
				displayFavourites();
			}
		});
	}

	public ArrayList<Space> getFavouritesAsSpaces(ArrayList<String> favourites) {
		List<Space> favSpaces = new ArrayList<Space>();
		for(Space space : spaces) {
			for(String name: favourites) {
				if(space.getName() == name)
					favSpaces.add(space);
			}
		}
		return (ArrayList<Space>) favSpaces;
	}

	public void displayFavourites() {
		favTable = makeTable((ArrayList<Space>) favouriteSpaces);

		favouritesSearchPanel.add(favSearchBox);
		favouritesSearchPanel.add(favSearchButton);
		favouritesSearchPanel.add(favResetButton);

		scrollFavPanel.add(favTable);

		favouritesPanel.add(favouritesSearchPanel);
		favouritesPanel.add(scrollFavPanel);

		tabPanel.add(favouritesPanel, "Favourites");

		favSearchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(favSearchBox.getText().trim().equals("")) {
					filteredFavourites = favouriteSpaces;
					updateFavTableBySearch();
				} else {
					filteredFavourites = titleSearch(favSearchBox.getText().trim(), (ArrayList<Space>) favouriteSpaces);
					updateFavTableBySearch();
				}
			}
		});

		favResetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				favSearchBox.setText("");
				filteredFavourites = favouriteSpaces;
				updateFavTableBySearch();
			}
		});

		final SingleSelectionModel<Space> selectionModelFav = new SingleSelectionModel<Space>();
		favTable.setSelectionModel(selectionModelFav);
		selectionModelFav.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				final Space selected = selectionModelFav.getSelectedObject();
				if (selected != null) {
					//LatLng spaceLocn = LatLng.newInstance(selected.getLat(), selected.getLon());
					popupFav.clear();
					popPanelFav.clear();
					String site = selected.getURL();
					if(!(site.contains("http")))
						site = "http://" + site;
					String linkString = "<a href='" + site + "'> " + site;
					HTML link2 = new HTML(linkString);
					popPanelFav.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
					popPanelFav.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
					popPanelFav.add(link2);
					popPanelFav.add(removeFavourite);

					removeFavourite.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							csvParser.deleteFavourite(selected, new AsyncCallback<Void>() {
								public void onFailure(Throwable error) {
									//handleError(error);
								}
								public void onSuccess(Void ignore) {
									popupFav.hide();
									updateFavourites();
								}	
							}); 
						}
					});

					popupFav.setAutoHideEnabled(true);
					popupFav.setAnimationEnabled(true);
					popupFav.setText("Copy this link to get more info or share to social media!");
					popupFav.add(popPanelFav);
					//map.setCenter(spaceLocn, 12); also add window popup 
					// might require making map accessible by creating MapWidget before buildMap();
					popupFav.center();
					popupFav.show();
				}
			}

		});
	}

	public boolean checkFavourites(Space space) {
		csvParser.checkFavourite(space, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable error) {}
			public void onSuccess(Boolean result) {
				found = result;
			}
		});

		return found;
	}

	public void updateFavTableBySearch() {

		favTable.setPageSize(filteredFavourites.size()+1);
		favTable.setRowCount(filteredFavourites.size(), true);
		favTable.setVisibleRange(0, filteredFavourites.size());
		favTable.setRowData(0, filteredFavourites);
	}

	//	public void updateFavouritesTable() {
	//		updateFavourites();
	//		
	//		favTable.setPageSize(favouriteSpaces.size()+1);
	//		favTable.setRowCount(favouriteSpaces.size(), true);
	//		favTable.setVisibleRange(0, favouriteSpaces.size());
	//		favTable.setRowData(0, favouriteSpaces);
	//	};

	public void updateFavourites() {
		csvParser.getFavourites(new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable error) {
				LOG.log(Level.WARNING, "Warning get favourites failed");
				//handleError(error);
			}

			public void onSuccess(ArrayList<String> result) {
				favouriteSpaces = getFavouritesAsSpaces(result);
				filteredFavourites = favouriteSpaces;
				LOG.log(Level.INFO, "Number of filteredFavourites before filtering: " + filteredSpaces.size());
				displayFavourites();
			}
		});
	}

	public void buildMap() {

		final LatLng vancouver = LatLng.newInstance(49.261226, -123.1139268);
		final MapWidget map = new MapWidget(vancouver, 2);
		map.setCenter(vancouver);

		map.setSize("1000px", "400px");
		map.addControl(new LargeMapControl());
		map.setScrollWheelZoomEnabled(true);

		Icon icon = Icon.newInstance();
		MarkerOptions markerOps = MarkerOptions.newInstance();
		String iconString = "";

		for(Space space : spaces) {

			String type = space.getType().toLowerCase();

			if(type.contains("museum")) {
				iconString = "http://www.google.com/mapfiles/kml/pal4/icon46.png";
			} else if (type.contains("studio")) {
				iconString = "http://www.google.com/mapfiles/kml/pal3/icon21.png";
			} else if (type.contains("community")) {
				iconString = "http://www.google.com/mapfiles/kml/pal3/icon56.png";
			} else if (type.contains("theatre")) {
				iconString = "http://www.google.com/mapfiles/kml/pal2/icon22.png";
			} else if (type.contains("education")) {
				iconString = "http://www.google.com/mapfiles/kml/pal2/icon10.png";
			} else {
				iconString = "http://www.google.com/mapfiles/kml/pal2/icon40.png";
			}
			final String name = space.getName();
			final String address = space.getAddress();
			//InfoWindowContent window = new InfoWindowContent("");
			icon = Icon.newInstance(iconString);
			markerOps = MarkerOptions.newInstance(icon);
			markerOps.setTitle(name + ": located at " + address);
			markerOps.setClickable(true);
			LatLng latlon = LatLng.newInstance(space.getLat(),  space.getLon());
			final Marker spaceMarker = new Marker(latlon, markerOps);
			spaceMarker.addMarkerClickHandler(new MarkerClickHandler() {
				public void onClick(MarkerClickEvent event) {
					spaceMarker.showMapBlowup();
				}
			});
			map.addOverlay(spaceMarker);

		}

		map.setCenter(vancouver, 11);
		mapPanel.add(map);


		//	    // Add a marker
		//map.addOverlay(new Marker(cawkerCity));

		// Add an info window to highlight a point of interest
		//	    map.getInfoWindow().open(map.getCenter(),
		//	        new InfoWindowContent("World's Largest Ball of Sisal Twine"));
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

	public static ArrayList<Space> areaSearch(String parameter, ArrayList<Space> originalList) {

		ArrayList<Space> areaList = new ArrayList<Space>();
		if(parameter.toLowerCase().equals("")) {
			// return only spaces with no area
			for(Space space : originalList) {
				String area = space.getLocalArea().toLowerCase();
				if(area.equals("")) {
					areaList.add(space);
				}
			}
		} else {
			for(Space space : originalList) {
				String area = space.getLocalArea().toLowerCase();
				if(area.contains(parameter.toLowerCase())) {
					areaList.add(space);
				}
			}
		}
		return areaList;
	}

	private CellTable<Space> makeTable(ArrayList<Space> list) {

		CellTable<Space> tableSpaces = new CellTable<Space>();
		//table = new CellTable<Space>();

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


		tableSpaces.addColumn(nameColumn, "Name");
		tableSpaces.addColumn(typeColumn, "Type");
		tableSpaces.addColumn(useColumn, "Use");
		tableSpaces.addColumn(urlColumn, "URL");
		tableSpaces.addColumn(areaColumn, "Area");
		tableSpaces.addColumn(addressColumn, "Address");
		tableSpaces.addColumn(ownershipColumn, "Ownership");

		tableSpaces.setRowCount(list.size(), true);
		tableSpaces.setVisibleRange(0, list.size());
		tableSpaces.setRowData(0, list);

		return tableSpaces;
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
