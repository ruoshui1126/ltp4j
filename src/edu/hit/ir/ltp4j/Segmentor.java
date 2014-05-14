package edu.hit.ir.ltp4j;
import java.util.List;
import java.io.IOException;


public class Segmentor {
	static {	 
		try {	 
			NativeUtils.loadLibraryFromJar("/libs/libboost_regex.so");	 
			NativeUtils.loadLibraryFromJar("/libs/libsegmentor.so");	 
			NativeUtils.loadLibraryFromJar("/libs/libsegmentor_jni.so");	 
		} catch (IOException e) {	 
			e.printStackTrace(); // This is probably not the best way to handle exception :-)	 
		}	 
	}
	public static native int create(String modelPath);
	public static native int create(String modelPath,String lexiconPath);
	public static native int segment(String sent,List<String> words);
	public static native void release();
}

