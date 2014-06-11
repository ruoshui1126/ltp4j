LTP本地程序说明文档
===============
### 作者
* 韩冰 << bhan@ir.hit.edu.cn >> 2014-05-15 创建文档


#split_snt
功能：
分句。
调用示例：

    ./split_snt < input_file
    
#cws_cmdline
功能：
分词，输出格式“我   是  中国    人”。
调用示例：

    ./cws_cmdline <input_file
    
#pos_cmdline
功能：
在分词的基础上进行词性标注，输出格式“我_r|是_v|中国_ns|人_n”。
调用示例：

    ./pos_cmdline <input_file

#cws_pos_cmdline
功能：
分词、词性标注，输出格式“我_r|是_v|中国_ns|人_n”。
调用示例：

    ./pos_cmdline <input_file
    
#ltp_test
功能：
接受输入文件，根据命令执行相应的任务，以xml形式返回结果。
调用示例：

    ./ltp_test ws input_file //分词
    ./ltp_test pos input_file //词性标注
    ./ltp_test par input_file //句法分析
    ./ltp_test ner input_fil //命名实体识别
    ./ltp_test srl input_file //语义角色标注
    
#muti_ltp_test
功能：
接受输入文件，根据命令执行相应的任务，以xml形式返回结果。多线程执行，示例会创建10个线程。
调用示例：

    ./ltp_test ws input_file 10
