/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class quickfix_Message */

#ifndef _Included_quickfix_Message
#define _Included_quickfix_Message
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     quickfix_Message
 * Method:    InitializeXML
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_InitializeXML
  (JNIEnv *, jclass, jstring);

/*
 * Class:     quickfix_Message
 * Method:    create
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_create
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    destroy
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_destroy
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    clone
 * Signature: ()Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_clone
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    setString
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setString
  (JNIEnv *, jobject, jint, jstring);

/*
 * Class:     quickfix_Message
 * Method:    setBoolean
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setBoolean
  (JNIEnv *, jobject, jint, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    setChar
 * Signature: (IC)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setChar
  (JNIEnv *, jobject, jint, jchar);

/*
 * Class:     quickfix_Message
 * Method:    setInt
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setInt
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    setDouble
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setDouble__ID
  (JNIEnv *, jobject, jint, jdouble);

/*
 * Class:     quickfix_Message
 * Method:    setDouble
 * Signature: (IDI)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setDouble__IDI
  (JNIEnv *, jobject, jint, jdouble, jint);

/*
 * Class:     quickfix_Message
 * Method:    setUtcTimeStamp
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setUtcTimeStamp__ILjava_util_Date_2
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    setUtcTimeStamp
 * Signature: (ILjava/util/Date;Z)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setUtcTimeStamp__ILjava_util_Date_2Z
  (JNIEnv *, jobject, jint, jobject, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    setUtcTimeOnly
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setUtcTimeOnly__ILjava_util_Date_2
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    setUtcTimeOnly
 * Signature: (ILjava/util/Date;Z)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setUtcTimeOnly__ILjava_util_Date_2Z
  (JNIEnv *, jobject, jint, jobject, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    setUtcDateOnly
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_setUtcDateOnly
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    getString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_quickfix_Message_getString
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    getBoolean
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_getBoolean
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    getChar
 * Signature: (I)C
 */
