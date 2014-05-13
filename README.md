# ltp4j: Language Technology Platform For Java

ltp4j是对[ltp](https://github.com/ruoshui1126/ltp)接口的一个Java封装，方便Java用户在本地使用ltp。利用JNI技术实现。

##如何安装ltp4j

ltp4j由两部分组成，分别是Java接口代码和ltp代码，分别在ltp4j/java与ltp4j/ltp目录下。

使用ltp4j需要编译Java接口代码生成LTPNative.jar，编译ltp生成所需的动态库 split_sentence_jni、segmentor_jni、postagger_jni、ner_jni、parser_jni、srl_jni，最后将ltpNtive.jar导入你的Java工程、将这6个动态库拷贝至项目根目录即可。

###编译LTPNative.jar
LTPNative.jar使用ant编译工具编译。 在命令行环境下，可以在项目根目录下使用

    ant
    
命令直接编译。 编译成功后，将在build/jar下产生名为LTPNative.jar的jar文件。

如果使用Eclipse编译，可以[参考这里](http://www.ltp-cloud.com/document/old/#api2_java)。

###编译ltp
请参考[LTP使用文档v3.0](https://github.com/HIT-SCIR/ltp/blob/master/doc/ltp-document-3.0.md)的“开始使用LTP”部分。

* **注意该处编译需要设置Java环境变量JAVA_HOME**。

##编程接口
见[接口说明文档](https://github.com/ruoshui1126/ltp4j/blob/master/doc/ltp4j-document-1.0.md)
* **注意，对于一个包含N个词的句子，句法分析返回的父节点范围在0至N之间，而语义角色标注的输入需要在-1至N-1之间。因此，若要在句法分析后进行语义角色标注，需要把heads作减一操作。**
