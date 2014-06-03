import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import edu.hit.ir.ltp4j.*;


public class Test {
  public static void init(){
    Segmentor.create("../ltp_data/cws.model");
    Postagger.create("../ltp_data/pos.model");
    NER.create("../ltp_data/ner.model");
    Parser.create("../ltp_data/parser.model");
    SRL.create("../ltp_data/srl");
  }

  public static void ltp(String sent){
    ArrayList<String> sents = new ArrayList<String>(); 
    SplitSentence.splitSentence(sent,sents);
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

      Segmentor.segment(sents.get(m),words);
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

      Postagger.postag(words,postags);
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


      NER.recognize(words,postags,ners);
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

      Parser.parse(words,postags,heads,deprels);
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

      SRL.srl(words,postags,ners,heads,deprels,srls);
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
    Segmentor.release();
    Postagger.release();
    NER.release();
    Parser.release();
    SRL.release();
  }

public static void main(String[] args) {
  init();
  Scanner input = new Scanner(System.in);
  String sent;
  try {
    while((sent = input.nextLine())!=null){
      if(sent.length()>0){
        ltp(sent);
      }
    }
  }
  catch(Exception e) {
    release();
  }
}

}
