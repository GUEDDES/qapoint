package uni.boun;

public class PropertyAndValue {

	/**
	 * @param propertyURI
	 * @param value
	 */
	public PropertyAndValue(String propertyURI, String value) {
		super();
		this.propertyURI = propertyURI;
		this.value = value;
	}
	private String propertyURI;
	private String value;
	
	public String getPropertyURI() {
		return propertyURI;
	}
	public void setPropertyURI(String propertyURI) {
		this.propertyURI = propertyURI;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
