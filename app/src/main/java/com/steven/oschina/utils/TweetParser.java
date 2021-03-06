package com.steven.oschina.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.steven.oschina.emoji.InputHelper;

import java.util.regex.Matcher;

/**
 * Description:
 * Data：7/27/2018-2:58 PM
 *
 * @author yanzhiwen
 */
public class TweetParser extends RichTextParser {
    private static TweetParser mInstance = new TweetParser();

    public static TweetParser getInstance() {
        return mInstance;
    }

    @Override
    public Spannable parse(Context context, String content) {
        if (TextUtils.isEmpty(content))
            return null;
        //"content": "6 <a href='https://www.oschina.net/p/oim' class='project' target='_blank' title='基于 JavaFX 开发的聊天客户端OIM'>#OIM#</a>",
        //"Rust社区与文档中文化 <a href='http://ruster.xyz/' rel='nofollow' target='_blank'>http://ruster.xyz/</a>",
        content = HTMLUtil.rollbackReplaceTag(content);
        Spannable spannable = parseOnlyAtUser(context, content);
        spannable = parseOnlyGist(context, spannable);
        spannable = parseOnlyGit(context, spannable);
        spannable = parseOnlyTag(context, spannable);
        spannable = parseOnlyLink(context, spannable);
        spannable = InputHelper.displayEmoji(context.getResources(), spannable);
        return spannable;
    }

    /**
     * 清空HTML标签
     */
    public Spannable clearHtmlTag(CharSequence content) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        Matcher matcher;
        while (true) {
            matcher = PatternHtml.matcher(builder.toString());
            if (matcher.find()) {
                String str = matcher.group(1);
                builder.replace(matcher.start(), matcher.end(), str);
                continue;
            }
            break;
        }
        return builder;
    }
}