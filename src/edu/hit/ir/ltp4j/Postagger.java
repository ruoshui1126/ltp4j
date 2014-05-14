package edu.hit.ir.ltp4j;
import java.util.List;
import java.io.IOException;

public class Postagger {
        static {
                try {
                        NativeUtils.loadLibraryFromJar("/libs/libpostagger.so");
                        NativeUtils.loadLibraryFromJar("/libs/libpostagger_jni.so");
                } catch (IOException e) {
                        e.printStackTrace(); // This is probably not the best way to handle exception :-)        
                }
        }

	public static native int create(String modelPath);
	public static native int create(String modelPath, String lexiconPath);
	public static native int postag(List<String> words,
			List<String> tags);
	public static native void release();

}

