//package model;
//
//public class Constraint {
//
//	private String fk, primary;
//
//	private boolean simpleFk;
//
//	private Column colParent;
//	
//	public Constraint(String source, String primary, Column column) {
//		
//		this.fk = source;
//		this.primary = primary;
//		
//		this.setColParent(column);
//		
//		this.colParent.getTable().getLinkedTable().add(primary.substring(primary.indexOf(".")+1));
//		
//		
//		
//	}
//
//	public String getFk() {
//		return fk;
//	}
//
//	public void setFk(String fk) {
//		this.fk = fk;
//	}
//
//	public String getPrimary() {
//		return primary;
//	}
//
//	public void setPrimary(String primary) {
//		this.primary = primary;
//	}
//
//	public Column getColParent() {
//		return colParent;
//	}
//
//	public void setColParent(Column colParent) {
//		this.colParent = colParent;
//	}
//
//	public boolean isSimpleFk() {
//		return simpleFk;
//	}
//
//	public void setSimpleFk(boolean simpleFk) {
//		this.simpleFk = simpleFk;
//	}
//	
//	
//	
//}
