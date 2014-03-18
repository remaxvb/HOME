package com.trm.trmclient.Utils;

import java.util.ArrayList;

public class NavigationDrawerItem {

	private int id;
	private int iconSrc;
	private String title;
	private int counter;
	private NavigationDrawerItem parent;

	public NavigationDrawerItem(int id, int iconSrc, String title) {
		this.id = id;
		this.iconSrc = iconSrc;
		this.title = title;
		this.parent = null;
		this.counter = 0;
	}

	public NavigationDrawerItem(int id, int iconSrc, String title,
			NavigationDrawerItem parent) {
		this.id = id;
		this.iconSrc = iconSrc;
		this.title = title;
		this.parent = parent;
		this.counter = 0;
	}

	public NavigationDrawerItem(int id, int iconSrc, String title, int counter) {
		this.id = id;
		this.iconSrc = iconSrc;
		this.title = title;
		this.parent = null;
		this.counter = counter;
	}

	public NavigationDrawerItem(int id, int iconSrc, String title,
			NavigationDrawerItem parent, int counter) {
		this.id = id;
		this.iconSrc = iconSrc;
		this.title = title;
		this.parent = parent;
		this.counter = counter;
	}

	public NavigationDrawerItem(int iconSrc, String title,
			ArrayList<NavigationDrawerItem> subItems) {
		this.iconSrc = iconSrc;
		this.title = title;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIconSrc(int iconSrc) {
		this.iconSrc = iconSrc;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void setParent(NavigationDrawerItem parent) {
		this.parent = parent;
	}

	public int getId() {
		return this.id;
	}

	public int getIconSrc() {
		return this.iconSrc;
	}

	public String getTitle() {
		return this.title;
	}

	public int getCounter() {
		return this.counter;
	}

	public NavigationDrawerItem getParent() {
		return this.parent;
	}

	public boolean hasParent() {
		if (this.parent != null) {
			return true;
		} else {
			return false;
		}
	}

}
