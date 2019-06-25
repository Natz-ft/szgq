package com.icfcc.ssrp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * @Author : Liuzz
 * @Description: 敏感词过滤 工具类
 * @Date : 2018/5/24  09:21
 * @Modified By : whyxs @ 20190624
 */
public class SensitiveWordFilter {
    private StringBuilder replaceAll;//初始化
    private String encoding = "UTF-8";
    private String replceStr = "*";
    private int replceSize = 500;
    private String[] fileName = {"广告类.txt","政治类.txt", "涉枪涉爆类.txt","色情类.txt", "通用类.txt"};
    private List<String> arrayList;

    /**
     *
     * @param fileName 词库文件名(含后缀)
     */
    public SensitiveWordFilter(String[] fileName) {
        this.fileName = fileName;
    }

    /**
     * @param replceStr  敏感词被转换的字符
     * @param replceSize 初始转义容量
     */
    public SensitiveWordFilter(String replceStr, int replceSize) {
        this.replceStr = replceStr;
        this.replceSize = replceSize;
    }

    public SensitiveWordFilter() {
    }

    /**
     * @param str 将要被过滤信息
     * @return 过滤后的信息
     */
    public Map<String,Object> filterInfo(String str) {
        Map<String,Object> map = new HashMap<>();
        StringBuilder buffer = new StringBuilder(str);
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>(arrayList.size());
        List<String> cws = new ArrayList<>();
        String temp;
        for (int x = 0; x < arrayList.size(); x++) {
            temp = arrayList.get(x);
            int findIndexSize = 0;
            for (int start = -1; (start = buffer.indexOf(temp, findIndexSize)) > -1; ) {
                findIndexSize = start + temp.length();//从已找到的后面开始找
                Integer mapStart = hash.get(start);//起始位置
                //满足1个，即可更新map
                if (mapStart == null || (mapStart != null && findIndexSize > mapStart)){
                    hash.put(start, findIndexSize);
                    cws.add(str.substring(start,findIndexSize));
                }
            }
        }
        Collection<Integer> values = hash.keySet();
        for (Integer startIndex : values) {
            Integer endIndex = hash.get(startIndex);
            buffer.replace(startIndex, endIndex, replaceAll.substring(0, endIndex - startIndex));
        }
        hash.clear();

        map.put("code",cws.size());//作为是否有敏感词的依据
        map.put("censorWords",cws);
        map.put("originalStr",str);
        map.put("processStr",buffer.toString());

        return map;
    }

    /**
     * 初始化敏感词库
     */
    public void InitializationWork() {
        replaceAll = new StringBuilder(replceSize);
        for (int x = 0; x < replceSize; x++) {
            replaceAll.append(replceStr);
        }
        //加载词库
        arrayList = new ArrayList<String>();
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;

        try {
            for(int i = 0;i < fileName.length;i++ ){
                String file = fileName[i];
                read = new InputStreamReader(SensitiveWordFilter.class.getClassLoader().getResourceAsStream("wordFilterDict/"+file), encoding);
                bufferedReader = new BufferedReader(read);
                for (String txt = null; (txt = bufferedReader.readLine()) != null; ) {
                    if (!arrayList.contains(txt))
                        arrayList.add(txt);
                    if(file.equals("涉枪涉爆类.txt")){
                        System.out.println(txt);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedReader)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != read)
                    read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public StringBuilder getReplaceAll() {
        return replaceAll;
    }

    public void setReplaceAll(StringBuilder replaceAll) {
        this.replaceAll = replaceAll;
    }

    public String getReplceStr() {
        return replceStr;
    }

    public void setReplceStr(String replceStr) {
        this.replceStr = replceStr;
    }

    public int getReplceSize() {
        return replceSize;
    }

    public void setReplceSize(int replceSize) {
        this.replceSize = replceSize;
    }

    public String[] getFileName() {
        return fileName;
    }

    public void setFileName(String[] fileName) {
        this.fileName = fileName;
    }

    public List<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<String> arrayList) {
        this.arrayList = arrayList;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
