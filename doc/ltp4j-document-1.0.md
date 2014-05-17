LTP for Java编程接口v1.0
===============

下面的文档将介绍使用LTP编译产生的静态链接库编写程序的方法。

(注：2.30以后，LTP的所有模型文件均使用UTF8编码训练，故请确保待分析文本的编码为UTF8格式)

#简介
ltp4j是对[LTP(Language Technology Platform)](https://github.com/ruoshui1126/ltp)接口的一个Java封装，方便Java用户在本地使用ltp。利用JNI技术实现。
ltp4j项目由两部分组成
* Java接口程序，利用ant能够直接编译构建为ltp4j.jar，方便用户导入使用。
* C++代理程序，在项目/jni/目录下实现Java接口中的功能，利用CMake编译构建为动态库。

#如何安装ltp4j

##编译Java接口程序
ltp4j.jar使用ant编译工具编译。 在命令行环境下，可以在项目根目录下使用

    ant

命令直接编译。 编译成功后，将在build_jar/jar下产生tp4j.jar。

如果使用Eclipse，可以按照"File > New > Project... > Java Project from Existing Ant Buildfile"的方式从build.xml中创建项目。 选择next后，在Ant buildfile:一栏中
填入build.xml的路径。 这里假设项目路径为F:\JNI\ltp4j, build.xml的路径就是E:\JNI\ltp4j\build.xml。


![Eclipse](https://raw.githubusercontent.com/ruoshui1126/ltp4j/master/doc/java.png)

点击Finish就导入了项目。

在导入项目后，右键build.xml选择2 Ant Build。 在弹出的对话框中的选择main选项卡，并在Base Directory:中填入项目路径。 在本例子里，需要填入E:\JAVA\ltp4j。

![BaseDirectory](https://raw.githubusercontent.com/ruoshui1126/ltp4j/master/doc/BaseDirectory.png)

填好后执行run，build/jar下产生名为LTPService.jar的jar文件。

##编译C++代理程序


代理程序jni动态库依赖于ltp的动态库，所以在编译jni之前必须在本地安装了ltp，[LTP使用文档v3.0](https://github.com/HIT-SCIR/ltp/blob/master/doc/ltp-document-3.0.md)详细介绍了ltp，如果你之前对ltp不太了解，建议通篇阅读一下，如果对ltp比较熟悉，可以直接看“开始使用LTP”部分来安装ltp。


###安装CMake
jni程序使用编译工具CMake构建项目。在安装jni之前，你需要首先安装CMake。CMake的网站在[这里](http://www.cmake.org)。如果你是Windows用户，请下载CMake的二进制安装包；
如果你是Linux，Mac OS或Cygwin的用户，可以通过编译源码的方式安装CMake，当然，你也可以使用Linux的软件源来安装。


### Windows(MSVC)编译

第一步：配置ltp的安装路径

因为jni依赖于ltp，所以在编译过程中需要知道ltp的路径。修改项目根目录下的CMakeLists.txt，把其中的set (LTP_HOME "/home/yijialiu/work/ltp/")后面的路径改成你的ltp的安装路即可。

第二步：构建VC Project

在项目文件夹下新建一个名为build的文件夹，使用CMake Gui，在source code中填入项目文件夹，在binaries中填入build文件夹。然后Configure -> Generate。

![win-cmake](https://raw.githubusercontent.com/ruoshui1126/ltp4j/master/doc/cmake.png)

或者在命令行build 路径下运行

        cmake ..

第二步：编译

### Linux，Mac OSX和Cygwin编译
Linux、Mac OSX(*)和Cygwin的用户，可以直接在项目根目录下使用命令


        cmake .
        make


进行编译。

编译成功后，会在libs文件夹下生成以下一些动态库(**)

| 程序名 | 说明 |
| ------ | ---- |
| split_sentence_jni.so | 分句动态库 |
| segmentor_jni.so | 分词动态库 |
| postagger_jni.so| 词性标注动态库 |
| parser_jni.so | 依存句法分析动态库 |
| ner_jni.so | 命名实体识别动态库 |
| srl_jni.so | 语义角色标注动态库 |

###注意事项

* **该处编译需要设置Java环境变量JAVA_HOME**。
* **需要保持c++编译器与JDK同是32位或者64位，否则JVM不能加载生成的动态库**

#开始使用

构建需要在本地使用ltp的工程
* 导入ltp4j.jar
* windows下将libs文件夹中生成的所有动态库、以及原ltp lib文件夹下的splitsnt、segmentor、postagger、ner、parser、srl 6个动态库拷贝到项目根目录
* linux下export LD_LIBRARY_PATH=#jni动态库路径#

接下来便可仿照下面各个接口的例子使用ltp啦。

#编程接口

## 分词接口

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


## 词性标注接口
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

## 命名实体识别接口

edu.ir.hit.ltp4j.NER

命名实体识别主要提供三个接口：

**int create**

功能：

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

## 依存句法分析接口
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




## 语义角色标注接口
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
    
###注意事项

* **对于一个包含N个词的句子，句法分析返回的父节点范围在0至N之间，而语义角色标注的输入需要在-1至N-1之间。因此，若要在句法分析后进行语义角色标注，需要把heads作减一操作。**
