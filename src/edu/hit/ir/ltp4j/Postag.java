package edu.hit.ir.ltp4j;
import java.util.List;


public class Postag {
	static {
		System.loadLibrary("postagger_jni");
	}

	public static native int create(String modelPath);
	public static native int postag(List<String> words,
			List<String> tags);
	public static native void release();

}

