package bruce.chang.mylibrary;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator
 * Date:2017/4/9
 * Time:21:23
 * Author:BruceChang
 * Function:
 */

public class CCGToastUtils {

    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, int res) {
        Snackbar.make(view, res, Snackbar.LENGTH_SHORT).show();
    }
}
