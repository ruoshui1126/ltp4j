import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import edu.hit.ir.ltpNative.*;


public class Test {
  public static void init(){
    SegmentJNI.segmentorCreate("../../../ltp_data/cws.model");
    PostagJNI.postaggerCreate("../../../ltp_data/pos.model");
    NerJNI.nerCreate("../../../ltp_data/ner.model");
    ParserJNI.parserCreate("../../../ltp_data/parser.model");
    SrlJNI.srlCreate("../../../ltp_data/srl");
  }

  public static void ltp(String sent){
    ArrayList<String> sents = new ArrayList<String>(); 
    SplitSentenceJNI.splitSentence(sent,sents);
    System.out.println(sent);
    System.out.println("sents:"+sents.size());
    for(int m = 0;m<sents.size();m++){
      System.out.println(sents.get(m));
      ArrayList<String> words = new ArrayList<String>(); 
      ArrayList<String> postags = new ArrayList<String>(); 
      ArrayList<String> ners = new ArrayList<String>(); 
      ArrayList<Integer> heads = new ArrayList<Integer>(); 
      ArrayList<String> deprels = new ArrayList<String>(); 
      List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls = 
        new ArrayList<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>>();

      SegmentJNI.segmentorSegment(sents.get(m),words);
      int size = words.size();
      System.out.println("words:"+size);
      for(int i = 0;i<size;i++) {
        System.out.print(words.get(i));
        if(i==size-1) {
          System.out.println();
        }
        else{
          System.out.print("	");
        }
      }

      PostagJNI.postaggerPostag(words,postags);
      size = postags.size();
      System.out.println("postags:"+size);
      for(int i = 0;i<size;i++) {
        System.out.print(postags.get(i));
        if(i==size-1) {
          System.out.println();
        }
        else{
          System.out.print("	");
        }
      }


      NerJNI.nerRecognize(words,postags,ners);
      size = postags.size();
      System.out.println("ners:"+size);
      for(int i = 0;i<size;i++) {
        System.out.print(postags.get(i));
        if(i==size-1) {
          System.out.println();
        }
        else{
          System.out.print("	");
        }
      }

      ParserJNI.parserParse(words,postags,heads,deprels);
      size = heads.size();
      System.out.println("parser:"+size);
      for(int i = 0;i<size;i++) {
        System.out.print(heads.get(i)+":"+deprels.get(i));
        if(i==size-1) {
          System.out.println();
        }
        else{
          System.out.print("	");
        }
      }

      for(int i = 0;i<heads.size();i++) {
        heads.set(i,heads.get(i)-1);
      }

      SrlJNI.srlSrl(words,postags,ners,heads,deprels,srls);
      size = srls.size();
      System.out.println("srl:"+size);
      for (int i = 0; i < srls.size(); i++) {
        System.out.print(srls.get(i).first + " ->");
        for (int j = 0; j < srls.get(i).second.size(); j++) {
          System.out.print(srls.get(i).second.get(j).first + ": beg = "+ srls.get(i).second.get(j).second.first + " end = "
          					+ srls.get(i).second.get(j).second.second + ";");
        }
        System.out.println();
      }
    }
  }
  public static void release(){
    SegmentJNI.segmentorRelease();
    PostagJNI.postaggerRelease();
    NerJNI.nerRelease();
    ParserJNI.parserRelease();
    SrlJNI.srlRelease();
  }

public static void main(String[] args) {
  init();
  Scanner input = new Scanner(System.in);
  String sent;
  while((sent = input.nextLine())!=null){
    if(sent.length()>0){
      ltp(sent);
    }
    ltp(sent);
  }
  release();
}

}
