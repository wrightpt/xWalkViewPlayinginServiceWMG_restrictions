package com.example.c.xwalkviewservice1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.xwalk.core.XWalkView;

public class MyService extends Service {
    public MyService() {
    }

  //  @Override
  //  public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
    //    throw new UnsupportedOperationException("Not yet implemented");
   // }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(this);
    }

    public class MyBinder extends Binder {

        private MyService service;
        private XWalkView view;

        public MyBinder(MyService service) {
            this.service = service;
        }

        public MyBinder() {
        }

        public void setService(MyService service) {
            this.service = service;
        }

        public MyService getService() {
            return service;
        }

        public XWalkView getView() {
            return view;
        }

        public void setView(XWalkView view) {
            this.view = view;
        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
}
