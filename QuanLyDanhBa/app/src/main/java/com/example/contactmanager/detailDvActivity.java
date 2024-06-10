package com.example.contactmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contactmanager.database.DatabaseHelper;

public class detailDvActivity extends AppCompatActivity {
    ImageView imgLogo;
    TextView tvName, tvPhone, tvEmail, tvWebsite, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_dv);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgLogo = findViewById(R.id.imgAvt);
        tvName = findViewById(R.id.txtName);
        tvPhone = findViewById(R.id.txtPhone);
        tvEmail = findViewById(R.id.txtEmail);
        tvWebsite = findViewById(R.id.txtWebsite);
        tvAddress = findViewById(R.id.txtLocation);

        int donViId = getIntent().getIntExtra("donViId", -1); // Get the passed DonVi ID
        if (donViId != -1) {
            loadDonViDetails(donViId);
        } else {
            // Handle invalid DonVi ID (optional)
        }
    }
    private void loadDonViDetails(int donViId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_NAME_DON_VI,
                DatabaseHelper.COLUMN_EMAIL_DON_VI,
                DatabaseHelper.COLUMN_WEBSITE_DON_VI,
                DatabaseHelper.COLUMN_LOGO_DON_VI,
                DatabaseHelper.COLUMN_DIA_CHI_DON_VI,
                DatabaseHelper.COLUMN_SO_DIEN_THOAI_DON_VI
        };
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(donViId)}; // Assuming id is of type int
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_DON_VI,
                projection,
                selection,
                selectionArgs,
                null, null, null
        );

        if (cursor.moveToFirst()) {
            // Update this part
            String name_donvi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_DON_VI));
            String email_donvi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_DON_VI));
            String website_donvi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEBSITE_DON_VI));
            byte[] logoBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOGO_DON_VI));
            String diachi_donvi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIA_CHI_DON_VI));
            String sodienthoai_donvi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_DIEN_THOAI_DON_VI));

            if (logoBytes != null && logoBytes.length > 0) { // Check if logoBytes is not null and has data
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2; // You can adjust this value
                Bitmap logo = BitmapFactory.decodeByteArray(logoBytes, 0, logoBytes.length, options);
                if (logo != null) {
                    imgLogo.setImageBitmap(logo);
                } else {
                    Log.e("detailDvActivity", "Failed to decode logo image");
                }
            } else {
                Log.e("detailDvActivity", "No logo data found for this DonVi");
            }

            tvName.setText(name_donvi);
            tvEmail.setText(email_donvi);
            tvWebsite.setText(website_donvi);
            tvAddress.setText(diachi_donvi);
            tvPhone.setText(sodienthoai_donvi);

        } else {
            // Handle no data for the ID (optional)
        }

        cursor.close();
        db.close();
    }

}