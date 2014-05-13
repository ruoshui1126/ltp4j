package edu.hit.ir.ltp4j;
import java.util.List;


public class Segmentor {
	static{
		System.loadLibrary("segmentor_jni");
	}
	public static native int create(String modelPath);
	public static native int create(String modelPath,String lexiconPath);
	public static native int segment(String sent,List<String> words);
	public static native void release();
}

