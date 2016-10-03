package com.wch.connect;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public class WebServicePost {

	// IP��ַ
	private static String IP = "192.168.101.24:8080";

	/**
	 * ͨ�� POST ��ʽ��ȡHTTP����������
	 * 
	 * @param infor
	 * @param credit
	 * @return
	 * @throws Exception
	 */
	public static String executeHttpPost(String step, String calorimetry, String kilometer) {

		try {
			String path = "http://" + IP + "/WebSocketDemo/TransferServlet";

			// 发送指令和信息
			Map<String, String> params = new HashMap<String, String>();
			params.put("step", step);
			params.put("calorimetry", calorimetry);
			params.put("kilometer", kilometer);

			return sendPOSTRequest(path, params, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "连接失败。。";
	}

	/**
	 * 处理发送数据请求
	 * 
	 * @return
	 */
	private static String sendPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception {
		// TODO Auto-generated method stub
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				pairs.add(new BasicNameValuePair(entry.getKey(), (String)(entry.getValue())));
			}
		}

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);

		HttpPost post = new HttpPost(path);
		post.setEntity(entity);
		DefaultHttpClient client = new DefaultHttpClient();
		// 请求超时
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		// 读取超时
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		HttpResponse response = client.execute(post);

		// 判断是否成功收取信息
		if (response.getStatusLine().getStatusCode() == 200) {
			return getInfo(response);
		}

		// 未成功收取信息，返回空指针
		return null;
	}

	// 收取数据
	private static String getInfo(HttpResponse response) throws Exception {

		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		// 将输入流转化成byte型
		byte[] data = read(is);
		// ת转化为字符串
		return new String(data, "UTF-8");
	}

	// 将输入流转化为byte型
	public static byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		inStream.close();
		return outputStream.toByteArray();
	}

}
