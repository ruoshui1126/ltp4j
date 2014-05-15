package edu.hit.ir.ltp4j;
import java.util.List;

public class Parser {

        static {
		System.loadLibrary("parser_jni");
        }
	public static native int create(String modelPath);

	public static native int parse(List<String> words,
			List<String> tags, List<Integer> heads,
			List<String> deprels);

	public static native void release();
}

