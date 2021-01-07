package com.launchtool.Sumit1334;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;


@DesignerComponent(version = 1,  description = "This extension is created bt Sumit kumar for generating string with your desired length.<br>" +
                   "Kodular Profile<br><a href='https://community.kodular.io/u/sumit1334' target='_blank'>https://community.kodular.io/u/sumit1334</a><br>",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,   iconName = "https://community.kodular.io/user_avatar/community.kodular.io/sumit1334/120/82654_2.png")
@SimpleObject(external = true)
public class LaunchTool extends AndroidNonvisibleComponent {
    private ComponentContainer container;
    private PackageManager pm;
    private Context context;
    public LaunchTool(ComponentContainer container) {
        super(container.$form());
        this.container = container;
        this.context=container.$context();
    }

    @SimpleFunction(description = "Send massage to whatsapp on individual person")
    public void WhatsappInidvidual(final String countryCode,final int phone,final String massage){
        if (appinstalledchecker("com.whatsapp")){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + countryCode + phone + "&text=" + massage;
                intent.setData(Uri.parse(url));
                intent.setPackage("com.whatsapp");
                context.startActivity(intent);
            }catch (Exception e){
                ErrorOccured(e.toString());
        }
        }else {
            Appnotfound("com.whatsapp");
        }
    }
    @SimpleFunction(description = "Whatsapp group")
    public void WhatsappGroup(final String link){
        if (appinstalledchecker("com.whatsapp")){
            try {
                Intent intentWhatsapp = new Intent("android.intent.action.VIEW");
                intentWhatsapp.setPackage("com.whatsapp");
                intentWhatsapp.setData(Uri.parse(link));
                context.startActivity(intentWhatsapp);
            }catch (Exception e){
                ErrorOccured(e.toString());
            }
        }else {
            Appnotfound("com.whatsapp");
        }
    }

    @SimpleFunction(description = "launches youtube video or channel")
    public void Youtube(final String link){
        if (link!=null){
            try {
                Intent intentyoutube = new Intent("android.intent.action.VIEW");
                intentyoutube.setData(Uri.parse(link));
                context.startActivity(intentyoutube);
            }catch (Exception e){
                ErrorOccured(e.toString());
            }
        }else
            throw new YailRuntimeError("link is empty","empty");
    }
    @SimpleFunction(description = "Whatsapp group")
    public void Telegram(final String link){
        if (appinstalledchecker("org.telegram.messenger")){
            try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setPackage("org.telegram.messenger");
            intent.setData(Uri.parse(link));
            context.startActivity(intent);
            }catch (Exception e){
                ErrorOccured(e.toString());
            }
        }else{
            Appnotfound("org.telegram.messenger");
        }
    }

    @SimpleFunction(description = "Launches the application.")
    public void OpenApp(final String packageName) {
        if(packageName != null){
            try {
                if (appinstalledchecker(packageName)) {
                    PackageManager pm = context.getPackageManager();
                    Intent launchIntent = pm.getLaunchIntentForPackage(packageName);

                    context.startActivity(launchIntent);

                } else
                    Appnotfound(packageName);
            }catch (Exception e){
                ErrorOccured(e.toString());
            }
        }else
            throw new YailRuntimeError("package cannot be empty","package is empty");
    }
    @SimpleFunction(description = "Send Email to a given email")
    public void Email(String to,String subject,String massage){
        if (appinstalledchecker("com.google.android.gm")){
            try {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + to));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, massage);
                context.startActivity(intent);
            }catch (Exception e){
                ErrorOccured(e.toString());
            }
        }else {
            Appnotfound("com.google.android.gm");
        }
    }

    @SimpleFunction(description = "opens facebook page")
    public void FacebookPage(final String pageid){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/"+pageid));
        context.startActivity(intent);
    }

    @SimpleEvent(description = "Raises when an app not found")
    public void Appnotfound(String packagename){
        EventDispatcher.dispatchEvent(this,"Appnotfound",packagename);
    }
    @SimpleEvent(description = "Raises when any error occurs")
    public void ErrorOccured(String error){
        EventDispatcher.dispatchEvent(this,"ErrorOccured",error);
    }

    private boolean appinstalledchecker(String packagename){

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packagename);
        if (intent!=null)
            return true;
        else
            return false;
    }

 }