JNIEXPORT jchar JNICALL Java_quickfix_Message_getChar
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    getInt
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_quickfix_Message_getInt
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    getDouble
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_quickfix_Message_getDouble
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    getUtcTimeStamp
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_getUtcTimeStamp
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    getUtcTimeOnly
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_getUtcTimeOnly
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    getUtcDateOnly
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_getUtcDateOnly
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    isSetField
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_isSetField
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    removeField
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_removeField
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    addGroup
 * Signature: (Lquickfix/Group;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_addGroup
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    replaceGroup
 * Signature: (ILquickfix/Group;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_replaceGroup
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    getGroup
 * Signature: (ILquickfix/Group;)Lquickfix/Group;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_getGroup
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    removeGroup
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_removeGroup__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    removeGroup
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_removeGroup__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    hasGroup
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_hasGroup__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    hasGroup
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_hasGroup__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    clear
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_clear
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    isEmpty
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_isEmpty
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    toString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_quickfix_Message_toString
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    toXML
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_quickfix_Message_toXML
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    fromString
 * Signature: (Ljava/lang/String;Z)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_fromString__Ljava_lang_String_2Z
  (JNIEnv *, jobject, jstring, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    fromString
 * Signature: (Ljava/lang/String;Lquickfix/DataDictionary;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_fromString__Ljava_lang_String_2Lquickfix_DataDictionary_2
  (JNIEnv *, jobject, jstring, jobject);

/*
 * Class:     quickfix_Message
 * Method:    fromString
 * Signature: (Ljava/lang/String;Lquickfix/DataDictionary;Z)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_fromString__Ljava_lang_String_2Lquickfix_DataDictionary_2Z
  (JNIEnv *, jobject, jstring, jobject, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    isAdmin
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_isAdmin
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    isApp
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_isApp
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    messageIteratorCreate
 * Signature: (Lquickfix/Message/Iterator;)Lquickfix/Message/Iterator;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_messageIteratorCreate
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    messageIteratorHasNext
 * Signature: (Lquickfix/Message/Iterator;)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_messageIteratorHasNext
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    messageIteratorNext
 * Signature: (Lquickfix/Message/Iterator;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_messageIteratorNext
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerSetString
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetString
  (JNIEnv *, jobject, jint, jstring);

/*
 * Class:     quickfix_Message
 * Method:    headerSetBoolean
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetBoolean
  (JNIEnv *, jobject, jint, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    headerSetChar
 * Signature: (IC)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetChar
  (JNIEnv *, jobject, jint, jchar);

/*
 * Class:     quickfix_Message
 * Method:    headerSetInt
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetInt
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerSetDouble
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetDouble__ID
  (JNIEnv *, jobject, jint, jdouble);

/*
 * Class:     quickfix_Message
 * Method:    headerSetDouble
 * Signature: (IDI)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetDouble__IDI
  (JNIEnv *, jobject, jint, jdouble, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerSetUtcTimeStamp
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetUtcTimeStamp__ILjava_util_Date_2
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerSetUtcTimeStamp
 * Signature: (ILjava/util/Date;Z)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetUtcTimeStamp__ILjava_util_Date_2Z
  (JNIEnv *, jobject, jint, jobject, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    headerSetUtcTimeOnly
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetUtcTimeOnly__ILjava_util_Date_2
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerSetUtcTimeOnly
 * Signature: (ILjava/util/Date;Z)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetUtcTimeOnly__ILjava_util_Date_2Z
  (JNIEnv *, jobject, jint, jobject, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    headerSetUtcDateOnly
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerSetUtcDateOnly
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerGetString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_quickfix_Message_headerGetString
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerGetBoolean
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_headerGetBoolean
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerGetChar
 * Signature: (I)C
 */
JNIEXPORT jchar JNICALL Java_quickfix_Message_headerGetChar
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerGetInt
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_quickfix_Message_headerGetInt
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerGetDouble
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_quickfix_Message_headerGetDouble
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerGetUtcTimeStamp
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_headerGetUtcTimeStamp
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerGetUtcTimeOnly
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_headerGetUtcTimeOnly
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerGetUtcDateOnly
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_headerGetUtcDateOnly
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerIsSetField
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_headerIsSetField
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerRemoveField
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerRemoveField
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerAddGroup
 * Signature: (Lquickfix/Group;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerAddGroup
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerReplaceGroup
 * Signature: (ILquickfix/Group;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerReplaceGroup
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerGetGroup
 * Signature: (ILquickfix/Group;)Lquickfix/Group;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_headerGetGroup
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerRemoveGroup
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerRemoveGroup__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerRemoveGroup
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerRemoveGroup__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerHasGroup
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_headerHasGroup__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerHasGroup
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_headerHasGroup__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    headerClear
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_headerClear
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerIsEmpty
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_headerIsEmpty
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerIteratorCreate
 * Signature: (Lquickfix/Message/Header/Iterator;)Lquickfix/Message/Iterator;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_headerIteratorCreate
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerIteratorHasNext
 * Signature: (Lquickfix/Message/Header/Iterator;)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_headerIteratorHasNext
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    headerIteratorNext
 * Signature: (Lquickfix/Message/Header/Iterator;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_headerIteratorNext
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetString
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetString
  (JNIEnv *, jobject, jint, jstring);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetBoolean
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetBoolean
  (JNIEnv *, jobject, jint, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetChar
 * Signature: (IC)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetChar
  (JNIEnv *, jobject, jint, jchar);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetInt
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetInt
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetDouble
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetDouble__ID
  (JNIEnv *, jobject, jint, jdouble);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetDouble
 * Signature: (IDI)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetDouble__IDI
  (JNIEnv *, jobject, jint, jdouble, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetUtcTimeStamp
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetUtcTimeStamp__ILjava_util_Date_2
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetUtcTimeStamp
 * Signature: (ILjava/util/Date;Z)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetUtcTimeStamp__ILjava_util_Date_2Z
  (JNIEnv *, jobject, jint, jobject, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetUtcTimeOnly
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetUtcTimeOnly__ILjava_util_Date_2
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetUtcTimeOnly
 * Signature: (ILjava/util/Date;Z)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetUtcTimeOnly__ILjava_util_Date_2Z
  (JNIEnv *, jobject, jint, jobject, jboolean);

/*
 * Class:     quickfix_Message
 * Method:    trailerSetUtcDateOnly
 * Signature: (ILjava/util/Date;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerSetUtcDateOnly
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_quickfix_Message_trailerGetString
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetBoolean
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_trailerGetBoolean
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetChar
 * Signature: (I)C
 */
JNIEXPORT jchar JNICALL Java_quickfix_Message_trailerGetChar
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetInt
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_quickfix_Message_trailerGetInt
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetDouble
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_quickfix_Message_trailerGetDouble
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetUtcTimeStamp
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_trailerGetUtcTimeStamp
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetUtcTimeOnly
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_trailerGetUtcTimeOnly
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetUtcDateOnly
 * Signature: (I)Ljava/util/Date;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_trailerGetUtcDateOnly
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerIsSetField
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_trailerIsSetField
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerRemoveField
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerRemoveField
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerAddGroup
 * Signature: (Lquickfix/Group;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerAddGroup
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerReplaceGroup
 * Signature: (ILquickfix/Group;)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerReplaceGroup
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerGetGroup
 * Signature: (ILquickfix/Group;)Lquickfix/Group;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_trailerGetGroup
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerRemoveGroup
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerRemoveGroup__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerRemoveGroup
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerRemoveGroup__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerHasGroup
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_trailerHasGroup__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerHasGroup
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_trailerHasGroup__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     quickfix_Message
 * Method:    trailerClear
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_quickfix_Message_trailerClear
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerIsEmpty
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_trailerIsEmpty
  (JNIEnv *, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerIteratorCreate
 * Signature: (Lquickfix/Message/Trailer/Iterator;)Lquickfix/Message/Iterator;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_trailerIteratorCreate
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerIteratorHasNext
 * Signature: (Lquickfix/Message/Trailer/Iterator;)Z
 */
JNIEXPORT jboolean JNICALL Java_quickfix_Message_trailerIteratorHasNext
  (JNIEnv *, jobject, jobject);

/*
 * Class:     quickfix_Message
 * Method:    trailerIteratorNext
 * Signature: (Lquickfix/Message/Trailer/Iterator;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_quickfix_Message_trailerIteratorNext
  (JNIEnv *, jobject, jobject);

#ifdef __cplusplus
}
#endif
#endif
