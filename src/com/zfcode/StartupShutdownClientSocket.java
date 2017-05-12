package com.zfcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartupShutdownClientSocket {

	public static Logger logger=LoggerFactory.getLogger(StartupShutdownClientSocket.class);
	public static void main(String[] args)  {
		Socket socket=null;
		InputStream is=null;
		BufferedReader br=null;
		try {
			socket=new Socket("127.0.0.1", 52000);
			is=socket.getInputStream();
			br=new BufferedReader(new InputStreamReader(is));
			String result="";
			String str="";
			while((str=br.readLine())!=null){
				result+=str;
			}
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(socket!=null){
				try {
					socket.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
