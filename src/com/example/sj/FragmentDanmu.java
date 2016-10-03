package com.example.sj;

import com.example.followme.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 这是记步的碎�?
 * Author: sj    
 */
public class FragmentDanmu extends Fragment{
	 private WebView webview;
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        return  inflater.inflate(R.layout.danmu, container, false);
	    }

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub

	        setView();
	        setListener();

	        super.onActivityCreated(savedInstanceState);
	    }

	    private void setListener() {
	        // TODO Auto-generated method stub

	        webview.loadUrl("http://172.17.131.37:8080/WebSocketDemo/index.jsp");
           // webview.loadUrl("http://www.youku.com/");
	        WebSettings webSettings = webview.getSettings();
	        webSettings.setJavaScriptEnabled(true);
	        webSettings.setAllowFileAccess(true);
	        //设置支持缩放
	        webSettings.setBuiltInZoomControls(true);
	        //加载需要显示的网页
	        webSettings.setBlockNetworkImage(false);
	        webSettings.setBlockNetworkLoads(false);
	        webSettings.setDomStorageEnabled(true);
	        webview.setWebViewClient(new WebViewClient() {
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
	                return true;
	            }
	        });

	    }

	    private void setView() {
	        // TODO Auto-generated method stub
	        webview=(WebView)getView().findViewById(R.id.webView);

	    }
}
