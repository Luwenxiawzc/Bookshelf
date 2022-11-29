package com.com.jnu.recycleview;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import androidx.appcompat.app.AlertDialog;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.util.Locale;

public class AboutFragment extends PreferenceFragment {
    private static final String TAG = "AboutFragment";

    private Preference namePreference;
    private Preference feedbackPreference;
    private Preference licensePreference;
    private Preference termOfServicePreference;
    private Preference privacyPolicyPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about_preference);

        namePreference = findPreference("about_pref_name");//APP名称
        namePreference.setSummary(BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");

        feedbackPreference = findPreference("about_pref_feedback");//反馈
        feedbackPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + getString(R.string.about_preference_feedback_email_address)));//发送邮箱
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bookshelf Feedback");
                mailIntent.putExtra(Intent.EXTRA_TEXT, getEmailContent());
                if (mailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mailIntent);
                } else {
                    Toast.makeText(getActivity(),
                                    String.format(
                                            getString(R.string.about_preference_no_email_client_toast),//没有设备连接
                                            getString(R.string.about_preference_feedback_email_address)),
                                    Toast.LENGTH_LONG)
                            .show();
                }
                return true;
            }
        });

        licensePreference = findPreference("about_pref_license");//开源许可
        licensePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(getString(R.string.about_preference_license_dialog));

                WebView wv = new WebView(getActivity());
                wv.loadUrl("file:///android_asset/license.html");
                wv.setWebViewClient(new WebViewClient() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    @TargetApi(Build.VERSION_CODES.N)
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        final Uri uri = request.getUrl();
                        view.loadUrl(uri.toString());
                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });

        privacyPolicyPreference = findPreference("about_pref_privacy_policy");//隐私政策
        privacyPolicyPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(getString(R.string.about_preference_privacy_policy));

                WebView wv = new WebView(getActivity());
                wv.loadUrl("file:///android_asset/privacy_policy.html");
                wv.setWebViewClient(new WebViewClient() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    @TargetApi(Build.VERSION_CODES.N)
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        final Uri uri = request.getUrl();
                        view.loadUrl(uri.toString());
                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });

        termOfServicePreference = findPreference("about_pref_term_of_service");//使用条款
        termOfServicePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(getString(R.string.about_preference_term_of_service));

                WebView wv = new WebView(getActivity());
                if (getCurrentLocale().equals(Locale.CHINA)) {
                    wv.loadUrl("file:///android_asset/termOfService_zh.html");
                } else {
                    wv.loadUrl("file:///android_asset/termOfService_en.html");
                }
                wv.setWebViewClient(new WebViewClient() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    @TargetApi(Build.VERSION_CODES.N)
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        final Uri uri = request.getUrl();
                        view.loadUrl(uri.toString());
                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });
    }

    private String getEmailContent() {
        String content = "\n\n" + "------------------------" + "\n";
        content += "Package Name: " + getActivity().getPackageName() + "\n";
        content += "App Version: " + BuildConfig.VERSION_NAME + "\n";
        content += "App Version Code: " + BuildConfig.VERSION_CODE + "\n";
        content += "Device Model: " + Build.MODEL + "\n" + "Device Brand: " + Build.BRAND + "\n" + "SDK Version: " + Build.VERSION.SDK_INT + "\n" + "------------------------";
        return content;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Locale getCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getResources().getConfiguration().getLocales().get(0);
        } else {
            return getResources().getConfiguration().locale;
        }
    }
}
