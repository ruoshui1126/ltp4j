package edu.hit.ir.ltp4j;
import java.util.List;
import java.io.IOException;

public class NER {
	static {
                try {
                        NativeUtils.loadLibraryFromJar("/libs/libner.so");
                        NativeUtils.loadLibraryFromJar("/libs/libner_jni.so");
                } catch (IOException e) {
                        e.printStackTrace(); // This is probably not the best way to handle exception :-)        
                }
        } 
	public static native int create(String modelPath);

	public static native int recognize(List<String> words,
			List<String> postags, List<String> ners);

	public static native void release();

}

