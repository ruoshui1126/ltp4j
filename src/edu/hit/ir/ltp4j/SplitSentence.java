package edu.hit.ir.ltp4j;
import java.util.List;
public class SplitSentence{
  static{
    System.loadLibrary("split_sentence_jni");
  }
  public static native void splitSentence(String sent,List<String> sents);
}
