package com.example.contactmanager;

import static com.example.contactmanager.database.DatabaseHelper.COLUMN_DIA_CHI_DON_VI;
import static com.example.contactmanager.database.DatabaseHelper.COLUMN_EMAIL_DON_VI;
import static com.example.contactmanager.database.DatabaseHelper.COLUMN_ID;
import static com.example.contactmanager.database.DatabaseHelper.COLUMN_LOGO_DON_VI;
import static com.example.contactmanager.database.DatabaseHelper.COLUMN_NAME_DON_VI;
import static com.example.contactmanager.database.DatabaseHelper.COLUMN_SO_DIEN_THOAI;
import static com.example.contactmanager.database.DatabaseHelper.COLUMN_SO_DIEN_THOAI_DON_VI;
import static com.example.contactmanager.database.DatabaseHelper.COLUMN_WEBSITE_DON_VI;
import static com.example.contactmanager.database.DatabaseHelper.TABLE_DON_VI;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contactmanager.database.DatabaseHelper;
import com.example.contactmanager.model.DonVi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class addDv extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private ImageView imgLogo;
    private EditText etTenDv, etEmail, etWebsite, etDiaChi, etDienThoai;
    private DatabaseHelper dbHelper;
    private Bitmap selectedImage;
    ImageView imageViewLogo;
    ImageButton btnBack, btnAdd;
    Button btnAddImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_dv);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DatabaseHelper(this);
        btnAdd = findViewById(R.id.ibtnAdd);
        btnAddImg = findViewById(R.id.btnAddImg);
        imgLogo = findViewById(R.id.imgAvt);
        etTenDv = findViewById(R.id.edtName);
        etEmail = findViewById(R.id.edtEmail);
        etWebsite = findViewById(R.id.edtWebsite);
        etDiaChi = findViewById(R.id.edtLocation);
        etDienThoai = findViewById(R.id.edtPhoneNum);
        btnAddImg.setOnClickListener(v -> openImagePicker());
        btnAdd.setOnClickListener(v -> addDonViToDatabase());
        btnBack = findViewById(R.id.ibtnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addDv.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgLogo.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void addDonViToDatabase() {
        String tenDv = etTenDv.getText().toString();
        String email = etEmail.getText().toString();
        String website = etWebsite.getText().toString();
        String diaChi = etDiaChi.getText().toString();
        String dienThoai = etDienThoai.getText().toString();
        // ... (get other fields)

        if (selectedImage != null) {
            byte[] logoBytes = bitmapToByteArray(selectedImage);
            DonVi donVi = new DonVi(0, tenDv, email, website, logoBytes, diaChi, dienThoai);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_LOGO_DON_VI, logoBytes);
            values.put(COLUMN_NAME_DON_VI, tenDv);
            values.put(COLUMN_EMAIL_DON_VI, email);
            values.put(COLUMN_WEBSITE_DON_VI, website);
            values.put(COLUMN_DIA_CHI_DON_VI, diaChi);
            values.put(COLUMN_SO_DIEN_THOAI_DON_VI, dienThoai);


            long newRowId = db.insert(DatabaseHelper.TABLE_DON_VI, null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Đơn vị đã được thêm thành công", Toast.LENGTH_SHORT).show();
                finish(); // Close activity after successful addition
            } else {
                Toast.makeText(this, "Lỗi khi thêm đơn vị", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Vui lòng chọn ảnh logo", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(addDv.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        // ... (Compression settings)
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Compression settings
        int quality = 0;
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;

        // Scale down the image if it's too large
        int maxWidth = 800; // Set your desired maximum width
        int maxHeight = 600; // Set your desired maximum height
        if (bitmap.getWidth() > maxWidth || bitmap.getHeight() > maxHeight) {
            float scaleFactor = Math.min(maxWidth / (float) bitmap.getWidth(), maxHeight / (float) bitmap.getHeight());
            int newWidth = Math.round(scaleFactor * bitmap.getWidth());
            int newHeight = Math.round(scaleFactor * bitmap.getHeight());
            bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        }

        bitmap.compress(format, quality, stream);

        return stream.toByteArray();
    }


}