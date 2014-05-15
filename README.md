# ltp4j: Language Technology Platform For Java

ltp4j是对[ltp](https://github.com/ruoshui1126/ltp)接口的一个Java封装，方便Java用户在本地使用ltp。利用JNI技术实现。

##如何安装ltp4j

ltp4j由两部分组成，分别是Java接口代码和ltp的JNI封装代码，分别在src/与jni/目录下。

要使用ltp4j需要编译Java接口代码生成ltp4j.jar以及编译jni代码生成c++动态库，然后将二者导入你的工程即可。
* **注意需要保持c++编译器与JDK同是32位或者64位，否则JVM不能加载生成的动态库**


###编译ltp4j.jar
ltp4j.jar使用ant编译工具编译。 在命令行环境下，可以在项目根目录下使用

    ant

命令直接编译。 编译成功后，将在build/jar下产生名为LTPNative.jar的jar文件。


如果使用Eclipse，可以按照"File > New > Project... > Java Project from Existing Ant Buildfile"的方式从build.xml中创建项目。 选择next后，在Ant buildfile:一栏中填入build.xml的路径。 这里假设项目路径为E:\work\projects\ltp4j。 build.xml的路径就是E:\work\projects\ltp4j\build.xml。

![Eclipse](http://ir.hit.edu.cn/~yjliu/image/2013-7-12-cmake-win-setup.png)

点击Finish就导入了项目。

在导入项目后，右键build.xml选择2 Ant Build。 在弹出的对话框中的选择main选项卡，并在Base Directory:中填入项目路径。 在本例子里，需要填入E:\work\projects\ltp-cloud-sdk\JAVA。

###编译jni
jni动态库依赖于ltp的动态库，所以在编译jni之前必须在本地安装了ltp，[LTP使用文档v3.0](https://github.com/HIT-SCIR/ltp/blob/master/doc/ltp-document-3.0.md)详细介绍了ltp，如果你之前对ltp不太了解，建议通篇阅读一下，如果对ltp比较熟悉，可以直接看“开始使用LTP”部分来安装ltp。

* **注意该处编译需要设置Java环境变量JAVA_HOME**。

在本地安装好了ltp，就可以编译jni了。

####安装CMake
LTP使用编译工具CMake构建项目。在安装LTP之前，你需要首先安装CMake。CMake的网站在[这里](http://www.cmake.org)。如果你是Windows用户，请下载CMake的二进制安装包；如果你是Linux，Mac OS或Cygwin的用户，可以通过编译源码的方式安装CMake，当然，你也可以使用Linux的软件源来安装。

#### Windows(MSVC)编译

第一步：修改CMakeLists.txt
修改项目根目录下的CMakeLists.txt，把其中的set (LTP_HOME "/home/yijialiu/work/ltp/")后面的路径改成你的ltp的安装路
第二步：构建VC Project

在项目文件夹下新建一个名为build的文件夹，使用CMake Gui，在source code中填入项目文件夹，在binaries中填入build文件夹。然后Configure -> Generate。

![win-cmake](http://ir.hit.edu.cn/~yjliu/image/2013-7-12-cmake-win-setup.png)

或者在命令行build 路径下运行

        cmake ..

第二步：编译

构建后得到ALL_BUILD、RUN_TESTS、ZERO_CHECK三个VC Project。使用VS打开ALL_BUILD项目，选择Release(*)方式构建项目。

#### Linux，Mac OSX和Cygwin编译
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

###开始使用

构建使用Java接口ltp4j的工程
* 导入ltp4j.jar
* windows下将libs文件夹中生成的所有动态库，以及原ltp lib文件夹下的splitsnt、segmentor、postagger、ner、parser、srl 6个动态库拷贝到项目根目录
* linux下export LD_LIBRARY_PATH=#jni动态库路径#

接下来便可仿照上面各个接口的例子使用ltp啦。
##编程接口
见[接口说明文档](https://github.com/ruoshui1126/ltp4j/blob/master/doc/ltp4j-document-1.0.md)
* **注意，对于一个包含N个词的句子，句法分析返回的父节点范围在0至N之间，而语义角色标注的输入需要在-1至N-1之间。因此，若要在句法分析后进行语义角色标注，需要把heads作减一操作。**
