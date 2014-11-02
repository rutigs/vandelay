package com.ubc.cs310.client;


import com.ubc.cs310.shared.FieldVerifier;
import com.google.gwt.user.client.ui.Anchor;
=======
import java.util.Arrays;
import java.util.List;

>>>>>>> ui
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.sample.stockwatcher.client.LoginInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VandelayIndustries implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
	    "Please sign in to your Google Account to access the Cultural Spaces application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	  
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}
	      
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if(loginInfo.isLoggedIn()) {
					loadVandelayIndustries();
				} else {
					loadLogin();
				}
			}
		});
	}
	
	private void loadLogin() {
	    // Assemble login panel.
	    signInLink.setHref(loginInfo.getLoginUrl());
	    loginPanel.add(loginLabel);
	    loginPanel.add(signInLink);
	    RootPanel.get("spaceList").add(loginPanel); //figure out what spaceList is
	  }

	private void loadVandelayIndustries() {
		
		CellTable<Contact> table = makeTable();

		tabPanel.addStyleName("tabPanel");
		searchAndDisplayPanel.addStyleName("searchAndDisplayPanel");
		searchPanel.addStyleName("searchPanel");
		searchBox.addStyleName("searchBox");
		filterBox.addStyleName("scrollDownBox");
		searchButton.addStyleName("searchButton");
		table.addStyleName("spacesTable");

		searchPanel.add(searchBox);
		searchPanel.add(filterBox);
		searchPanel.add(searchButton);

		searchAndDisplayPanel.add(searchPanel);
		searchAndDisplayPanel.add(table);

		tabPanel.add(searchAndDisplayPanel, "Search");

		tabPanel.selectTab(0);

		mainPanel.add(tabPanel);

		RootPanel.get("spaces").add(mainPanel);

		searchBox.setFocus(true);
	}
	//	/**
	//	 * The message displayed to the user when the server cannot be reached or
	//	 * returns an error.
	//	 */
	//	private static final String SERVER_ERROR = "An error occurred while "
	//			+ "attempting to contact the server. Please check your network "
	//			+ "connection and try again.";
	//
	//	/**
	//	 * Create a remote service proxy to talk to the server-side Greeting service.
	//	 */
	//	private final GreetingServiceAsync greetingService = GWT
	//			.create(GreetingService.class);

	// A simple data type that represents a contact.
	private static class Contact {
		private final String address;
		private final String name;
		private final String type;
		private final String url;
		private final String ownership;
		private final String area;

		public Contact(String name, String address, String type, String url, String ownership,
				String area) {
			this.name = name;
			this.address = address;
			this.type = type;
			this.url = url;
			this.ownership = ownership;
			this.area = area;
		}
	}
	private VerticalPanel mainPanel = new VerticalPanel();
	private TabPanel tabPanel = new TabPanel();
	private VerticalPanel searchAndDisplayPanel = new VerticalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private TextBox searchBox = new TextBox();
	private ListBox filterBox = new ListBox();
	private Button searchButton = new Button("Search");
	//	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the Cultural Spaces application.");
	private Anchor signInLink = new Anchor("Sign In");

	private Anchor signOutLink = new Anchor("Sign Out");

	// The list of data to display.
	private static List<Contact> CONTACTS = Arrays.asList(
			new Contact("John", "123 Fourth Road", "Human", "www.pleasework.com", "public", "UBC"),
			new Contact("Mary", "222 Lancer Lane", "Human", "www.pleasework.com", "public", "UBC"));

	private CellTable<Contact> makeTable() {
		CellTable<Contact> table = new CellTable<Contact>();

		TextColumn<Contact> nameColumn = new TextColumn<Contact>() {
			@Override
			public String getValue(Contact contact) {
				return contact.name;
			}
		};

		TextColumn<Contact> typeColumn = new TextColumn<Contact>() {
			@Override
			public String getValue(Contact contact) {
				return contact.type;
			}
		};

		TextColumn<Contact> urlColumn = new TextColumn<Contact>() {
			@Override
			public String getValue(Contact contact) {
				return contact.url;
			}
		};

		TextColumn<Contact> areaColumn = new TextColumn<Contact>() {
			@Override
			public String getValue(Contact contact) {
				return contact.address;
			}
		};

		TextColumn<Contact> addressColumn = new TextColumn<Contact>() {
			@Override
			public String getValue(Contact contact) {
				return contact.area;
			}
		};

		TextColumn<Contact> ownershipColumn = new TextColumn<Contact>() {
			@Override
			public String getValue(Contact contact) {
				return contact.ownership;
			}
		};

		table.addColumn(nameColumn, "Name");
		table.addColumn(typeColumn, "Type");
		table.addColumn(urlColumn, "URL");
		table.addColumn(areaColumn, "Area");
		table.addColumn(addressColumn, "Address");
		table.addColumn(ownershipColumn, "Ownership");

		table.setRowCount(CONTACTS.size(), true);

		table.setRowData(0, CONTACTS);
		
		return table;
	};
}
