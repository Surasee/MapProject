package com.tho.mapprippree;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.FragmentManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class FunctionLib {
	//private static final String ServerUrl = "http://tho.ddns.net:2407/prippree/";
	//private static final String ServerUrl = "http://tho.ddns.net:2407/hostproject/";
	//private static final String ServerUrl = "http://travelapp.pe.hu/";
	private static final String ServerUrl = "http://mrthoserver.net/";


	public String getHttpGet(String parameter) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ServerUrl+parameter);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Status OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download result..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	public String getHttpPost(String url, List<NameValuePair> params) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Status OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download result..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	public String getJSONUrl(String parameter) {
		//Log.e("DeBug"," "+ServerUrl);
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ServerUrl+parameter);
		Log.e("Log", "Can't connect to server.");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Download OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download file..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//str.delete(0, 5);
		//str.delete(0, 2);
		//str.delete(0, 1);

		return str.toString().replaceAll("\uFEFF","");

	}



	public JSONObject makeHttpRequest(String url) {
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		// Making HTTP request
		try {

			// check for request method

			// request method is GET
			DefaultHttpClient httpClient = new DefaultHttpClient();
			// String paramString = URLEncodedUtils.format(params, "utf-8");
			// url += "?" + paramString;
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}


	//	BitMAP
	public static Bitmap getBitmapFromURL(String src) {
//		try {
//
//			URL url = new URL(src);
//			Log.e("BitMap","URL="+url);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setDoInput(true);
//			connection.connect();
//			InputStream input = connection.getInputStream();
//			Log.e("BitMap","input = "+input);
//			Bitmap myBitmap = BitmapFactory.decodeStream(input);
//			Log.e("BitMap","myBitmap = "+myBitmap);
//
//			return myBitmap;
//		} catch (IOException e) {
//			// Log exception
//			return null;
//		}

//		String urldisplay = ServerUrl+src;
//
//		Log.e("Error"," URL = "+urldisplay);
//		Bitmap myBitmap = null;
//		try {
//			InputStream in = new java.net.URL(urldisplay).openStream();
//
//			Log.e("Error"," in = "+in);
//			myBitmap = BitmapFactory.decodeStream(in);
//			Log.e("Error"," Bitmap = "+myBitmap);
//		} catch (Exception e) {
//			Log.e("Error", e.getMessage());
//			e.printStackTrace();
//		}
//		return myBitmap;

		String urldisplay = ServerUrl+src;
		//Log.e("Error"," URL = "+urldisplay);
		Bitmap myBitmap = null;
		try {
			URL Url = new URL(urldisplay);
			InputStream in = new BufferedInputStream(Url.openStream(), 20000);
			//Log.e("Error"," in = "+in);
			myBitmap = BitmapFactory.decodeStream(in);
			//Log.e("Error"," Bitmap = "+myBitmap);
		} catch (Exception e) {
			//Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return myBitmap;




}//getBitmapFromURL
	//END	BitMAP

	public static void btnBackClick(Fragment fragment){
		FragmentManager frgManager = fragment.getFragmentManager();
		frgManager.beginTransaction().remove(fragment).commit();
		//frgManager.popBackStack();

	}








}
