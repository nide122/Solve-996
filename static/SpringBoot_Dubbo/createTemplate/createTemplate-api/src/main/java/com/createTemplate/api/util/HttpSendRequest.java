package com.createTemplate.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.StringUtils;


public class HttpSendRequest {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * @Description: 发送https get 请求
	 * @Author: libiq
	 * @Version: V1.00
	 * @Create Date: 2015-1-7下午1:51:41
	 * @Parameters:@param url
	 * @Parameters:@return
	 */
	public static String sendHttpsGet(String url) {
		String result = "";
		HttpsURLConnection urlCon = null;
		try {
			urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("GET");
			urlCon.setUseCaches(false);
			urlCon.setRequestProperty("Connection", "Keep-Alive");
	        // 设置请求头信息
			urlCon.setRequestProperty("Charset", "GBK");
	        // 设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
	        urlCon.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
//			urlCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream(),"GBK"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("GET : " + result);
		return result;
	}

	/**
	 * @Description: 发送https post 请求
	 * @Author: libiq
	 * @Version: V1.00
	 * @Create Date: 2015-1-7下午1:51:52
	 * @Parameters:@param url
	 * @Parameters:@param xmlStr
	 * @Parameters:@return
	 */
	public static String sendHttpsPost(String url, String xmlStr) {
		String result = "";
		HttpsURLConnection urlCon = null;
		try {
			urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setUseCaches(false);
			// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
			if (StringUtils.isNotBlank(xmlStr)) {
				urlCon.setRequestProperty("Content-Length",
						String.valueOf(xmlStr.getBytes().length));
				urlCon.getOutputStream().write(xmlStr.getBytes("utf-8"));
			}
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("POST : " + result);
		return result;
	}

	   /**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 发送请求的 URL
	 * @param param 请求参数
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, Map<String, ?> paramMap) {
	    PrintWriter out = null;
	    BufferedReader in = null;
	    String result = "";
	    
	    String param = "";
		Iterator<String> it = paramMap.keySet().iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			param += key + "=" + paramMap.get(key) + "&";
		}
		
	    try {
	        URL realUrl = new URL(url);
	        // 打开和URL之间的连接
	        URLConnection conn = realUrl.openConnection();
	        // 设置通用的请求属性
	        conn.setRequestProperty("accept", "*/*");
	        conn.setRequestProperty("connection", "Keep-Alive");
	        conn.setRequestProperty("Accept-Charset", "utf-8");
	        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	        // 发送POST请求必须设置如下两行
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        // 获取URLConnection对象对应的输出流
	        out = new PrintWriter(conn.getOutputStream());
	        // 发送请求参数
	        out.print(param);
	        // flush输出流的缓冲
	        out.flush();
	        // 定义BufferedReader输入流来读取URL的响应
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    //使用finally块来关闭输出流、输入流
	    finally{
	        try{
	            if(out!=null){
	                out.close();
	            }
	            if(in!=null){
	                in.close();
	            }
	        }
	        catch(IOException ex){
	            ex.printStackTrace();
	        }
	    }
	    return result;
	}

    /**
     * @param alipayGatewayNew
     * @param sParaTemp
     */
    public static String sendHttpsGet(String alipayGatewayNew, Map<String, String> sParaTemp) {
        StringBuilder url = new StringBuilder();
        url.append(alipayGatewayNew);
        for (String key : sParaTemp.keySet()) {
            url.append(key).append("=").append(sParaTemp.get(key)).append("&");
        }
        if(url.length()>alipayGatewayNew.length()){
            url.deleteCharAt(url.length()-1);
        }
        return sendHttpsGet(url.toString());
    }
}
