package io.mosip.testrig.adminui.kernel.util;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.mosip.testrig.adminui.kernel.service.ApplicationLibrary;
import io.mosip.testrig.adminui.utility.BaseTestCaseFunc;
import io.mosip.testrig.adminui.utility.TestRunner;
import io.restassured.http.Cookie;
import io.restassured.response.Response;

public class CommonLibrary extends BaseTestCaseFunc {
	private static Logger logger = Logger.getLogger(CommonLibrary.class);
	private ApplicationLibrary applicationLibrary = new ApplicationLibrary();
	
	
	public String getResourcePath() {
		return TestRunner.getResourcePath() + "/";
	}
	
	public String getResourcePathForKernel() {
		return TestRunner.getResourcePath() + "/";
	}
	
	
	public boolean isValidToken(String cookie) {
		
		logger.info("========= Revalidating the token =========");
		Response response = applicationLibrary.getWithoutParams("/v1/authmanager/authorize/admin/validateToken", cookie);
		JSONObject responseJson =null;
		try {
			responseJson = (JSONObject) ((JSONObject) new JSONParser().parse(response.asString()))
					.get("response");
		} catch (ParseException | NullPointerException e) {
			logger.info(e.getMessage());
		}

		if (responseJson!=null && responseJson.get("errors")==null)
			{
			logger.info("========= Valid Token =========");
			return true;
			}
		else
		{
			
			logger.info("========= InValid Token =========");
			return false;
		}

	}
	
	public Map<String, String> readProperty(String propertyFileName) {
		Properties prop = new Properties();
		try {
			if(propertyFileName.equals("Kernel")) {
				File propertyFile = new File(getResourcePathForKernel() + "config/" +TestRunner.GetKernalFilename());
				prop.load(new FileInputStream(propertyFile));
			}else {
			logger.info("propertyFileName:  " + propertyFileName + "Path :" + getResourcePathForKernel() + "config/" + propertyFileName + ".properties");
			logger.info("propertyFileName:  " + propertyFileName + "Path :" + getResourcePathForKernel() + "config/" + propertyFileName + ".properties");
			File propertyFile = new File(getResourcePathForKernel() + "config/" + propertyFileName + ".properties");
			prop.load(new FileInputStream(propertyFile));
			}

		} catch (IOException e) {
			logger.info("Error occrued while reading propertyFileName " + propertyFileName + e.getMessage());
			logger.info(e.getMessage());
		}

		Map<String, String> mapProp = prop.entrySet().stream()
				.collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));

		return mapProp;
	}
	
	// Get Requests:
	/**
	 * @param url
	 * @param cookie
	 * @return this method is for get request with authentication(cookie) and
	 *         without any param.
	 */
	public Response getWithoutParams(String url, String cookie) {
		logger.info("REST-ASSURED: Sending a Get request to " + url);
		Cookie.Builder builder = new Cookie.Builder("Authorization", cookie);
		Response getResponse = given().cookie(builder.build()).relaxedHTTPSValidation().log().all().when().get(url);
		// log then response
		responseLogger(getResponse);
		logger.info("REST-ASSURED: the response Time is: " + getResponse.time());
		return getResponse;
	}
	
	
	/**
	 * @param response
	 *            this method is for logging the response in case of error only.
	 *            this is used in get request response logging
	 */
	public void responseLogger(Response response) {
		int statusCode = response.statusCode();
		if (statusCode < 200 || statusCode > 299) {
			logger.info(response.asString());
		} else
			logger.info("status code: " + statusCode + "(success)");

	}
	
	public JSONObject readJsonData(String path, boolean isRelative) {
		logger.info("path : " + path);
		if(isRelative)
			path = getResourcePath() + path;
		logger.info("Relativepath : " + path);
		File fileToRead = new File(path);
		InputStream isOfFile = null;
		try {
			logger.info("fileToRead : " + fileToRead);
			isOfFile = new FileInputStream(fileToRead);
		} catch (FileNotFoundException e1) {
			logger.info("error while reading the file : " + e1.getLocalizedMessage() );
			e1.printStackTrace();
			logger.info("File Not Found at the given path");
		}
		JSONObject jsonData = null;
		try {
			jsonData = (JSONObject) new JSONParser().parse(new InputStreamReader(isOfFile, "UTF-8"));
		} catch (IOException | ParseException | NullPointerException e) {
			logger.info(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * @param url
	 * @param body
	 * @param contentHeader
	 * @param acceptHeader
	 * @return this method is for post request without authentication(cookie) and
	 *         only with jsonData in request body.
	 */
	public Response postWithJson(String url, Object body, String contentHeader, String acceptHeader) {
		logger.info("REST:ASSURED:Sending post request to" + url);
		Response postResponse = given().relaxedHTTPSValidation().body(body).contentType(contentHeader)
				.accept(acceptHeader).log().all().when().post(url).then().log().all().extract().response();
		// log then response
		logger.info("REST-ASSURED: The response from request is: " + postResponse.asString());
		logger.info("REST-ASSURED: The response Time is: " + postResponse.time());
		return postResponse;
	}
}
