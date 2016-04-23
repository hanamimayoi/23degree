package com.quintet.littleweather.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.design.widget.Snackbar;
import android.text.format.Formatter;

import com.quintet.littleweather.R;
import com.quintet.littleweather.base.BaseApplication;
import com.quintet.littleweather.config.Setting;
import com.quintet.littleweather.utils.ACache;
import com.quintet.littleweather.utils.Utils;

import java.io.File;

/**
 * Created by hanami on 2016/4/22.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {

    private ListPreference mCacheTime;
    private ListPreference mChangeIcons;
    private SwitchPreference mAutoLocation;
    private Preference mClearCache;

    private String[] cacheTimeArrays;
    private String[] iconsArrays;

    private Setting mSetting;
    private ACache mACache;
    private File mCacheFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_setting);

        mSetting = Setting.getInstance();
        mACache = ACache.get(getActivity());
        mCacheFile = new File(BaseApplication.cacheDir, "Data");

        /*
        设置默认的SharedPreference文件名为setting，而不是默认的文件名
         */
        getPreferenceManager().setSharedPreferencesName("setting");

        mCacheTime = (ListPreference) findPreference(Setting.CACHE_TIME);
        mChangeIcons = (ListPreference) findPreference(Setting.CHANGE_ICONS);
        mAutoLocation = (SwitchPreference) findPreference(Setting.AUTO_LOCATION);
        mClearCache = findPreference(Setting.CLEAR_CACHE);

        cacheTimeArrays = getResources().getStringArray(R.array.cache_time);
        iconsArrays = getResources().getStringArray(R.array.change_icons);

        mCacheTime.setOnPreferenceChangeListener(this);
        mChangeIcons.setOnPreferenceChangeListener(this);
        mClearCache.setOnPreferenceClickListener(this);

        /**
         * 这里有一个小BUG，刚进入设置界面，点击选项弹出的dialog窗口，没有显示选中我们保存的那个值
         * 而需要在改变一次值之后，再点击选项弹出dialog窗口，就会显示选中改变的那个值，为了更好的体验
         * 在刚进来的时候也给ListPreference设置一个值，使得第一次弹出的dialog窗口，也能显示选中我们保存的那个值
         */
        mCacheTime.setValue(mSetting.getString(Setting.CACHE_TIME, "0"));
        mChangeIcons.setValue(mSetting.getString(Setting.CHANGE_ICONS, "0"));

        /**
         * 设置刚进设置界面时的设置详情
         */
        mCacheTime.setSummary(cacheTimeArrays[Integer.parseInt(mSetting.getString(Setting.CACHE_TIME, "0"))]);
        mChangeIcons.setSummary(iconsArrays[Integer.parseInt(mSetting.getString(Setting.CHANGE_ICONS, "0"))]);

        /**
         * 设置清除缓存的summary
         */
        long length = Utils.getSize(mCacheFile);
        String s = Formatter.formatFileSize(getActivity(), length);
        mClearCache.setSummary(s);

    }

    /**
     * 设置改变Preference的时候，改变preference的summary
     *
     * @param preference
     * @param newValue
     * @return
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mCacheTime) {
            mCacheTime.setSummary(cacheTimeArrays[Integer.parseInt(newValue.toString())]);
        } else if (preference == mChangeIcons) {
            mChangeIcons.setSummary(iconsArrays[Integer.parseInt(newValue.toString())]);
        }
        return true;//返回true的话，会将新的值保存到sharedPreference中
    }

    /**
     * 设置清楚缓存的监听
     *
     * @param preference
     * @return
     */
    @Override
    public boolean onPreferenceClick(Preference preference) {

        if (preference == mClearCache) {
            mACache.clear();
            String s = Formatter.formatFileSize(getActivity(), Utils.getSize(mCacheFile));
            mClearCache.setSummary(s);

            //getView()返回的应该是这个fragment的view
            Snackbar.make(getView(), "缓存已经清除", Snackbar.LENGTH_LONG).show();
        }

        return false;//返回true的话不会调用onPreferenceTreeClick，返回false的话还会调用onPreferenceTreeClick
    }
}
