package com.yaya.baby.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yaya.baby.R;
import com.yaya.baby.ui.activity.ManualFetalActivity;
import com.yaya.baby.ui.activity.SearchBabyHeartActivity;
import com.yaya.baby.ui.common.BaseFragment;
import com.yaya.baby.ui.common.config.Const;
import com.yaya.baby.utils.PreferencesUtils;
import com.yaya.baby.utils.ToastUtils;

import butterknife.OnClick;

import static com.inuker.bluetooth.library.Constants.STATUS_DEVICE_CONNECTED;
import static com.yaya.baby.BabyApplication.getBluetoothClient;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 数胎动 fragment
 */
public class FetalFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fetal;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @OnClick({R.id.rl_fetal_automatic, R.id.rl_fetal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_fetal_automatic:
                checkConnection();
                break;

            case R.id.rl_fetal:
                Intent intent = new Intent(getActivity(), ManualFetalActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    //判断当前是否连接上设备
    private void checkConnection() {
        String mac = PreferencesUtils.getString(getContext(), Const.MAC_ADDRESS);
        if (TextUtils.isEmpty(mac))
            ToastUtils.showToast(getContext(), "你还没有配对设备");
        else {
            // Constants.STATUS_UNKNOWN
            // Constants.STATUS_DEVICE_CONNECTED
            // Constants.STATUS_DEVICE_CONNECTING
            // Constants.STATUS_DEVICE_DISCONNECTING
            // Constants.STATUS_DEVICE_DISCONNECTED
            int status = getBluetoothClient().getConnectStatus(mac);
            if (status == STATUS_DEVICE_CONNECTED)
                skipPage(SearchBabyHeartActivity.class);
            else
                ToastUtils.showToast(context, "设备尚未连接");

        }
    }

}
