package com.shoukeplus.jFinal.plugin.message;

import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import com.shoukeplus.jFinal.common.utils.ClassScaner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessagePlugin implements IPlugin {
	private static final Log log=Log.getLog(MessagePlugin.class);
	private final ExecutorService threadPool;
	private final Map<String,List<MessageListener>> listenerMap;
	private final Map<String,List<MessageListener>> syncListenerMap;

	public MessagePlugin(){
		threadPool= Executors.newFixedThreadPool(5);
		listenerMap=new ConcurrentHashMap<>();
		syncListenerMap=new ConcurrentHashMap<>();
	}

	public void registerListener(Class<? extends MessageListener> listenerClass){
		if(listenerClass==null)
			return;
		if(listenerHasRegisterBefore(listenerClass))
			return;

		MessageListener listener=null;
		try {
			listener=listenerClass.newInstance();
		} catch (Throwable e) {
			e.printStackTrace();
			log.error(String.format("listener \"%s\" newInstance is error. ", listenerClass), e);
			return;
		}
		MessageAction actions=new MessageAction();
		listener.onRegisterAction(actions);
		addListenerMap(listener, actions.getActions(),listenerMap);
		addListenerMap(listener, actions.getSyncActions(),syncListenerMap);

	}

	private void addListenerMap(MessageListener listener, List<String> actions,Map<String,List<MessageListener>> listenerMap) {
		for (String action : actions) {
			List<MessageListener> list = listenerMap.get(action);
			if (null == list) {
				list = new ArrayList<MessageListener>();
			}
			if (!list.contains(listener)) {
				list.add(listener);
			}
			Collections.sort(list, new Comparator<MessageListener>() {
				@Override
				public int compare(MessageListener o1, MessageListener o2) {
					return o2.onGetWeight() - o1.onGetWeight();
				}
			});
			listenerMap.put(action, list);
		}
	}

	private boolean listenerHasRegisterBefore(Class<? extends MessageListener> listenerClass){
		listenerHasRegisterBefore(listenerMap,listenerClass);
		listenerHasRegisterBefore(syncListenerMap,listenerClass);
		return false;
	}
	private boolean listenerHasRegisterBefore(Map<String,List<MessageListener>> map,Class<? extends MessageListener> listenerClass){
		for(Map.Entry<String,List<MessageListener>> entry:map.entrySet()){
			List<MessageListener> listeners=entry.getValue();
			if(listeners==null||listeners.isEmpty()){
				continue;
			}
			for(MessageListener ml:listeners){
				if(listenerClass==ml.getClass()){
					return true;
				}
			}
		}
		return false;
	}

	public void publish(final Message message){
		String key = message.getAction();
		List<MessageListener> syncListeners = syncListenerMap.get(key);
		if (syncListeners != null && !syncListeners.isEmpty()) {
			invokeListenersInSync(message, syncListeners);
		}

		List<MessageListener> listeners = listenerMap.get(key);
		if (listeners != null && !listeners.isEmpty()) {
			invokeListeners(message, listeners);
		}
	}
	private void invokeListenersInSync(final Message message, List<MessageListener> syncListeners) {
		for (final MessageListener listener : syncListeners) {
			try {
				listener.onMessage(message);
			} catch (Throwable e) {
				log.error(String.format("listener[%s] onMessage is erro! ", listener.getClass()), e);
			}
		}
	}

	private void invokeListeners(final Message message, List<MessageListener> listeners) {
		for (final MessageListener listener : listeners) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						listener.onMessage(message);
					} catch (Throwable e) {
						log.error(String.format("listener[%s] onMessage is erro! ", listener.getClass()), e);
					}
				}
			});
		}
	}
	private void autoRegister() {
		List<Class<MessageListener>> list = ClassScaner.scanSubClass(MessageListener.class, true);
		if (list != null && list.size() > 0) {
			for (Class<MessageListener> clazz : list) {
				registerListener(clazz);
			}
		}
	}

	@Override
	public boolean start() {
		MessageKit.init(this);
		autoRegister();
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}
}
