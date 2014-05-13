cmake_minimum_required (VERSION 2.8.0)
project ("ltp4j")

include_directories (./ltp/include
./ltp/src/_split_sentence
$ENV{JAVA_HOME}/include)

if(WIN32)
include_directories ($ENV{JAVA_HOME}/include/win32)
else(WIN32)
include_directories ($ENV{JAVA_HOME}/include/linux)
endif(WIN32)

link_directories (./libs)

set (LIBRARY_OUTPUT_PATH    ${PROJECT_SOURCE_DIR}/libs)


add_library (split_sentence_jni SHARED split_sentence_jni.cpp )
target_link_libraries (split_sentence_jni splitsnt)

add_library (segmentor_jni SHARED segment_jni.cpp )
target_link_libraries (segmentor_jni segmentor)

add_library(postagger_jni SHARED postag_jni.cpp)
target_link_libraries (postagger_jni postagger)

add_library (ner_jni SHARED ner_jni.cpp)
target_link_libraries (ner_jni ner)

add_library (parser_jni SHARED parser_jni.cpp)
target_link_libraries (parser_jni parser)

add_library (srl_jni SHARED srl_jni.cpp)
target_link_libraries (srl_jni srl)
# -----------------------------------------------
# TOOLKIT
