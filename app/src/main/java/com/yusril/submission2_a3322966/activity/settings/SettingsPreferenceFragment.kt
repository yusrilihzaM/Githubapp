package com.yusril.submission2_a3322966.activity.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.yusril.submission2_a3322966.R


class SettingsPreferenceFragment: PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var switchPreferenceCompat: SwitchPreferenceCompat
    private lateinit var ISCHECKED: String
    private lateinit var alarmReceiver: AlarmReceiver
    companion object{
        const val TIME="16:24"
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)// masukan xml preferences

        //localization
        val preferenceLanguage: Preference? = findPreference("key_localization")
        preferenceLanguage?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            false
        }
        switchPreferenceCompat=findPreference<SwitchPreferenceCompat>("key_alarm") as SwitchPreferenceCompat
        alarmReceiver = AlarmReceiver()

    }
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (switchPreferenceCompat.isChecked) {
//            Toast.makeText(activity, getString(R.string.alarm_activated), Toast.LENGTH_SHORT).show()
            activity?.let {
                alarmReceiver.setRepeatingAlarm(
                    it,
                    TIME
                )
            }
        } else {
//            Toast.makeText(activity, getString(R.string.alarm_deactivated), Toast.LENGTH_SHORT).show()
            activity?.let {
                alarmReceiver.cancelAlarm(
                    it
                )
            }
        }
    }

}


