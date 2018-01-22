#include <jni.h>
#include <string>
#include <opencv2/core/utility.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
//#include "OmnidirectionalCamera.hpp"

int toGray(cv::Mat img, cv::Mat &Gray);
extern "C" {

JNIEXPORT jstring JNICALL Java_com_example_elab_opencv_1test_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    cv::Mat test(100, 100, CV_8UC3);

    std::ostringstream oss;
    oss << "rows = " << test.rows << "cols = " << test.cols << std::endl;
    std::string hello = oss.str();
    return env->NewStringUTF(hello.c_str());
}
JNIEXPORT jint
Java_com_example_elab_opencv_1test_MainActivity_ConvertNative(JNIEnv *, jobject, jlong addrRgba,
                                                              jlong addrGray);


JNIEXPORT jint
Java_com_example_elab_opencv_1test_MainActivity_ConvertNative(JNIEnv *, jobject, jlong addrRgba,
                                                              jlong addrGray) {

    cv::Mat &mRgb = *(cv::Mat *) addrRgba;
    cv::Mat &mGray = *(cv::Mat *) addrGray;

    int con;
    jint ret;

    con = toGray(mRgb, mGray);
    ret = (jint) con;

    return ret;
}
}

int toGray(cv::Mat img, cv::Mat &gray) {
    cv::cvtColor(img, gray, CV_RGB2GRAY);
    if (gray.rows == img.rows && gray.cols == img.cols) {
        return (1);
    }
    return (0);

}

