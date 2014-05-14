package edu.hit.ir.ltp4j;
import java.util.List;
import java.io.IOException;

public class SplitSentence{
        static {
                try {
                        NativeUtils.loadLibraryFromJar("/libs/libsplitsnt.so");
                        NativeUtils.loadLibraryFromJar("/libs/libsplit_sentence_jni.so");
                } catch (IOException e) {
                        e.printStackTrace(); // This is probably not the best way to handle exception :-)        
                }
        }
	public static native void splitSentence(String sent,List<String> sents);
}
