package com.lfp.ardf.debug;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lfp.ardf.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 日志记录器 <br><br>
 * <p>PrettyFormatStrategy:</p>
 * <pre>
 *  ┌──────────────────────────
 *  │ Method stack history
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Thread information
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Log message
 *  └──────────────────────────
 * </pre>
 * 获得更好的Log体验推荐配置：File -&gt; Setting -&gt; Console Font<br>
 * 为Logcat新建一个Scheme：《LogScheme》 <br>
 * Font:Fira Code<br>
 * <p>
 * File -&gt; Setting -&gt; Android Logcat<br>
 * 使用配置 《LogScheme》
 * <p>
 * <p>LogcatLogStrategy:</p>
 * 普通日志输出模式
 */
public class LogUtil {
    public static final int LEVEL_VERBOSE = 2;
    public static final int LEVEL_DEBUG = 3;
    public static final int LEVEL_INFO = 4;
    public static final int LEVEL_WARN = 5;
    public static final int LEVEL_ERROR = 6;
    public static final int LEVEL_ASSERT = 7;
    private static final int JSON_INDENT = 2;

    static String TAG = "LogUtil";
    static boolean isDebug = false;
    static final LogUtil logger = new LogUtil();

    static PrettyFormatStrategy mPrettyFormatStrategy;
    static CsvFormatStrategy mCsvFormatStrategy;
    static LogcatLogStrategy mLogcatLogStrategy;

    private LogUtil() {
    }

