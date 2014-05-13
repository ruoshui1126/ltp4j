package edu.hit.ir.ltp4j;
import java.util.List;

public class NER {

	static {
		System.loadLibrary("ner_jni");
	}

	public static native int create(String modelPath);

	public static native int recognize(List<String> words,
			List<String> postags, List<String> ners);

	public static native void release();

}

