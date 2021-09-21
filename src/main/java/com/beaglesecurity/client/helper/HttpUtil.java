/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.client.helper;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.beaglesecurity.api.payloads.PayloadBase;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtil {
	
	public static HttpReturn postRequest(String url, PayloadBase postObject, String token) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		HttpReturn returnObj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(postObject);
			
			httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(json);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			httpPost.setHeader("Authorization", "Bearer " + token);	
			HttpResponse response = httpClient.execute(httpPost);
			
			String result = EntityUtils.toString(response.getEntity());
			returnObj = new HttpReturn();
			returnObj.setCode(response.getStatusLine().getStatusCode());
			returnObj.setResultJson(result);
			
		} catch (Exception e) {
			return null;
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return returnObj;		
	}
	
	public static HttpReturn putRequest(String url, PayloadBase postObject, String token) {
		CloseableHttpClient httpClient = null;
		HttpPut httpPut = null;
		HttpReturn returnObj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(postObject);
			
			httpClient = HttpClients.createDefault();
			httpPut = new HttpPut(url);
			StringEntity entity = new StringEntity(json);
			entity.setContentType("application/json");
			httpPut.setEntity(entity);
			httpPut.setHeader("Authorization", "Bearer " + token);	
			HttpResponse response = httpClient.execute(httpPut);
			
			String result = EntityUtils.toString(response.getEntity());
			returnObj = new HttpReturn();
			returnObj.setCode(response.getStatusLine().getStatusCode());
			returnObj.setResultJson(result);
			
		} catch (Exception e) {
			return null;
		} finally {
			if (httpPut != null) {
				httpPut.releaseConnection();
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return returnObj;		
	}
	
	public static HttpReturn getRequest(String url, String token) {
		CloseableHttpClient httpClient = null;
		HttpGet httpGet = null;
		HttpReturn returnObj = null;
		try {
			httpClient = HttpClients.createDefault();
			httpGet = new HttpGet(url);

			httpGet.setHeader("Authorization", "Bearer " + token);	
			HttpResponse response = httpClient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity());
			returnObj = new HttpReturn();
			returnObj.setCode(response.getStatusLine().getStatusCode());
			returnObj.setResultJson(result);
			
		} catch (Exception e) {
			return null;
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return returnObj;		
	}
	
	public static HttpReturn deleteRequest(String url, String token) {
		CloseableHttpClient httpClient = null;
		HttpDelete httpDelete = null;
		HttpReturn returnObj = null;
		try {
			httpClient = HttpClients.createDefault();
			httpDelete = new HttpDelete(url);

			httpDelete.setHeader("Authorization", "Bearer " + token);	
			HttpResponse response = httpClient.execute(httpDelete);
			String result = EntityUtils.toString(response.getEntity());
			returnObj = new HttpReturn();
			returnObj.setCode(response.getStatusLine().getStatusCode());
			returnObj.setResultJson(result);
			
		} catch (Exception e) {
			return null;
		} finally {
			if (httpDelete != null) {
				httpDelete.releaseConnection();
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return returnObj;		
	}
}
