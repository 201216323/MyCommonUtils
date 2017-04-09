package bruce.chang.mylibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator
 * Date:2017/4/9
 * Time:21:23
 * Author:BruceChang
 * Function:
 */

public class ToastUtils {


    private static void showToastShort(Context context, String s) {
        Toast.makeText(context, "This is a test for jcenter", Toast.LENGTH_SHORT).show();
    }

    private static void showToastLong(Context context, String s) {
        Toast.makeText(context, "This is a test for jcenter", Toast.LENGTH_LONG).show();
    }
}
