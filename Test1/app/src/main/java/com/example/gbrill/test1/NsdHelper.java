package com.example.gbrill.test1;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.DiscoveryListener;
import android.net.nsd.NsdManager.ResolveListener;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

//new FindServicesNSD((NsdManager)getSystemService(NSD_SERVICE), "_ipp._tcp")
final class NsdHelper
        implements
        DiscoveryListener,
        ResolveListener
{
    // DiscoveryListener

    @Override
    public void onDiscoveryStarted(String theServiceType)
    {
        Log.d(TAG, "onDiscoveryStarted");
    }

    @Override
    public void onStartDiscoveryFailed(String theServiceType, int theErrorCode)
    {
        Log.d(TAG, "onStartDiscoveryFailed(" + theServiceType + ", " + theErrorCode);
    }

    @Override
    public void onDiscoveryStopped(String serviceType)
    {
        Log.d(TAG, "onDiscoveryStopped");
    }

    @Override
    public void onStopDiscoveryFailed(String theServiceType, int theErrorCode)
    {
        Log.d(TAG, "onStartDiscoveryFailed(" + theServiceType + ", " + theErrorCode);
    }

    @Override
    public void onServiceFound(NsdServiceInfo theServiceInfo)
    {
        Log.d(TAG, "onServiceFound(" + theServiceInfo + ")");
        Log.d(TAG, "name == " + theServiceInfo.getServiceName());
        Log.d(TAG, "type == " + theServiceInfo.getServiceType());
        serviceFound(theServiceInfo);
    }

    @Override
    public void onServiceLost(NsdServiceInfo theServiceInfo)
    {
        Log.d(TAG, "onServiceLost(" + theServiceInfo + ")");
    }

    // Resolve Listener

    @Override
    public void onServiceResolved(NsdServiceInfo theServiceInfo)
    {
        Log.d(TAG, "onServiceResolved(" + theServiceInfo + ")");
        Log.d(TAG, "name == " + theServiceInfo.getServiceName());
        Log.d(TAG, "type == " + theServiceInfo.getServiceType());
        Log.d(TAG, "host == " + theServiceInfo.getHost());
        Log.d(TAG, "port == " + theServiceInfo.getPort());
    }

    @Override
    public void onResolveFailed(NsdServiceInfo theServiceInfo, int theErrorCode)
    {
        Log.d(TAG, "onResolveFailed(" + theServiceInfo + ", " + theErrorCode);
    }

    //

    NsdHelper(NsdManager theManager, String theServiceType)
    {
        manager     = theManager;
        serviceType = theServiceType;
    }

    void run()
    {
        manager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, this);
    }

    private void serviceFound(NsdServiceInfo theServiceInfo)
    {
        manager.resolveService(theServiceInfo, this);
    }

    //

    private NsdManager   manager;
    private String       serviceType;

    //

    private static final String TAG = "AndroidTalker";
}