cmake_minimum_required (VERSION 2.8.0)
project ("ltp4j")

set (LTP_HOME               "/users1/bhan/ggwp/ltp4j/ltp/")
set (LIBRARY_OUTPUT_PATH    ${PROJECT_SOURCE_DIR}/libs)
set (JNI_SOURCE_DIR         ${PROJECT_SOURCE_DIR}/jni)

include_directories (
    ${JNI_SOURCE_DIR}
    ${LTP_HOME}/include
    $ENV{JAVA_HOME}/include)

if(WIN32)
    include_directories ($ENV{JAVA_HOME}/include/win32)
else(WIN32)
    include_directories ($ENV{JAVA_HOME}/include/linux)
endif(WIN32)

link_directories (${LTP_HOME}/lib)

add_library (split_sentence_jni SHARED
${JNI_SOURCE_DIR}/split_sentence_jni.cpp)
target_link_libraries (split_sentence_jni splitsnt)

add_library (segmentor_jni SHARED ${JNI_SOURCE_DIR}/segment_jni.cpp)
target_link_libraries (segmentor_jni segmentor)

add_library(postagger_jni SHARED ${JNI_SOURCE_DIR}/postag_jni.cpp)
target_link_libraries (postagger_jni postagger)

add_library (ner_jni SHARED ${JNI_SOURCE_DIR}/ner_jni.cpp)
target_link_libraries (ner_jni ner)

add_library (parser_jni SHARED ${JNI_SOURCE_DIR}/parser_jni.cpp)
target_link_libraries (parser_jni parser)

add_library (srl_jni SHARED ${JNI_SOURCE_DIR}/srl_jni.cpp)
target_link_libraries (srl_jni srl)

# -----------------------------------------------
# TOOLKIT
