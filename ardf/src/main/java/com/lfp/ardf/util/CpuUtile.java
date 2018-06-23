package com.lfp.ardf.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.regex.Pattern;

/**
 * CUP工具
 * Created by Administrator on 2016/1/13.
 */
public final class CpuUtile {

    //Private Class to display only CPU devices in the directory listing
    private static final class CpuFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            //Check if filename is "cpu", followed by a single digit number
            if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                return true;
            }
            return false;
        }
    }

    /**
     * 获得CPU核心数目
     * @return int
     */
    public static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 获取CPU最小频率（单位KHZ）
     * @return String
     */
    public static String getMinCpuFrequence() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 实时获取CPU当前频率（单位KHZ）
     * @return String
     */
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取CPU名字
     * @return String
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得CPU信息
     * @return String
     */
    public static String getCupInfo() {
        return MessageFormat.format("CPU_Info：\nName:{0}\n核心数:{1}核\n最小频率:{2}khz\n当前频率:{3}khz"
                , getCpuName()
                , getNumCores()
                , getMinCpuFrequence()
                , getCurCpuFreq()
        );
    }

    /**
     * 根据CPU核心数计算最优线程数<br>
     * CPU敏感的程序，线程数大于处理器个数是没有意义的
     *
     * @param blockingRate 阻塞率
     * @param defualt      默认线程个数
     * @return 计算之后的线程个数
     */
    public static int getThreadValue(float blockingRate, int defualt) {
        int maxConcurrency;
        try {
            maxConcurrency = (int) (getNumCores() * blockingRate);
            if (maxConcurrency < 1) maxConcurrency = 1;
        } catch (Exception e) {
            e.printStackTrace();
            maxConcurrency = defualt;
        }
        return maxConcurrency;
    }

}
