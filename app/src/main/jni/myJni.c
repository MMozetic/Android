//
// Created by student on 28.5.2019.
//

#include "myJni.h"

JNIEXPORT jint JNICALL Java_pnrs_rtrk_myapplication_MyNDK_convert
  (JNIEnv *env, jobject obj, jdouble x, jint type){
    if(type == 0){
        return x;
    }

    return x*9/5+32;
  }