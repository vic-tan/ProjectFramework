package com.tanlifei.exemple.baseadapter.adapter;

import com.tanlifei.exemple.baseadapter.bean.ExempleBaseAdpterChatMessage;
import com.tanlifei.framework.R;
import com.tlf.basic.base.adapter.ItemViewDelegate;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.base.adapter.recycler.RvViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class ExempleMsgComingItemDelagate implements ItemViewDelegate<ExempleBaseAdpterChatMessage>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.exemple_base_adapter_item_chat_from_msg;
    }

    @Override
    public boolean isForViewType(ExempleBaseAdpterChatMessage item, int position)
    {
        return item.isComMeg();
    }

    @Override
    public void convert(RvViewHolder holder, ExempleBaseAdpterChatMessage exempleBaseAdpterChatMessage, int position) {

    }

    @Override
    public void convert(AbsViewHolder holder, ExempleBaseAdpterChatMessage chatMessage, int position) {
        holder.setText(R.id.chat_from_content, chatMessage.getContent());
        holder.setText(R.id.chat_from_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_from_icon, chatMessage.getIcon());
    }


}
