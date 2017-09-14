package com.wise;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * ��򵥵�HTTP�ͻ���,������ʾͨ��GET����POST��ʽ����ĳ��ҳ��
 *
 * @authorLiudong
 */
public class SimpleClient {
	public static void main(String[] args) throws HttpException, IOException {

		HttpClient client = new HttpClient();
		// ���ô����������ַ�Ͷ˿�
		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		// ʹ�� GET ���� �������������Ҫͨ�� HTTPS ���ӣ���ֻ��Ҫ������ URL �е� http ���� https
		HttpMethod method = new GetMethod("http://java.sun.com");
		// ʹ��POST����
		// HttpMethod method = new PostMethod("http://java.sun.com");
		System.out.println(client.executeMethod(method));

		// ��ӡ���������ص�״̬
		System.out.println(method.getStatusLine());
		// ��ӡ���ص���Ϣ
		//System.out.println(method.getResponseBodyAsString());
		// �ͷ�����
		method.releaseConnection();
	}
}