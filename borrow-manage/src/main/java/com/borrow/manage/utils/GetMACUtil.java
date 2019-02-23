package com.borrow.manage.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class GetMACUtil
{
    public static String getOSName()
    {
        return System.getProperty("os.name").toLowerCase();
    }


    public static String getUnixMACAddress()
    {
        return "00-16-3E-0A-BA-EF";
    }

    public static String getWindowsMACAddress()
    {
        String str1 = null;
        BufferedReader localBufferedReader = null;
        Process localProcess = null;
        try
        {
            localProcess = Runtime.getRuntime().exec("ipconfig /all");
            localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream(), "gbk"));
            String str2 = null;
            int i = -1;
            while ((str2 = localBufferedReader.readLine()) != null)
            {
                i = str2.toLowerCase().indexOf("physical address");
                if (i >= 0)
                {
                    i = str2.indexOf(":");
                    if (i >= 0) {
                        str1 = str2.substring(i + 1).trim();
                    }
                }
            }
        }
        catch (IOException localIOException2)
        {
            localIOException2.printStackTrace();
        }
        finally
        {
            try
            {
                if (localBufferedReader != null) {
                    localBufferedReader.close();
                }
            }
            catch (IOException localIOException4)
            {
                localIOException4.printStackTrace();
            }
            localBufferedReader = null;
            localProcess = null;
        }
        return str1;
    }

    public static String getWindowsMACAddressOfWin7()
    {
        String str1 = null;
        BufferedReader localBufferedReader = null;
        Process localProcess = null;
        try
        {
            localProcess = Runtime.getRuntime().exec("ipconfig /all");
            localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream(), "gbk"));
            String str2 = null;
            int i = -1;
            while ((str2 = localBufferedReader.readLine()) != null)
            {
                i = str2.toLowerCase().indexOf("物理地址");
                if (i >= 0)
                    {
                    i = str2.indexOf(":");
                    if (i >= 0) {
                        str1 = str2.substring(i + 1).trim();
                    }
                }
            }
        }
        catch (IOException localIOException2)
        {
            localIOException2.printStackTrace();
        }
        finally
        {
            try
            {
                if (localBufferedReader != null) {
                    localBufferedReader.close();
                }
            }
            catch (IOException localIOException4)
            {
                localIOException4.printStackTrace();
            }
            localBufferedReader = null;
            localProcess = null;
        }
        return str1;
    }

    public static String getMacStr()
    {
        String str1 = getOSName();
        String str2 = "";
        if (str1.startsWith("windows"))
        {
            str2 = getWindowsMACAddress();
            if ((null == str2) || (str2.equals(""))) {
                str2 = getWindowsMACAddressOfWin7();
            }
        }
        else
        {
            str2 = getUnixMACAddress();
        }
        return str2;
    }
}
