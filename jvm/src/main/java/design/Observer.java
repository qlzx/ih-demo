package design;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh0
 * @date 2021/11/17
 * @desc
 */
public class Observer {

    interface Listener{
        /**
         * 接收事件
         * @param msg
         */
        void update(Object msg);
    }

    class Publisher{
        List<Listener> listeners = new ArrayList<>();

        public void subscribe(Listener listener){
            listeners.add(listener);
        }

        public void remove(Listener listener){
            listeners.remove(listener);
        }

        public void notifySmg(Object data){
            for (Listener listener : listeners) {
                listener.update(data);
            }
        }
    }

}