    /**
     * 初始化日志记录器，默认配置 - Debug包:日志开启 , Release包:日志关闭
     *
     * @param context Context
     */
    public static void init(Context context) {
        try {
            setDebug((context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        } catch (Exception e) {
            setDebug(false);
        }
    }

    /**
     * 设置tag
     *
     * @param tag String
     */
    public void setTag(String tag) {
        TAG = tag;
    }

    /**
     * 开关debug模式
     *
     * @param is 为true时才会显示日志
     */
    public static void setDebug(boolean is) {
        isDebug = is;
    }

    /**
     * @return 是否为debug模式
     */
    public static boolean isDebug() {
        return isDebug;
    }

    /*---------------------------- 打印格式 --------------------------------*/
    /*格式化显示方式*/
    public static LogAdapter getPrettyFormat() {
        if (mPrettyFormatStrategy == null) {
            synchronized (LogAdapter.class) {
                if (mPrettyFormatStrategy == null)
                    mPrettyFormatStrategy = PrettyFormatStrategy.newBuilder().build();
            }
        }
        return mPrettyFormatStrategy;
    }

    /*输出Csv格式日子到本地*/
    public static LogAdapter getCsvFormat() {
        if (mCsvFormatStrategy == null) {
            synchronized (LogAdapter.class) {
                if (mCsvFormatStrategy == null)
                    mCsvFormatStrategy = CsvFormatStrategy.newBuilder().build();
            }
        }
        return mCsvFormatStrategy;
    }

    /*普通日志模式*/
    public static LogAdapter getLogcatLog() {
        if (mLogcatLogStrategy == null) {
            synchronized (LogAdapter.class) {
                if (mLogcatLogStrategy == null)
                    mLogcatLogStrategy = new LogcatLogStrategy();
            }
        }
        return mLogcatLogStrategy;
    }

    /*---------------------------- 输出Csv格式到本地--------------------------------*/
    public static void csv_e(@Nullable Object object) {
        Log(getCsvFormat(), LEVEL_ERROR, null, LogFormat(object));
    }


    /*---------------------------- 打印输出 LogcatLog--------------------------------*/
    public static void d(@NonNull String message, @Nullable Object... args) {
        Log(getLogcatLog(), LEVEL_DEBUG, null, message, args);
    }

    public static void d(@Nullable Object object) {
        Log(getLogcatLog(), LEVEL_DEBUG, null, LogFormat(object));
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        e(null, message, args);
    }

    public static void e(@Nullable Object object) {
        Log(getLogcatLog(), LEVEL_ERROR, null, LogFormat(object));
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        Log(getLogcatLog(), LEVEL_ERROR, throwable, message, args);
    }

    public static void e(@Nullable Throwable throwable) {
        Log(getLogcatLog(), LEVEL_ERROR, throwable, throwable == null ? "print a empty log ！！！" : throwable.getMessage());
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        Log(getLogcatLog(), LEVEL_WARN, null, message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        Log(getLogcatLog(), LEVEL_INFO, null, message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        Log(getLogcatLog(), LEVEL_VERBOSE, null, message, args);
    }

    public static void wtf(@NonNull String message, @Nullable Object... args) {
        Log(getLogcatLog(), LEVEL_ASSERT, null, message, args);
    }

    /*---------------------------- 打印输出 PrettyFormat--------------------------------*/
    public static void d_Pretty(@NonNull String message, @Nullable Object... args) {
        Log(getPrettyFormat(), LEVEL_DEBUG, null, message, args);
    }

    public static void d_Pretty(@Nullable Object object) {
        Log(getPrettyFormat(), LEVEL_DEBUG, null, LogFormat(object));
    }

    public static void e_Pretty(@NonNull String message, @Nullable Object... args) {
        e_Pretty(null, message, args);
    }

    public static void e_Pretty(@Nullable Object object) {
        Log(getPrettyFormat(), LEVEL_ERROR, null, LogFormat(object));
    }

    public static void e_Pretty(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        Log(getPrettyFormat(), LEVEL_ERROR, throwable, message, args);
    }

    public static void e_Pretty(@Nullable Throwable throwable) {
        Log(getPrettyFormat(), LEVEL_ERROR, throwable, throwable == null ? "print a empty log ！！！" : throwable.getMessage());
    }

    public static void w_Pretty(@NonNull String message, @Nullable Object... args) {
        Log(getPrettyFormat(), LEVEL_WARN, null, message, args);
    }

    public static void i_Pretty(@NonNull String message, @Nullable Object... args) {
        Log(getPrettyFormat(), LEVEL_INFO, null, message, args);
    }

    public static void v_Pretty(@NonNull String message, @Nullable Object... args) {
        Log(getPrettyFormat(), LEVEL_VERBOSE, null, message, args);
    }

    public static void wtf_Pretty(@NonNull String message, @Nullable Object... args) {
        Log(getPrettyFormat(), LEVEL_ASSERT, null, message, args);
    }

    public static void json(@Nullable String json) {
        d_Pretty(jsonFormat(json));
    }

    public static void xml(@Nullable String xml) {
        d_Pretty(xmlFormat(xml));
    }


    private static synchronized void Log(LogAdapter logAdapter, int level, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        if (!isDebug()) return;

        if (throwable != null) {
            if (message == null) message = GetStackTraceString(throwable);
            else message += " : " + GetStackTraceString(throwable);
        }
        if (Utils.isEmpty(message)) message = "Empty/NULL log message";
        logAdapter.log(level, tag, message);
    }

    private static synchronized void Log(LogAdapter logAdapter, int level, @Nullable Throwable throwable, @NonNull String msg, @Nullable Object... args) {
        if (Utils.isEmpty(msg)) {
            Log(logAdapter, LEVEL_ERROR, TAG, "print a empty log ！！！", throwable);
        } else Log(logAdapter, level, TAG, CreateMessage(msg, args), throwable);
    }

    /*------------- 辅助方法 --------------*/

    /**
     * 格式化 JSON 为日志显示格式
     *
     * @param json 需要格式化的 JSON 数据
     * @return 格式化之后的数据
     */
    public static String jsonFormat(String json) {
        if (Utils.isEmpty(json)) return "Empty/Null json content";
        try {
            json = json.trim();
            if (json.startsWith("{")) return new JSONObject(json).toString(JSON_INDENT);
            if (json.startsWith("[")) return new JSONArray(json).toString(JSON_INDENT);
        } catch (JSONException e) {
            return "Invalid Json:" + e.getMessage();
        }
        return "Invalid Json";
    }

    /**
     * 格式化 Xml 为日志显示格式
     *
     * @param xml 需要格式化的 Xml 数据
     * @return 格式化之后的数据
     */
    public static String xmlFormat(String xml) {
        if (Utils.isEmpty(xml)) return "Empty/Null xml content";
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            return "Invalid xml :" + e.getException();
        }
    }

    @NonNull
    private static String CreateMessage(@NonNull String message, @Nullable Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

    @NonNull
    private static String LogFormat(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return object.toString();
    }

    @NonNull
    private static String GetStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    static String logLevel(int value) {
        switch (value) {
            case LEVEL_VERBOSE:
                return "VERBOSE";
            case LEVEL_DEBUG:
                return "DEBUG";
            case LEVEL_INFO:
                return "INFO";
            case LEVEL_WARN:
                return "WARN";
            case LEVEL_ERROR:
                return "ERROR";
            case LEVEL_ASSERT:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }
}
