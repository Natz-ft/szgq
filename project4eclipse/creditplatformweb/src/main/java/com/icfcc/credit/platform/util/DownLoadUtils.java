package com.icfcc.credit.platform.util;

/** 
 * 说明： 
 * 每一个线程下载的位置计算方式： 
 * 开始位置： 
 * (线程id - 1)*每一块大小 
 * 结束位置： 
 * (线程id*每一块大小) - 1  
 *  ---注意有时候不一定能够整除，所以最后一个线程的结束位置应该是文件的末尾 
 *   
 *  步骤： 
 *  1.本地创建一个大小跟服务器文件相同的临时文件 
 *  2.计算分配几个线程去下载服务器上的资源，知道每个线程下载文件的位置 
 *  3.开启三个线程，每一个线程下载对应位置的文件 
 *  4.如果所有的线程，都把自己的数据下载完毕后，服务器上的资源都被下载到本地了 
 */
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <文件下载>
 * 
 * @version [1.0, 2016年3月24日]
 */
@Slf4j
public class DownLoadUtils {
	public static int threadCount = 10;
	private static Logger log = LoggerFactory.getLogger(DownLoadUtils.class);

	public static void FileDownLoad(String webAddr, String savePath) throws Exception {
		// 判断文件夹是否存在
		File file = new File(ImageUtils.getFileLocation(savePath));
		if (!file.exists()) {
			file.mkdirs();
		}

		// 1.连接服务器，获取一个文件，获取文件的长度，在本地创建一个跟服务器一样大小的临时文件
		URL url = new URL(webAddr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		int code = conn.getResponseCode();
		if (code == 200) {
			// 服务器端返回的数据的长度，实际上就是文件的长度
			int length = conn.getContentLength();
			log.info("文件总长度：" + length);

			// 假设是3个线程去下载资源。
			// 平均每一个线程下载的文件大小.
			int blockSize = length / threadCount;
			List<Thread> arr = new ArrayList<Thread>();
			for (int threadId = 1; threadId <= threadCount; threadId++) {
				// 第一个线程下载的开始位置
				int startIndex = (threadId - 1) * blockSize;
				int endIndex = threadId * blockSize - 1;
				if (threadId == threadCount) {
					// 最后一个线程下载的长度要稍微长一点
					endIndex = length;
				}
				log.info("线程：" + threadId + "下载:---" + startIndex + "--->" + endIndex);
				Thread myThread = new DownLoadThread(webAddr, threadId, startIndex, endIndex, savePath);
				myThread.start();
				arr.add(myThread);
			}

			for (Thread t : arr) {
				while (t.isAlive()) {
				}
			}
			log.info("下载处理完成");
		} else {
			log.info("服务器错误!");
		}
	}

	/**
	 * 下载文件的子线程 每一个线程下载对应位置的文件
	 * 
	 * @author jie
	 * 
	 */
	public static class DownLoadThread extends Thread {
		private int threadId;

		private int startIndex;

		private int endIndex;

		private String webAddr;

		private String savePath;

		/**
		 * @param path
		 *            下载文件在服务器上的路径
		 * @param threadId
		 *            线程Id
		 * @param startIndex
		 *            线程下载的开始位置
		 * @param endIndex
		 *            线程下载的结束位置
		 */
		public DownLoadThread(String webAddr, int threadId, int startIndex, int endIndex, String savePath) {
			super();
			this.webAddr = webAddr;
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.savePath = savePath;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(webAddr);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				// 重要:请求服务器下载部分文件 指定文件的位置
				conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
				// 从服务器请求全部资源返回200 ok如果从服务器请求部分资源 返回 206 ok
				int code = conn.getResponseCode();
				log.info("code:" + code);
				InputStream is = conn.getInputStream();// 已经设置了请求的位置，返回的是当前位置对应的文件的输入流
				RandomAccessFile raf = new RandomAccessFile(savePath, "rwd");
				// 随机写文件的时候从哪个位置开始写
				raf.seek(startIndex);// 定位文件

				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					raf.write(buffer, 0, len);
				}
				is.close();
				raf.close();
				log.info("线程：" + threadId + "下载完毕");
			} catch (Exception e) {
				log.error("下载失败 e:{}", e);
			}
		}

	}
}
