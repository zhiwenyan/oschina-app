package com.steven.oschina.utils;

import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.steven.oschina.emoji.InputHelper;
import com.steven.oschina.widget.MyLinkMovementMethod;
import com.steven.oschina.widget.MyURLSpan;
import com.steven.oschina.widget.TweetTextView;

/**
 * Description:
 * Data：7/30/2018-2:49 PM
 *
 * @author yanzhiwen
 */
public class CommentsUtil {
    @SuppressWarnings("deprecation")
    public static void formatHtml(Resources resources, TextView textView, String str) {
        if (str == null)
            return;
        str = str.trim();

        textView.setMovementMethod(MyLinkMovementMethod.a());
        textView.setFocusable(false);
        textView.setClickable(false);
        textView.setLongClickable(false);

        if (textView instanceof TweetTextView) {
            ((TweetTextView) textView).setDispatchToParent(true);
        }

        str = TweetTextView.modifyPath(str);
        Spanned span = Html.fromHtml(str);
        span = InputHelper.displayEmoji(resources, span.toString());
        textView.setText(span);
        MyURLSpan.parseLinkText(textView, span);
    }

}
