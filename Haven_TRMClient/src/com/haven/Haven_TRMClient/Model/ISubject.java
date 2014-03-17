package com.haven.Haven_TRMClient.Model;

import com.haven.Haven_TRMClient.View.IObserver;

/**
 * Created by hieu.t.vo on 3/13/14.
 */
public interface ISubject {
    void registerObserver(IObserver iob);
    void removeObserver(IObserver iob);
    void notifyObservers();
}
