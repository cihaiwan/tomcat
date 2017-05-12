package com.zfcode;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartupShutdownServerSocket {
	public static ServerSocket serverSocket=null;
	public static Logger logger=LoggerFactory.getLogger(StartupShutdownClientSocket.class);
	
	public static void main(String[] args) throws Exception {
		if(serverSocket==null){
			try {
				serverSocket=new ServerSocket(52000);
			} catch (Exception e) {
				logger.error("port被占用,无法启动服务");
				return ;
			}
		}
		Socket socket=null;
		while(true){
			OutputStream os=null;
			BufferedWriter bw=null;
			try{
				socket=serverSocket.accept();
				int i=5;
				boolean success=false;
				try {
					while(i>=0){
						logger.info("coming in ......................");
						Thread.sleep(1000);
						String tomcat=System.getenv("CATALINA_HOME").replaceAll("\\\\","/")+"/bin";
						String cmd2 = "\""+tomcat+"/shutdown.bat\"";  
				        		Process process=Runtime.getRuntime().exec(cmd2);
				        		logger.info("shutdown:"+tomcat+"/shutdown.bat");
				        		Thread.sleep(5000);
				        		process.destroy();
				        		int exit=process.exitValue();
				        		System.out.println(process.exitValue());
				        		logger.info("startup:"+tomcat+"/startup.bat");

				        String cmd = "\""+tomcat+"/startup.bat\"";  
				        Process process2=Runtime.getRuntime().exec(cmd); 
				        process2.waitFor();
				        int exit2=process2.exitValue();
				        System.out.println(exit2);
				        if(exit!=0||exit2!=0){
				        	logger.info("剩余启动次数"+(i-1));
				        	i--;
				        	Thread.sleep(60000);
				        }else{
				        	success=true;
				        	break;
				        }
					}
					os=socket.getOutputStream();
					bw=new BufferedWriter(new OutputStreamWriter(os));
					String msg="";
					if(success){
						msg="启动成功";
					}else{
						msg="启动失败";
					}
					logger.info(msg);
					bw.write(msg);
			        } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				if(bw!=null){
					bw.close();
				}
				if(os!=null){
					os.close();
				}
				if(socket!=null){
					socket.close();
				}
				
				
			}
		}
	}
}
