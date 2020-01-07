package com.bupt.aiya.blockchain.socket.distruptor;

import com.bupt.aiya.blockchain.ApplicationContextProvider;
import com.bupt.aiya.blockchain.socket.distruptor.base.BaseEvent;
import com.lmax.disruptor.EventHandler;

/**
 * @author wuweifeng wrote on 2018/4/20.
 */
public class DisruptorClientHandler implements EventHandler<BaseEvent> {

    @Override
    public void onEvent(BaseEvent baseEvent, long sequence, boolean endOfBatch) throws Exception {
        ApplicationContextProvider.getBean(DisruptorClientConsumer.class).receive(baseEvent);
    }
}
