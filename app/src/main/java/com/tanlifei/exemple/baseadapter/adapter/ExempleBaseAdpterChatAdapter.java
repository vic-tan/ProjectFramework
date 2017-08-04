package com.tanlifei.exemple.baseadapter.adapter;

import android.content.Context;

import com.tanlifei.exemple.baseadapter.bean.ExempleBaseAdpterChatMessage;
import com.tlf.basic.base.adapter.abslistview.AbsMultiItemTypeAdapter;

import java.util.List;


/**
 * Created by tanlifei on 15/9/4.
 */
public class ExempleBaseAdpterChatAdapter extends AbsMultiItemTypeAdapter<ExempleBaseAdpterChatMessage> {
    public ExempleBaseAdpterChatAdapter(Context context, List<ExempleBaseAdpterChatMessage> datas) {
        super(context, datas);
        addItemViewDelegate(new ExempleMsgSendItemDelagate());
        addItemViewDelegate(new ExempleMsgComingItemDelagate());
    }
}
