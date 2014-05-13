LTP for Java编程接口v1.0
===============

下面的文档将介绍使用LTP编译产生的静态链接库编写程序的方法。

(注：2.30以后，LTP的所有模型文件均使用UTF8编码训练，故请确保待分析文本的编码为UTF8格式)

# 分词接口

edu.ir.hit.ltp4j.Segmentor

分词主要提供四个接口：

**int create(带外部词典)**

功能：

读取模型文件，初始化分词器。

参数：

| 参数名 | 参数描述 |
|--------|----------|
|String path | 指定模型文件的路径 |
|String lexicon_path | 指定外部词典路径。如果lexicon_path为NULL，则不加载外部词典 |

返回值：

成功加载模型返回1，否则返回-1。

**int create**

功能：

读取模型文件，初始化分词器。

参数：

| 参数名 | 参数描述 |
|--------|----------|
|String path | 指定模型文件的路径 |


返回值：

成功加载模型返回1，否则返回-1。


**void release**

功能：

释放模型文件，销毁分词器。

参数：无

返回值：无

**int Segment**

功能：

调用分词接口。

参数：

| 参数名 | 参数描述 |
|--------|----------|
|String | 待分词句子 |
|java.util.List< String > words| 结果分词序列 |

返回值：

返回结果中词的个数。

### 示例程序

一个简单的实例程序可以说明分词接口的用法：

    import java.util.ArrayList;
    import java.util.List;
    import edu.hit.ir.ltp4j.*;


    public class TestSegment {

    public static void main(String[] args) {
      if(Segmentor.create("../../../ltp_data/cws.model")<0){
       System.err.println("load failed");
       return;
     }      

     String sent = "我是中国人";
     List<String> words = new ArrayList<String>();

    int size = Segmentor.Segment(sent,words);

     for(int i = 0;i<size;i++) {
       System.out.print(words.get(i));
      if(i==size-1) {
       System.out.println();
      }      
      else{  
       System.out.print("      ");
     }      
     }      

      Segmentor.release();
    }      

    }


# 词性标注接口
edu.ir.hit.ltp4j.Postagger

词性标注主要提供三个接口

**int create**

功能：

读取模型文件，初始化词性标注器

参数：

|参数名 | 参数描述 |
|-------|----------|
|String path | 词性标注模型路径 |

返回值：

成功加载模型返回1，否则返回-1。

**void release**

功能：

释放模型文件，销毁分词器。

参数：无

返回值：无

**int Postag**

功能：

调用词性标注接口

参数：

|参数名 | 参数描述 |
|-------|----------|
|java.util.List< String >  words | 待标注的词序列 |
|java.util.List< String >  tags | 词性标注结果，序列中的第i个元素是第i个词的词性 |

返回值：

返回结果中词的个数

### 示例程序

一个简单的实例程序可以说明词性标注接口的用法：

    import java.util.ArrayList;
    import java.util.ArrayList;
    import java.util.List;
    import edu.hit.ir.ltp4j.*;


    public class TestPostag {

    public static void main(String[] args) {
     if(Postagger.create("../../../ltp_data/pos.model")<0) {
      System.err.println("load failed");
      return;
      }
      List<String> words= new ArrayList<String>();
     words.add("我");
     words.add("是");
     words.add("中国");
     words.add("人");
     List<String> postags= new ArrayList<String>();

     int size = Postagger.Postag(words,postags);
     for(int i = 0;i<size;i++) {
     System.out.print(words.get(i)+"_"+postags.get(i));
      if(i==size-1) {
        System.out.println();
     }
     else{
      System.out.print("|");
      }
      }

      Postagger.release();
      }

    }

# 命名实体识别接口

edu.ir.hit.ltp4j.NER

命名实体识别主要提供三个接口：

**int create**

功能：

读取模型文件，初始化命名实体识别器

参数：

| 参数名 | 参数描述 |
|-------|----------|
| String path | 命名实体识别模型路径 |

返回值：

成功加载模型返回1，否则返回-1。

**void release**

功能：

释放模型文件，销毁命名实体识别器。

参数：无

返回值：无

**int recognize**

功能：

调用命名实体识别接口

参数：

|参数名 | 参数描述 |
|-------|----------|
|java.util.List< String > words | 待识别的词序列 |
|java.util.List< String > postags | 待识别的词的词性序列 |
|java.util.List< String > tags | 命名实体识别结果，命名实体识别的结果为O时表示这个词不是命名实体，否则为{POS}-{TYPE}形式的标记，POS代表这个词在命名实体中的位置，TYPE表示命名实体类型|

返回值：

返回结果中词的个数

### 示例程序
    import java.util.ArrayList;
    import java.util.List;
    import edu.hit.ir.ltp4j.*;

    public class TestNer {

     public static void main(String[] args) {
      if(NER.nercreate("../../../ltp_data/ner.model")<0) {
       System.err.println("load failed");
        return;          
      }          
       List<String> words = new ArrayList<String>();
       List<String> tags = new ArrayList<String>();
       List<String> ners = new ArrayList<String>();
       words.add("中国");tags.add("ns");
       words.add("国际");tags.add("n");
       words.add("广播");tags.add("n");
       words.add("电台");tags.add("n");
       words.add("创办");tags.add("v");
       words.add("于");tags.add("p");
       words.add("1941年");tags.add("m");
       words.add("12月");tags.add("m");
       words.add("3日");tags.add("m");
       words.add("。");tags.add("wp");

       NER.recognize(words, tags, ners);

      for (int i = 0; i < words.size(); i++) {
        System.out.println(ners.get(i));
       }          

      NER.release();

     }
     }

