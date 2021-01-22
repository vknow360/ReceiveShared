package com.sunny.ReceiveShared;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.annotations.androidmanifest.*;
import com.google.appinventor.components.annotations.UsesActivities;
import com.google.appinventor.components.annotations.UsesActivities;
@DesignerComponent(version = 1,
	description ="Receive shared files<br> Developed by Sunny Gupta",
	category = ComponentCategory.EXTENSION,
	nonVisible = true,
	iconName = "https://res.cloudinary.com/andromedaviewflyvipul/image/upload/c_scale,h_20,w_20/v1571472765/ktvu4bapylsvnykoyhdm.png")
@UsesActivities(activities = {
	@ActivityElement(intentFilters = {
		@IntentFilterElement(actionElements = {
			@ActionElement(name = "android.intent.action.SEND")},
						   categoryElements = {@CategoryElement(name = "android.intent.category.DEFAULT")},
						   dataElements = {@DataElement(mimeType = "text/*"),
						   @DataElement(mimeType = "image/*"),
						   @DataElement(mimeType = "audio/*"),
						   @DataElement(mimeType = "video/*")})},
		name="com.sunny.ReceiveShared.ReceiveShared$ReceiveActivty")})
@SimpleObject(external=true)
public final class ReceiveShared extends AndroidNonvisibleComponent {
	
	public ReceiveShared(ComponentContainer container) {
		super(container.$form());
    }
	
	public static class ReceiveActivty extends Activity {
		@Override
		protected void onCreate(Bundle saved) {
			super.onCreate(saved);
			String value = "";
        String type = "";
        Intent intent = getIntent();
        if (intent != null && intent.getAction().equals(Intent.ACTION_SEND)){
            type = intent.getType();
            if (intent.getType().startsWith("text/")){
                String receivedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (receivedText != null){
                    value = receivedText;
                }
            }else{
                Uri receivedUri = (Uri)intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (receivedUri != null){
                    value = receivedUri.getPath();
                }
            }
            List<String> startValue = new ArrayList<>();
            startValue.add(value);
            startValue.add(type);
            PackageManager packageManager = getPackageManager();
            Intent lIntent = packageManager.getLaunchIntentForPackage(getPackageName());
            lIntent.putExtra("APP_INVENTOR_START",JsonUtil.getJsonRepresentation(startValue));
            startActivity(lIntent);
            finish();
        }
		}
	}
}
