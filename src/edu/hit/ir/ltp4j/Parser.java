package edu.hit.ir.ltp4j;
import java.util.List;
import java.io.IOException;

public class Parser {

        static {
                try {
                        NativeUtils.loadLibraryFromJar("/libs/libparser.so");
                        NativeUtils.loadLibraryFromJar("/libs/libparser_jni.so");
                } catch (IOException e) {
                        e.printStackTrace(); // This is probably not the best way to handle exception :-)        
                }
        }
	public static native int create(String modelPath);

	public static native int parse(List<String> words,
			List<String> tags, List<Integer> heads,
			List<String> deprels);

	public static native void release();
}

