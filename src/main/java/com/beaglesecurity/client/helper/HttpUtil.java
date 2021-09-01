/*
 * Copyright 2021-2025 beaglesecurity.com. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * 
 * You may not use this file except in compliance with the License.
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * 
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
