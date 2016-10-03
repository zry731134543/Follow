package com.wch.connect;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import android.widget.Toast;

public class WebService {
	private static String TAG = "WebService";
	// IP
	private static String IP = "192.168.1.14:8080";

	/**
	 * ͨ通过GET方式获取服务器数据
	 * 
	 * @return
	 */
	public static String executeHttpGet(String step, String calorimetry, String kilometer) {

		HttpURLConnection conn = null;
		InputStream is = null;

		try {
			// 用户名 密码
			// URL 地址
			String path = "http://" + IP + "/WebSocketDemo/TransferServlet";
			path = path + "?step=" + step + "&calorimetry=" + calorimetry + "&kilometer=" + kilometer;

			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(3000); // 设置超时时间
			conn.setReadTimeout(3000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET"); // 设置获取信息方式
			conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				return parseInfo(is);
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 意外退出时进行连接关闭保护
			if (conn != null) {
				conn.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return "通信失败...";
	}

	// 将输入流转化为String型
	private static String parseInfo(InputStream inStream) throws Exception {
		byte[] data = read(inStream);
		// 转化为字符串
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

	// login的通信
	public static String executeHttpGet(String username, String password) {

		HttpURLConnection conn = null;
		InputStream is = null;

		try {
			// 用户名 密码
			// URL 地址
			String path = "http://" + IP + "/WebSocketDemo/LogLet";

			path = path + "?username=" + username + "&password=" + password;
			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(3000); // 设置超时时间
			conn.setReadTimeout(3000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET"); // 设置获取信息方式
			conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				System.out.println(parseInfo(is));
				return parseInfo(is);
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 意外退出时进行连接关闭保护
			if (conn != null) {
				conn.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return "通信失败...";
	}

	// Register的通信
	public static String executeHttpGet(String username, String password, int i) {

		HttpURLConnection conn = null;
		InputStream is = null;

		try {
			// 用户名 密码
			// URL 地址
			String path = "http://" + IP + "/WebSocketDemo/RegLet";

			path = path + "?username=" + username + "&password=" + password;
			conn = (HttpURLConnection) new URL(path).openConnection();
			
			conn.setConnectTimeout(3000); // 设置超时时间
			conn.setReadTimeout(3000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET"); // 设置获取信息方式
			conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式
			if (conn.getResponseCode() == 200) {
				Log.d(TAG, "连接成功");
				is = conn.getInputStream();
				return parseInfo(is);
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 意外退出时进行连接关闭保护
			if (conn != null) {
				conn.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return "注册失败...";
	}
}
