#include <jni.h>
#include <string>
#include <opencv2/core/utility.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
extern "C"
JNIEXPORT jstring

JNICALL
Java_com_example_elab_opencv_1test_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    cv::Mat test(100,100,CV_8UC3);

    std::ostringstream oss;
    oss << "rows = " << test.rows << "cols = " << test.cols << std::endl;
    std::string hello = oss.str();
    return env->NewStringUTF(hello.c_str());
}
