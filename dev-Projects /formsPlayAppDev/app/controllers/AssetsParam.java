package controllers;

public class AssetsParam{
	
	//Fields
	private String assetType= "";
	private String description = "";
	private String value = "";
	private String ownership = "";
	
	//No arg Constructor
	public AssetsParam() {
		// TODO Auto-generated constructor stub
	}

	//Argumented Constructor
	public AssetsParam(String assetType, String description, String value, String ownership) {
		super();
		this.assetType = assetType;
		this.description = description;
		this.value = value;
		this.ownership = ownership;
	}
	
	//Getters and Setters
	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	
}
	