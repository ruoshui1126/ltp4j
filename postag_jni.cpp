#include "edu_hit_ir_ltp4j_Postag.h"
#include <vector>
#include <string>
#include "postag_dll.h"
#include <iostream>
#include "string_to_jstring.hpp"

static void * postagger = NULL;

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Postag_create
(JNIEnv * env, jclass obj, jstring model_path){
  const char * str = env->GetStringUTFChars( model_path , 0);
  if(!postagger){
    postagger = postagger_create_postagger(str);
  }
  else {
    postagger_release_postagger(postagger);
    postagger = postagger_create_postagger(str);
  }
  env->ReleaseStringUTFChars( model_path, str); 
  if(postagger) {
    return 1;
  }
  return -1;
}

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Postag_postag
(JNIEnv * env, jclass, jobject array_words, jobject array_postags){
  jclass array_list = env->GetObjectClass(array_words);

  jmethodID list_add = env->GetMethodID(array_list,"add","(Ljava/lang/Object;)Z");
  jmethodID list_get = env->GetMethodID(array_list,"get","(I)Ljava/lang/Object;");
  jmethodID list_size = env->GetMethodID(array_list,"size","()I");

  std::vector<std::string> words,postags;

  int size = env->CallIntMethod(array_words,list_size);

  for(int i = 0;i<size;i++){
    jobject tmp = env->CallObjectMethod(array_words,list_get,i);
    jstring s = reinterpret_cast<jstring> (tmp);
    const char * st = env->GetStringUTFChars(s,0);
    std::string s_s(st);
    words.push_back(s_s);
    env->ReleaseStringUTFChars( s, st); 
  }

  int len = postagger_postag(postagger,words,postags);

  for(int i = 0;i<len;i++){
    jobject tmp = stringToJstring(env,postags[i].c_str());
    env->CallBooleanMethod(array_postags,list_add,tmp);
  }

  return len;
}

JNIEXPORT void JNICALL Java_edu_hit_ir_ltp4j_Postag_release
(JNIEnv * env, jclass obj){
	postagger_release_postagger(postagger);
	postagger = NULL;
}



