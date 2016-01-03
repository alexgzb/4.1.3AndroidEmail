package com.gezelbom.app413;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    int ATTACHMENT_RESULT_CODE = 222;
    int SEND_EMAIL_RESULT_CODE = 333;
    private EditText emailTo = null;
    private EditText emailSubject = null;
    private EditText emailMessage = null;
    private Button attach = null;
    private Button send = null;
    private TextView attachmentIcon = null;
    private Uri attachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTo = (EditText) findViewById(R.id.editTextEmailTo);
        emailSubject = (EditText) findViewById(R.id.editTextSubject);
        emailMessage = (EditText) findViewById(R.id.editTextMessage);
        attachmentIcon = (TextView) findViewById(R.id.textViewAttachment);
        attach = (Button) findViewById(R.id.buttonAddAttachment);
        send = (Button) findViewById(R.id.buttonSend);
    }

    public void attachFile(View view) {

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        startActivityForResult(i, ATTACHMENT_RESULT_CODE);
        System.out.println();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityForResult requestCode = " + requestCode);
        Log.d(TAG, "onActivityForResult resultCode = " + resultCode);
        if (requestCode == ATTACHMENT_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                attachment = data.getData();
                attachmentIcon.setText("@");
            }
        }
        if (requestCode == SEND_EMAIL_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                /* This Does not work since the Email Activities not return any result
                    emailTo.setText("");
                    emailSubject.setText("");
                    emailMessage.setText("");
                    Toast.makeText(this,"Email sent",Toast.LENGTH_SHORT);
                    */
            }
        }

    }

    public void sendEmail(View view) {
        String recipient = emailTo.getText().toString();
        String subject = emailSubject.getText().toString();
        String message = emailMessage.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.putExtra(Intent.EXTRA_STREAM, attachment);
        emailIntent.setType("message/rfc822");
        startActivityForResult(Intent.createChooser(emailIntent, "Send email..."), SEND_EMAIL_RESULT_CODE);

    }
}