# 依存句法分析接口
edu.ir.hit.ltp4j.Parser
依存句法分析主要提供三个接口：

**int create**

功能：

读取模型文件，初始化依存句法分析器

参数：

|参数名 | 参数描述 |
|---|---|
|String path | 依存句法分析模型路径 |

返回值：

成功加载模型返回1，否则返回-1。

**void release**

功能：

释放模型文件，销毁依存句法分析器。

参数：无

返回值：无

**int parse**

功能：

调用依存句法分析接口

参数：

|参数名 | 参数描述 |
|---|---|
|java.util.List< String > words | 待分析的词序列 |
|java.util.List< String > postags | 待分析的词的词性序列 |
|java.util.List< Integer > heads | 结果依存弧，heads[i]代表第i个词的父亲节点的编号 |
|java.util.List< String > deprels | 结果依存弧关系类型 |

返回值：

返回结果中词的个数

### 示例程序

一个简单的实例程序可以说明依存句法分析接口的用法：

    import java.util.ArrayList;
    import java.util.List;
    import edu.hit.ir.ltp4j.*;


    public class TestParser {

    public static void main(String[] args) {
     if(ParserJNI.parsercreate("../../../ltp_data/parser.model")<0) {
      System.err.println("load failed");
      return;
     }
      List<String> words = new ArrayList<String>();
      List<String> tags = new ArrayList<String>();
      words.add("一把手");tags.add("n");
      words.add("亲自");tags.add("d");
     words.add("过问");tags.add("v");
     words.add("。");tags.add("wp");
      List<Integer> heads = new ArrayList<Integer>();
      List<String> deprels = new ArrayList<String>();

      int size = Parser.parse(words,tags,heads,deprels);

      for(int i = 0;i<size;i++) {
        System.out.print(heads.get(i)+":"+deprels.get(i));
       if(i==size-1) {
          System.out.println();
       }
        else{
          System.out.print("        ");
        }
      }

      Parser.release();
    }



**注意，对于一个包含N个词的句子，句法分析返回的父节点返回在0至N之间，而语义角色标注的输入需要在-1至N-1之间。因此，若要在句法分析后进行语义角色标注，需要把heads作减一操作**

# 语义角色标注接口
edu.ir.hit.ltp4j.SRL
依存句法分析主要提供三个接口：

**int create**

功能：

读取模型文件，初始化语义角色标注分析器

参数：

|参数名 | 参数描述 |
|---|---|
|String path | 依存句法分析模型路径 |

返回值：

成功加载模型返回1，否则返回-1。

**void release**

功能：

释放模型文件，销毁语义角色标注分析器。

参数：无

返回值：无

**int srl**

功能：

调用语义角色标注接口

参数：

|参数名 | 参数描述 |
|---|---|
|java.util.List< String > words | 待分析的词序列 |
|java.util.List< String > postags | 待分析的词的词性序列 |
|java.util.List< String > ners| 待分析的命名实体序列 
|java.util.List< Integer > heads | 待分析的依存弧，heads[i]代表第i个词的父亲节点的编号 |
|java.util.List< String > deprels | 待分析的依存弧关系类型 |
| List< Pair< Integer, List< Pair< String, Pair< Integer, Integer > > > > > srls | 结果语义角色标注 |

返回值：

返回角色个数

### 示例程序

一个简单的实例程序可以说明依存句法分析接口的用法：

    import java.util.ArrayList;
    import java.util.List;
    import edu.hit.ir.ltp4j.*;

    public class Srl {
      public static void main(String[] args) {
        SRL.create("../../../ltp_data/srl");
        ArrayList<String> words = new ArrayList<String>();
        words.add("一把手");
        words.add("亲自");
        words.add("过问");
        words.add("。");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("n");
        tags.add("d");
        tags.add("v");
        tags.add("wp");
        ArrayList<String> ners = new ArrayList<String>();
        ners.add("O");
        ners.add("O");
        ners.add("O");
        ners.add("O");
        ArrayList<Integer> heads = new ArrayList<Integer>();
        heads.add(2);
        heads.add(2);
        heads.add(-1);
        heads.add(2);
        ArrayList<String> deprels = new ArrayList<String>();
        deprels.add("SBV");
        deprels.add("ADV");
        deprels.add("HED");
        deprels.add("WP");
        List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls = new ArrayList<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>>();
        SRL.srl(words, tags, ners, heads, deprels, srls);
        for (int i = 0; i < srls.size(); ++i) {
          System.out.println(srls.get(i).first + ":");
            for (int j = 0; j < srls.get(i).second.size(); ++j) {
              System.out.println("   tpye = "+ srls.get(i).second.get(j).first + " beg = "+ srls.get(i).second.get(j).second.first + " end = "+ srls.get(i).second.get(j).second.second);
            }
        }
        SRL.release();
      }

    }
