package model;

import MavenBdd.Generator.App;

public class Constraint {

	private String fk, primary;

	public Constraint(String source, String primary) {
		
		this.fk = source;
		this.primary = primary;
		
		
		
		
		
	}

	public String getFk() {
		return fk;
	}

	public void setFk(String fk) {
		this.fk = fk;
	}

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}
	
	
	
}
