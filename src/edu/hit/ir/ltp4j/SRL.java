package edu.hit.ir.ltp4j;
import java.util.List;
import java.io.IOException;

public class SRL {

	static {
                try {
                        NativeUtils.loadLibraryFromJar("/libs/libutil.so");
                        NativeUtils.loadLibraryFromJar("/libs/libmaxent.so");
                        NativeUtils.loadLibraryFromJar("/libs/libsrl.so");
                        NativeUtils.loadLibraryFromJar("/libs/libsrl_jni.so");
                } catch (IOException e) {
                        e.printStackTrace(); // This is probably not the best way to handle exception :-)        
                }
        }
	public static native int create(String modelPath);

	public static native int srl(
			List<String> words,
			List<String> tags,
			List<String> ners,
			List<Integer> heads,
			List<String> deprels,
			List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls);

	public static native void release();
}

