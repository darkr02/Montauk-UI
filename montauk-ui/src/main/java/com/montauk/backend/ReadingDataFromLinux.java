/***********************************************************************

Copyright (c) 2014 CA.  All rights reserved.

This software and all information contained therein is confidential and
proprietary and shall not be duplicated, used, disclosed or disseminated
in any way except as authorized by the applicable license agreement,
without the express written permission of CA ("CA"). All authorized
reproductions must be marked with this language.

EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT
PERMITTED BY APPLICABLE LAW, CA PROVIDES THIS SOFTWARE WITHOUT WARRANTY
OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY IMPLIED WARRANTIES OF
MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN NO EVENT WILL
CA BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY LOSS OR DAMAGE,
DIRECT OR INDIRECT, FROM THE USE OF THIS SOFTWARE, INCLUDING WITHOUT
LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION, GOODWILL, OR LOST DATA,
EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR DAMAGE.

***********************************************************************/

package com.montauk.backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;

/**
 * This class fetches data from Linux box for clusters.
 * @author Krishnendu.Daripa
 */
public class ReadingDataFromLinux 

{
	public static String ReadClusterData(String Command) 
	{
        //String host="kotpa04-i113563";
        Properties cred = new Properties(); 
        String inputString = "";
        FileInputStream credfile = null;
        String sHost;
		try {
			credfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\ca\\montauk\\objectrepo\\credentials.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			cred.load(credfile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        		
        try
        {
             
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
			if (System.getProperty("Host")==null)
			{
				sHost = cred.getProperty("serverid");
			}
			else
			{
				sHost = System.getProperty("Host");
			}
            Session session=jsch.getSession(cred.getProperty("serveruser"), sHost, 22);
            session.setPassword(cred.getProperty("serverpassword"));
            session.setConfig(config);
            session.connect();
            System.out.println("Connected to Linuxbox. ");
             
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(Command);
            channel.setInputStream(null);
            //((ChannelExec)channel).setErrStream(System.err);
             
            InputStream in=channel.getInputStream();
            channel.connect();
            
            //String newLine = System.getProperty("line.separator");
            byte[] tmp=new byte[1024];
            while(true)
            {
              while(in.available()>0)
              {
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                //System.out.println("DATA: "/n);
                //System.out.println(new String(tmp, 0, i));
                inputString = inputString + new String(tmp, 0, i);
              }
              if(channel.isClosed())
              {
            	  System.out.println("Data fetching From Linuxbox is Completed");
            	  credfile.close();
            	  channel.disconnect();
            	  session.disconnect();
            	  return inputString;
              }
              try{Thread.sleep(1000);}catch(Exception ee){}
            }
        }
        catch(Exception e)
        {
        	System.out.println("Data fetching From Linuxbox is failed");
            e.printStackTrace();
            return inputString; 
        }
    }

	/*public static String ReadJobDetails(String jobRefId) 
	{
        //String host="kotpa04-i113563";
        Properties obj = new Properties(); 
        String inputString = "";
        FileInputStream objfile = null;
		try {
			System.out.println(System.getProperty("user.dir"));
			objfile = new FileInputStream((System.getProperty("user.dir") +"\\src\\ca\\montauk\\objectrepo\\linuxmap.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			obj.load(objfile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
             		
        		
        try
        {
             
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(obj.getProperty("user"), obj.getProperty("host"), 22);
            session.setPassword(obj.getProperty("password"));
            session.setConfig(config);
            session.connect();
            System.out.println("Connected to Linuxbox");
             
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(obj.getProperty("getjobdetails"));
            channel.setInputStream(null);
            //((ChannelExec)channel).setErrStream(System.err);
             
            InputStream in=channel.getInputStream();
            channel.connect();
            
            //String newLine = System.getProperty("line.separator");
            byte[] tmp=new byte[1024];
            while(true)
            {
              while(in.available()>0)
              {
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                //System.out.println("DATA: "/n);
                //System.out.println(new String(tmp, 0, i));
                inputString = inputString + new String(tmp, 0, i);
              }
              if(channel.isClosed())
              {
            	 System.out.println("DONE");
            	 channel.disconnect();
            	 session.disconnect();
            	 return inputString;
            	 
              }
              try{Thread.sleep(1000);}catch(Exception ee){}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return inputString; 
        }
    }*/
	
	
	public static String GetJobDetails(String curlCmd,String jobRefId) 
	{
        //String host="kotpa04-i113563";
        Properties obj = new Properties(); 
        String inputString = "";
        FileInputStream objfile = null;
		try {
			System.out.println(System.getProperty("user.dir"));
			objfile = new FileInputStream((System.getProperty("user.dir") +"\\src\\main\\java\\ca\\montauk\\objectrepo\\linuxmap.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			obj.load(objfile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
             		
        		
        try
        {
             
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(obj.getProperty("user"), obj.getProperty("host"), 22);
            session.setPassword(obj.getProperty("password"));
            session.setConfig(config);
            session.connect();
            System.out.println("Connected to Linuxbox");
             
            Channel channel=session.openChannel("exec");
            System.out.println(curlCmd+"/"+jobRefId);
            
            ((ChannelExec)channel).setCommand(curlCmd+"/"+jobRefId);
            channel.setInputStream(null);
            //((ChannelExec)channel).setErrStream(System.err);
             
            InputStream in=channel.getInputStream();
            channel.connect();
            
            //String newLine = System.getProperty("line.separator");
            byte[] tmp=new byte[1024];
            while(true)
            {
              while(in.available()>0)
              {
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                //System.out.println("DATA: "/n);
                //System.out.println(new String(tmp, 0, i));
                inputString = inputString + new String(tmp, 0, i);
              }
              if(channel.isClosed())
              {
            	 System.out.println("DONE");
            	 channel.disconnect();
            	 session.disconnect();
            	 System.out.println(inputString);
            	 return inputString;
              }
              try{Thread.sleep(1000);}catch(Exception ee){}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(inputString);
            return inputString; 
        }
    }
}
