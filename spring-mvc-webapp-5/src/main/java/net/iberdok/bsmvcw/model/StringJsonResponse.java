package net.iberdok.bsmvcw.model;

import java.io.Serializable;

/**
 * 
 * @author DOIBALMI
 *
 */
public class StringJsonResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@com.fasterxml.jackson.annotation.JsonRawValue
	@com.fasterxml.jackson.annotation.JsonUnwrapped
	private String jsonBody;

	/**
	 * 
	 */
	public StringJsonResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param jsonBody
	 */
	public StringJsonResponse(String jsonBody) {
		super();
		this.jsonBody = jsonBody;
	}

	/**
	 * @return the jsonBody
	 */
	public String getJsonBody() {
		return jsonBody;
	}

	/**
	 * @param jsonBody the jsonBody to set
	 */
	public void setJsonBody(String jsonBody) {
		this.jsonBody = jsonBody;
	}



}