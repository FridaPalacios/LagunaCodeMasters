package mx.itlalaguna.c19130959.appbloqueoint3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String packageName = "com.supercell.brawlstars";
    private DevicePolicyManager mDevicePolicyManager;
    private PackageManager packageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        setContentView(R.layout.activity_main);
        packageManager = getPackageManager();
    }

    public void btnActivarClick(View v) {

        ComponentName mComponentName = new ComponentName(this, MyDeviceAdminReceiver.class);
        if (mDevicePolicyManager.isAdminActive(mComponentName)) {
            // Bloquea la aplicación
            try {
                mDevicePolicyManager.setApplicationHidden(mComponentName, packageName, true);
            }catch (Exception ex){
                String msg = ex.getMessage();
            }

        } else {
            // Si no tiene permisos de administrador de dispositivos, solicita activarlos
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Necesita activar el administrador de dispositivos para bloquear aplicaciones.");
            startActivityForResult(intent, 1);
        }
            /*
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Activar la administración de dispositivos para esta aplicación.");
        startActivityForResult(intent, 1);

        */

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

            } else {
                // El usuario no activó la administración de dispositivos.
            }
        }
    }
}